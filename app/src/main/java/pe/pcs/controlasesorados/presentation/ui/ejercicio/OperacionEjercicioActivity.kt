package pe.pcs.controlasesorados.presentation.ui.ejercicio

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.navArgs
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import pe.pcs.controlasesorados.databinding.ActivityOperacionEjercicioBinding
import pe.pcs.controlasesorados.domain.model.Ejercicio
import pe.pcs.controlasesorados.domain.model.Maquina
import pe.pcs.controlasesorados.domain.model.Rutina
import pe.pcs.controlasesorados.presentation.common.ResponseStatus
import pe.pcs.controlasesorados.presentation.common.UtilsMessage
import pe.pcs.controlasesorados.presentation.ui.dialog.buscarmaquina.BuscarMaquinaDialog
import pe.pcs.controlasesorados.presentation.ui.dialog.buscarrutina.BuscarRutinaDialog

@AndroidEntryPoint
class OperacionEjercicioActivity : AppCompatActivity(),
    BuscarMaquinaDialog.IOnBuscarMaquinaListener,
    BuscarRutinaDialog.IOnBuscarRutinaListener {

    private lateinit var binding: ActivityOperacionEjercicioBinding
    private val viewModel by viewModels<OperacionEjercicioViewModel>()
    private val args: OperacionEjercicioActivityArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOperacionEjercicioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initListener()
        initUiState()

        if (args.id > 0)
            viewModel.obtenerEjercicio(args.id)
    }

    private fun initListener() {

        binding.toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        binding.etMaquina.setOnClickListener {
            BuscarMaquinaDialog.newInstance(this)
                .show(supportFragmentManager, "Buscar_Maquina")
        }

        binding.etRutina.setOnClickListener {
            BuscarRutinaDialog.newInstance(this)
                .show(supportFragmentManager, "Buscar_Rutina")
        }

        binding.fabGrabar.setOnClickListener {
            if (binding.etDescripcion.text.toString().trim().isEmpty()) {
                UtilsMessage.showAlertOk("ADVERTENCIA", "Debe asignar un ejercicio", this)
                return@setOnClickListener
            }

            if (viewModel.itemMaquina.value == null) {
                UtilsMessage.showAlertOk("ADVERTENCIA", "Debe asignar una maquina o equipo", this)
                return@setOnClickListener
            }

            if (viewModel.itemRutina.value == null) {
                UtilsMessage.showAlertOk("ADVERTENCIA", "Debe asignar una rutina", this)
                return@setOnClickListener
            }

            viewModel.grabar(
                Ejercicio().apply {
                    id = viewModel.itemEjercicio.value?.id ?: 0
                    descripcion = binding.etDescripcion.text.toString().trim()
                    idmaquina = viewModel.itemMaquina.value!!.id
                    idrutina = viewModel.itemRutina.value!!.id
                }
            )
        }

    }

    private fun initUiState() {

        viewModel.itemMaquina.observe(this) {
            binding.etMaquina.setText(it?.descripcion)
        }

        viewModel.itemRutina.observe(this) {
            binding.etRutina.setText(it?.descripcion)
        }

        viewModel.itemEjercicio.observe(this) {
            if(it == null) return@observe

            binding.etDescripcion.setText(it.descripcion)

            viewModel.setMaquina(
                Maquina().apply {
                    id = it.idmaquina
                    descripcion = it.maquina
                }
            )

            viewModel.setRutina(
                Rutina().apply {
                    id = it.idrutina
                    descripcion = it.rutina
                }
            )
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.stateObtenerEjercicio.collect {
                    when (it) {
                        is ResponseStatus.Error -> {
                            binding.progressBar.isVisible = false
                            UtilsMessage.showAlertOk(
                                "ERROR",
                                it.message,
                                this@OperacionEjercicioActivity
                            )
                        }

                        is ResponseStatus.Loading -> binding.progressBar.isVisible = true
                        is ResponseStatus.Success -> binding.progressBar.isVisible = false
                        null -> Unit
                    }
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.stateGrabar.collect {
                    when (it) {
                        is ResponseStatus.Error -> {
                            binding.progressBar.isVisible = false
                            UtilsMessage.showAlertOk(
                                "ERROR",
                                it.message,
                                this@OperacionEjercicioActivity
                            )
                        }

                        is ResponseStatus.Loading -> binding.progressBar.isVisible = true
                        is ResponseStatus.Success -> {
                            binding.progressBar.isVisible = false

                            if (it.data > 0) {
                                binding.etDescripcion.setText("")
                                viewModel.resetItem()
                                viewModel.setRutina(null)
                                viewModel.setMaquina(null)
                                UtilsMessage.showToast("Registro grabado correctamente")
                            }
                        }

                        null -> Unit
                    }
                }
            }
        }
    }

    override fun clickItemBuscarMaquina(entidad: Maquina) {
        viewModel.setMaquina(entidad)
    }

    override fun clickItemBuscarRutina(entidad: Rutina) {
        viewModel.setRutina(entidad)
    }
}