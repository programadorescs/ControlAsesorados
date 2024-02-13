package pe.pcs.controlasesorados.presentation.ui.rutina

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
import pe.pcs.controlasesorados.databinding.ActivityOperacionRutinaBinding
import pe.pcs.controlasesorados.domain.model.Rutina
import pe.pcs.controlasesorados.presentation.common.ResponseStatus
import pe.pcs.controlasesorados.presentation.common.UtilsCommon
import pe.pcs.controlasesorados.presentation.common.UtilsMessage

@AndroidEntryPoint
class OperacionRutinaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOperacionRutinaBinding
    private val viewModel by viewModels<OperacionRutinaViewModel>()
    private val args: OperacionRutinaActivityArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOperacionRutinaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initListener()
        initUiState()

        if (args.id > 0)
            viewModel.obtenerRutina(args.id)
    }

    private fun initListener(){
        binding.toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        binding.fabGrabar.setOnClickListener {
            UtilsCommon.ocultarTeclado(it)

            if (binding.etDescripcion.text.toString().isEmpty()) {
                UtilsMessage.showAlertOk(
                    "ADVERTENCIA",
                    "Todos los datos son requeridos.",
                    this
                )

                return@setOnClickListener
            }

            viewModel.grabar(
                Rutina().apply {
                    id = viewModel.item.value?.id ?: 0
                    descripcion = binding.etDescripcion.text.toString().trim()
                }
            )
        }
    }

    private fun initUiState(){
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiStateItem.collect {
                    when (it) {
                        is ResponseStatus.Error -> {
                            binding.progressBar.isVisible = false
                            UtilsMessage.showAlertOk(
                                "ERROR", it.message, this@OperacionRutinaActivity
                            )
                        }

                        is ResponseStatus.Loading -> binding.progressBar.isVisible = true
                        is ResponseStatus.Success -> {
                            binding.progressBar.isVisible = false

                            if (it.data == null)
                                return@collect

                            binding.etDescripcion.setText(it.data.descripcion)
                        }
                    }
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    when (it) {
                        is ResponseStatus.Error -> {
                            binding.progressBar.isVisible = false
                            UtilsMessage.showAlertOk(
                                "ERROR", it.message, this@OperacionRutinaActivity
                            )
                        }

                        is ResponseStatus.Loading -> binding.progressBar.isVisible = true
                        is ResponseStatus.Success -> {
                            binding.progressBar.isVisible = false

                            if (it.data > 0) {
                                UtilsMessage.showToast("¡Felicidades, el registro fue grabado!")
                                UtilsCommon.limpiarEditText(binding.root.rootView)
                                binding.etDescripcion.requestFocus()
                                viewModel.resetItem()
                            }
                        }

                        null -> Unit
                    }
                }
            }
        }
    }
}