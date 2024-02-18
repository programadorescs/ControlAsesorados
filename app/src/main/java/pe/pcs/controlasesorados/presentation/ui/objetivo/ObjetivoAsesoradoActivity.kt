package pe.pcs.controlasesorados.presentation.ui.objetivo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import pe.pcs.controlasesorados.R
import pe.pcs.controlasesorados.databinding.ActivityObjetivoAsesoradoBinding
import pe.pcs.controlasesorados.domain.model.Objetivo
import pe.pcs.controlasesorados.presentation.adapter.ObjetivoAdapter
import pe.pcs.controlasesorados.presentation.common.ResponseStatus
import pe.pcs.controlasesorados.presentation.common.UtilsMessage

@AndroidEntryPoint
class ObjetivoAsesoradoActivity : AppCompatActivity(), ObjetivoAdapter.IOnClickListener {

    private lateinit var binding: ActivityObjetivoAsesoradoBinding
    private val viewModel by viewModels<ObjetivoAsesoradoViewModel>()
    private val args: ObjetivoAsesoradoActivityArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityObjetivoAsesoradoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initListener()
        initUiState()

        if (args.idAsesorado > 0) {
            binding.tvTitulo.text = args.nombre
            binding.tvFecha.text = args.fecnac
            binding.tvDireccion.text = args.direccion
            binding.tvDni.text = args.dni
            binding.tvTelefono.text = args.telefono
            binding.tvSexo.text = args.sexo

            viewModel.obtenerObjetivosAsesorado(args.idAsesorado)
        }
    }

    private fun initListener() {

        binding.includedLayout.toolbar.title = this.getString(R.string.objetivo_del_asesorado)
        binding.includedLayout.toolbar.subtitle = ""

        binding.includedLayout.toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        binding.rvLista.apply {
            layoutManager = LinearLayoutManager(this@ObjetivoAsesoradoActivity)
            adapter = ObjetivoAdapter(this@ObjetivoAsesoradoActivity)
        }

        binding.fabNuevo.setOnClickListener {
            if (args.idAsesorado == 0)
                return@setOnClickListener

            var existeObjetivoVigente = false
            for (item in viewModel.lista.value) {
                if (item.estado.lowercase() == "vigente")
                    existeObjetivoVigente = true
            }

            if (existeObjetivoVigente) {
                UtilsMessage.showAlertOk(
                    "ADVERTENCIA",
                    "Aun existe un objetivo vigente, para crear un nuevo objetivo primero debe de finalizar el objetivo",
                    this
                )
                return@setOnClickListener
            }

            OperacionObjetivoDialog.newInstance(args.idAsesorado).show(
                supportFragmentManager, "OperacionObjetivoDialog"
            )
        }

    }

    private fun initUiState() {

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.lista.collect {
                    (binding.rvLista.adapter as ObjetivoAdapter).submitList(it)
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.statusObjetivo.collect {
                    when (it) {
                        is ResponseStatus.Error -> {
                            binding.progressBar.isVisible = false

                            UtilsMessage.showAlertOk(
                                "ERROR", it.message, this@ObjetivoAsesoradoActivity
                            )

                            viewModel.resetStatus()
                        }

                        is ResponseStatus.Loading -> binding.progressBar.isVisible = true
                        is ResponseStatus.Success -> binding.progressBar.isVisible = false
                        else -> Unit
                    }
                }
            }
        }

        viewModel.statusDelete.observe(this) {
            when (it) {
                is ResponseStatus.Error -> {
                    binding.progressBar.isVisible = false

                    UtilsMessage.showAlertOk(
                        "ERROR", it.message, this@ObjetivoAsesoradoActivity
                    )

                    viewModel.resetStatusDelete()
                }

                is ResponseStatus.Loading -> binding.progressBar.isVisible = true
                is ResponseStatus.Success -> {
                    binding.progressBar.isVisible = false

                    if (it.data > 0)
                        UtilsMessage.showToast("¡Felicidades, registro anulado correctamente!")

                    viewModel.resetStatusDelete()
                }

                null -> Unit
            }
        }

        viewModel.statusFinalizar.observe(this) {
            when (it) {
                is ResponseStatus.Error -> {
                    binding.progressBar.isVisible = false

                    UtilsMessage.showAlertOk(
                        "ERROR", it.message, this@ObjetivoAsesoradoActivity
                    )

                    viewModel.resetStatusFinalizar()
                }

                is ResponseStatus.Loading -> binding.progressBar.isVisible = true
                is ResponseStatus.Success -> {
                    binding.progressBar.isVisible = false

                    if (it.data > 0)
                        UtilsMessage.showToast("¡Felicidades, registro finalizado correctamente!")

                    viewModel.resetStatusFinalizar()
                }

                null -> Unit
            }
        }

    }

    private fun verificarEstado(estado: String) {
        if (estado.lowercase() == "finalizado") {
            UtilsMessage.showAlertOk("ADVERTENCIA", "El objetivo ya se encuentra finalizado", this)
            return
        }
    }

    override fun clickControl(entidad: Objetivo) {

        if (entidad.estado.lowercase() == "finalizado") {
            UtilsMessage.showAlertOk("ADVERTENCIA", "El objetivo ya se encuentra finalizado", this)
            return
        }
    }

    override fun clickFinalizar(entidad: Objetivo) {
        if (entidad.estado.lowercase() == "finalizado") {
            UtilsMessage.showAlertOk("ADVERTENCIA", "El objetivo ya se encuentra finalizado", this)
            return
        }

        MaterialAlertDialogBuilder(this).apply {
            setCancelable(false)
            setTitle("ELIMINAR")
            setMessage("¿Desea eliminar el registro: ${entidad.descripcion}")

            setPositiveButton("SI") { dialog, _ ->
                viewModel.finalizar(entidad)
                dialog.dismiss()
            }

            setNegativeButton("NO") { dialog, _ ->
                dialog.cancel()
            }
        }.create().show()
    }

    override fun clickEjercicio(entidad: Objetivo) {
        if (entidad.estado.lowercase() == "finalizado") {
            UtilsMessage.showAlertOk("ADVERTENCIA", "El objetivo ya se encuentra finalizado", this)
            return
        }
    }

}