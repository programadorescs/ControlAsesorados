package pe.pcs.controlasesorados.presentation.ui.dialog.buscarrutina

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import pe.pcs.controlasesorados.R
import pe.pcs.controlasesorados.databinding.BuscarRutinaDialogBinding
import pe.pcs.controlasesorados.domain.model.Rutina
import pe.pcs.controlasesorados.presentation.adapter.BuscarRutinaAdapter
import pe.pcs.controlasesorados.presentation.common.ResponseStatus
import pe.pcs.controlasesorados.presentation.common.UtilsMessage

@AndroidEntryPoint
class BuscarRutinaDialog : DialogFragment(), BuscarRutinaAdapter.IOnClickListener {

    private lateinit var binding: BuscarRutinaDialogBinding
    private val viewModel by viewModels<BuscarRutinaViewModel>()
    private lateinit var iOnBuscarRutinaListener: IOnBuscarRutinaListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.FullScreenDialog)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = BuscarRutinaDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListener()
        initUiState()

        viewModel.listarRutina(binding.etBuscar.text.toString().trim())
    }

    private fun initListener() {

        binding.toolbar.setNavigationOnClickListener {
            dismiss()
        }

        binding.rvLista.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = BuscarRutinaAdapter(this@BuscarRutinaDialog)
        }

        binding.etBuscar.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                viewModel.listarRutina(p0.toString().trim())
            }

        })
    }

    private fun initUiState() {

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.listaRutina.collect {
                    (binding.rvLista.adapter as BuscarRutinaAdapter).submitList(it)
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.stateListaRutina.collect {
                    when (it) {
                        is ResponseStatus.Error -> {
                            binding.progressBar.isVisible = false

                            UtilsMessage.showAlertOk("ERROR", it.message, requireContext())
                        }

                        is ResponseStatus.Loading -> binding.progressBar.isVisible = true
                        is ResponseStatus.Success -> binding.progressBar.isVisible = false
                    }
                }
            }
        }
    }

    override fun clickItem(entidad: Rutina) {
        iOnBuscarRutinaListener.clickItemBuscarRutina(entidad)
        dismiss()
    }

    interface IOnBuscarRutinaListener{
        fun clickItemBuscarRutina(entidad: Rutina)
    }

    companion object{
        fun newInstance(listener: IOnBuscarRutinaListener): BuscarRutinaDialog {
            val instancia = BuscarRutinaDialog()
            instancia.iOnBuscarRutinaListener = listener
            return instancia
        }
    }
}