package pe.pcs.controlasesorados.presentation.ui.objetivo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import pe.pcs.controlasesorados.R
import pe.pcs.controlasesorados.databinding.OperacionObjetivoDialogBinding
import pe.pcs.controlasesorados.domain.model.Objetivo
import pe.pcs.controlasesorados.presentation.common.ResponseStatus
import pe.pcs.controlasesorados.presentation.common.UtilsCommon
import pe.pcs.controlasesorados.presentation.common.UtilsDate
import pe.pcs.controlasesorados.presentation.common.UtilsMessage

@AndroidEntryPoint
class OperacionObjetivoDialog : DialogFragment() {

    private lateinit var binding: OperacionObjetivoDialogBinding
    private val viewModel by viewModels<OperacionObjetivoViewModel>()
    private var codigoAsesorado = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.FullScreenDialog)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = OperacionObjetivoDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListener()
        initUiState()
    }

    companion object {
        fun newInstance(_idAsesorado: Int): OperacionObjetivoDialog {
            val instancia = OperacionObjetivoDialog()
            instancia.codigoAsesorado = _idAsesorado
            return instancia
        }
    }

    private fun initListener() {

        binding.includedLayout.toolbar.title = this.getString(R.string.registrar_objetivo)
        binding.includedLayout.toolbar.subtitle = ""

        binding.includedLayout.toolbar.setNavigationOnClickListener {
            dismiss()
        }

        binding.fabGrabar.setOnClickListener {
            UtilsCommon.ocultarTeclado(it)

            if (binding.etDescripcion.text.toString().isEmpty()) {
                UtilsMessage.showToast("Debe ingresar una descripcion o nombre de objetivo")
                return@setOnClickListener
            }

            viewModel.grabar(
                Objetivo().apply {
                    idAsesorado = codigoAsesorado
                    fecha = UtilsDate.obtenerFechaActual()
                    descripcion = binding.etDescripcion.text.toString().trim()
                    obs = binding.etObs.text.toString().trim()
                    estado = "vigente"
                }
            )
        }

    }

    private fun initUiState() {

        viewModel.state.observe(viewLifecycleOwner) {
            when (it) {
                is ResponseStatus.Error -> {
                    binding.progressBar.isVisible = false
                }

                is ResponseStatus.Loading -> binding.progressBar.isVisible = true
                is ResponseStatus.Success -> {
                    binding.progressBar.isVisible = false

                    if (it.data < 1)
                        return@observe

                    UtilsMessage.showToast("Registro grabado correctamente")
                    viewModel.resetState()
                    UtilsCommon.limpiarEditText(requireView())
                    binding.etDescripcion.requestFocus()
                    dismiss()
                }

                null -> Unit
            }
        }

    }

}