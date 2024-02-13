package pe.pcs.controlasesorados.presentation.ui.dialog.buscarmaquina

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
import pe.pcs.controlasesorados.databinding.BuscarMaquinaDialogBinding
import pe.pcs.controlasesorados.domain.model.Maquina
import pe.pcs.controlasesorados.presentation.adapter.BuscarMaquinaAdapter
import pe.pcs.controlasesorados.presentation.common.ResponseStatus
import pe.pcs.controlasesorados.presentation.common.UtilsMessage

@AndroidEntryPoint
class BuscarMaquinaDialog : DialogFragment(), BuscarMaquinaAdapter.IOnClickListener {

    private lateinit var binding: BuscarMaquinaDialogBinding
    private val viewModel by viewModels<BuscarMaquinaViewModel>()
    private lateinit var iOnBuscarMaquinaListener: IOnBuscarMaquinaListener

    companion object {
        fun newInstance(listener: IOnBuscarMaquinaListener): BuscarMaquinaDialog {
            val instancia = BuscarMaquinaDialog()
            instancia.iOnBuscarMaquinaListener = listener
            //instancia.isCancelable = false
            return instancia
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.FullScreenDialog)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = BuscarMaquinaDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListener()
        initUiState()

        viewModel.listarMaquina(binding.etBuscar.text.toString().trim())
    }

    private fun initListener() {

        binding.toolbar.setNavigationOnClickListener {
            dismiss()
        }

        binding.rvLista.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = BuscarMaquinaAdapter(this@BuscarMaquinaDialog)
        }

        binding.etBuscar.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                viewModel.listarMaquina(p0.toString().trim())
            }

        })
    }

    private fun initUiState() {

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.listaMaquina.collect {
                    (binding.rvLista.adapter as BuscarMaquinaAdapter).submitList(it)
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.stateListaMaquina.collect {
                    when (it) {
                        is ResponseStatus.Error -> {
                            binding.progressBar.isVisible = false

                            UtilsMessage.showAlertOk("ERROR", it.message, requireContext())
                        }

                        is ResponseStatus.Loading -> binding.progressBar.isVisible = true
                        is ResponseStatus.Success -> binding.progressBar.isVisible = false
                        null -> binding.progressBar.isVisible = false
                    }
                }
            }
        }
    }

    override fun clickItem(entidad: Maquina) {
        iOnBuscarMaquinaListener.clickItemBuscarMaquina(entidad)
        dismiss()
    }

    interface IOnBuscarMaquinaListener {
        fun clickItemBuscarMaquina(entidad: Maquina)
    }

}