package pe.pcs.controlasesorados.presentation.ui.crearcuenta

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import dagger.hilt.android.AndroidEntryPoint
import pe.pcs.controlasesorados.databinding.ActivityCrearCuentaBinding
import pe.pcs.controlasesorados.domain.model.Usuario
import pe.pcs.controlasesorados.presentation.common.ResponseStatus
import pe.pcs.controlasesorados.presentation.common.UtilsCommon
import pe.pcs.controlasesorados.presentation.common.UtilsMessage
import pe.pcs.controlasesorados.presentation.common.UtilsSecurity
import pe.pcs.controlasesorados.presentation.ui.main.MainActivity

@AndroidEntryPoint
class CrearCuentaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCrearCuentaBinding
    private val viewModel by viewModels<CrearCuentaViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCrearCuentaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initListener()
        initUiState()
    }

    private fun initListener() {
        binding.fabGrabar.setOnClickListener {
            UtilsCommon.ocultarTeclado(it)

            if (binding.etNombre.text.toString().trim().isEmpty() ||
                binding.etEmail.text.toString().trim().isEmpty() ||
                binding.etClave.text.toString().trim().isEmpty()
            ) {
                UtilsMessage.showToast("Todos los campos son obligatorios")
                return@setOnClickListener
            }

            viewModel.grabarCuenta(
                Usuario().apply {
                    id = 0
                    nombre = binding.etNombre.text.toString().trim()
                    email = binding.etEmail.text.toString().trim()
                    clave = UtilsSecurity.createSHAHash512(binding.etClave.text.toString().trim())
                    vigente = 1
                }
            )
        }
    }

    private fun initUiState() {
        viewModel.item.observe(this) {
            if (it == null) return@observe

            MainActivity.mUsuario = it

            UtilsMessage.showToast("¡Cuenta creada correctamente!")

            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        viewModel.status.observe(this) {
            when (it) {
                is ResponseStatus.Error -> {
                    binding.progressBar.isVisible = false

                    if (it.message.isNotEmpty())
                        UtilsMessage.showAlertOk(
                            "ERROR", it.message, this
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