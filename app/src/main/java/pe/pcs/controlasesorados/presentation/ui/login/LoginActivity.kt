package pe.pcs.controlasesorados.presentation.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import dagger.hilt.android.AndroidEntryPoint
import pe.pcs.controlasesorados.R
import pe.pcs.controlasesorados.databinding.ActivityLoginBinding
import pe.pcs.controlasesorados.presentation.common.ResponseStatus
import pe.pcs.controlasesorados.presentation.common.UtilsCommon
import pe.pcs.controlasesorados.presentation.common.UtilsMessage
import pe.pcs.controlasesorados.presentation.common.UtilsSecurity
import pe.pcs.controlasesorados.presentation.ui.crearcuenta.CrearCuentaActivity
import pe.pcs.controlasesorados.presentation.ui.main.MainActivity

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val viewModel by viewModels<LoginViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initListener()
        initUiState()
    }

    private fun initListener() {
        binding.btAceptar.setOnClickListener {
            UtilsCommon.ocultarTeclado(it)

            if (binding.etEmail.text.toString().trim().isEmpty() ||
                binding.etClave.text.toString().trim().isEmpty()
            ) {
                UtilsMessage.showAlertOk(
                    "ADVERTENCIA", "Debe ingresar un email y clave", this
                )
                return@setOnClickListener
            }

            viewModel.obtenerUsuario(
                binding.etEmail.text.toString().trim(),
                UtilsSecurity.createSHAHash512(binding.etClave.text.toString().trim())
            )
        }

        binding.tvCrearCuenta.setOnClickListener {
            UtilsCommon.ocultarTeclado(it)
            viewModel.existeCuenta()
        }
    }

    private fun initUiState() {
        viewModel.leerEmailLogin()

        viewModel.emailLogin.observe(this) {
            binding.etEmail.setText(it)
        }

        viewModel.cantidad.observe(this) {
            if ((it != null) && (it > 0)) {
                UtilsMessage.showAlertOk(
                    "ERROR",
                    "Ya existe una o mas cuentas creadas en la app, use alguna de ellas para acceder.",
                    this
                )
                return@observe
            }

            // Pasamos a la actividad de crear cuenta
            startActivity(Intent(this, CrearCuentaActivity::class.java))
            finish()
        }

        viewModel.item.observe(this) {
            if (it == null) {
                UtilsMessage.showAlertOk(
                    "ADVERTENCIA",
                    "El email y/o la clave ingresada no son correctos.",
                    this
                )
                return@observe
            }

            MainActivity.mUsuario = viewModel.item.value

            // Pasamos a la actividad principal
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        viewModel.statusInt.observe(this) {
            when (it) {
                is ResponseStatus.Error -> {
                    binding.progressBar.isVisible = false

                    if (it.message.isNotEmpty())
                        UtilsMessage.showAlertOk(
                            "ERROR", it.message, this
                        )

                    viewModel.resetStatusInt()
                }

                is ResponseStatus.Loading -> binding.progressBar.isVisible = true
                is ResponseStatus.Success -> binding.progressBar.isVisible = false
                else -> Unit
            }
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