package pe.pcs.controlasesorados.presentation.ui.rutina

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import pe.pcs.controlasesorados.databinding.FragmentRutinaBinding
import pe.pcs.controlasesorados.domain.model.Rutina
import pe.pcs.controlasesorados.presentation.adapter.RutinaAdapter
import pe.pcs.controlasesorados.presentation.common.ResponseStatus
import pe.pcs.controlasesorados.presentation.common.UtilsCommon
import pe.pcs.controlasesorados.presentation.common.UtilsMessage

@AndroidEntryPoint
class RutinaFragment : Fragment(), RutinaAdapter.IOnClickListener {

    private lateinit var binding: FragmentRutinaBinding
    private val viewModel by viewModels<RutinaViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRutinaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListener()
        initUiState()

        viewModel.listar(binding.etBuscar.text.toString().trim())
    }

    private fun initListener() {
        binding.rvLista.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = RutinaAdapter(this@RutinaFragment)
        }

        binding.fabNuevo.setOnClickListener {
            findNavController().navigate(
                RutinaFragmentDirections.actionNavRutinaToOperacionRutinaActivity(0)
            )
        }

        binding.etBuscar.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }

            override fun afterTextChanged(p0: Editable?) {
                viewModel.listar(p0.toString().trim())
            }

        })
    }

    private fun initUiState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.lista.collect {
                    (binding.rvLista.adapter as RutinaAdapter).submitList(it)
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.status.collect {
                    when (it) {
                        is ResponseStatus.Error -> {
                            binding.progressBar.isVisible = false

                            UtilsMessage.showAlertOk(
                                "ERROR", it.message, requireContext()
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

        viewModel.statusDelete.observe(viewLifecycleOwner) {
            when (it) {
                is ResponseStatus.Error -> {
                    binding.progressBar.isVisible = false

                    UtilsMessage.showAlertOk(
                        "ERROR", it.message, requireContext()
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

                else -> Unit
            }
        }
    }

    override fun clickEditar(entidad: Rutina) {
        UtilsCommon.ocultarTeclado(requireView())

        findNavController().navigate(
            RutinaFragmentDirections.actionNavRutinaToOperacionRutinaActivity(entidad.id)
        )
    }

    override fun clickEliminar(entidad: Rutina) {
        UtilsCommon.ocultarTeclado(requireView())

        MaterialAlertDialogBuilder(requireContext()).apply {
            setCancelable(false)
            setTitle("ELIMINAR")
            setMessage("¿Desea eliminar el registro: ${entidad.descripcion}")

            setPositiveButton("SI") { dialog, _ ->
                viewModel.eliminar(entidad)
                dialog.dismiss()
            }

            setNegativeButton("NO") { dialog, _ ->
                dialog.cancel()
            }
        }.create().show()
    }
}