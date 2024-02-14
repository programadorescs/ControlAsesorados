package pe.pcs.controlasesorados.presentation.ui.asesorado

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.navArgs
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import pe.pcs.controlasesorados.R
import pe.pcs.controlasesorados.databinding.ActivityOperacionAsesoradoBinding
import pe.pcs.controlasesorados.domain.model.Asesorado
import pe.pcs.controlasesorados.domain.model.Ejercicio
import pe.pcs.controlasesorados.domain.model.Maquina
import pe.pcs.controlasesorados.domain.model.Rutina
import pe.pcs.controlasesorados.presentation.common.ResponseStatus
import pe.pcs.controlasesorados.presentation.common.UtilsCommon
import pe.pcs.controlasesorados.presentation.common.UtilsMessage

@AndroidEntryPoint
class OperacionAsesoradoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOperacionAsesoradoBinding
    private val viewModel by viewModels<OperacionAsesoradoViewModel>()
    private val args: OperacionAsesoradoActivityArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOperacionAsesoradoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initListener()
        initUiState()

        if (args.id > 0)
            viewModel.obtenerAsesorado(args.id)
    }

    private fun initListener() {

        binding.includedLayout.toolbar.subtitle = this.getString(R.string.asesorado)

        binding.includedLayout.toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        binding.fabGrabar.setOnClickListener {
            if (binding.etNombre.text.toString().trim().isEmpty() ||
            binding.etDni.text.toString().trim().isEmpty()) {
                UtilsMessage.showAlertOk("ADVERTENCIA", "El nombre y el nro de dni son obligatorios, debe completar la info", this)
                return@setOnClickListener
            }

            viewModel.grabar(
                Asesorado().apply {
                    id = viewModel.itemAsesorado.value?.id ?: 0
                    nombre = binding.etNombre.text.toString().trim()
                    fecnac = binding.etFecnac.text.toString().trim()
                    dni = binding.etDni.text.toString().trim()
                    telefono = binding.etTelefono.text.toString().trim()
                    direccion = binding.etDireccion.text.toString().trim()
                    sexo = "Femenino"
                }
            )
        }
    }

    private fun initUiState() {

        viewModel.itemAsesorado.observe(this) {
            if(it == null) return@observe

            binding.etNombre.setText(it.nombre)
            binding.etDni.setText(it.dni)
            binding.etFecnac.setText(it.fecnac)
            binding.etTelefono.setText(it.telefono)
            binding.etDireccion.setText(it.direccion)
            //binding.etNombre.setText(it.sexo)
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.stateObtenerAsesorado.collect {
                    when (it) {
                        is ResponseStatus.Error -> {
                            binding.progressBar.isVisible = false
                            UtilsMessage.showAlertOk(
                                "ERROR",
                                it.message,
                                this@OperacionAsesoradoActivity
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
                                this@OperacionAsesoradoActivity
                            )
                        }

                        is ResponseStatus.Loading -> binding.progressBar.isVisible = true
                        is ResponseStatus.Success -> {
                            binding.progressBar.isVisible = false

                            if (it.data > 0) {
                                UtilsCommon.limpiarEditText(binding.root.rootView)
                                viewModel.resetItem()
                                UtilsMessage.showToast("Registro grabado correctamente")
                            }
                        }

                        null -> Unit
                    }
                }
            }
        }

    }
}