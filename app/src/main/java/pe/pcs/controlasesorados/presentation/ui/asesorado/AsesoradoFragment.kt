package pe.pcs.controlasesorados.presentation.ui.asesorado

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
import pe.pcs.controlasesorados.databinding.FragmentAsesoradoBinding
import pe.pcs.controlasesorados.domain.model.Asesorado
import pe.pcs.controlasesorados.presentation.adapter.AsesoradoAdapter
import pe.pcs.controlasesorados.presentation.common.ResponseStatus
import pe.pcs.controlasesorados.presentation.common.UtilsCommon
import pe.pcs.controlasesorados.presentation.common.UtilsMessage

@AndroidEntryPoint
class AsesoradoFragment : Fragment(), AsesoradoAdapter.IOnClickListener {

    private lateinit var binding: FragmentAsesoradoBinding
    private val viewModel by viewModels<AsesoradoViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAsesoradoBinding.inflate(inflater, container, false)
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
            adapter = AsesoradoAdapter(this@AsesoradoFragment)
        }

        binding.fabNuevo.setOnClickListener {
            findNavController().navigate(
                AsesoradoFragmentDirections.actionNavHomeToOperacionAsesoradoActivity(0)
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
                    (binding.rvLista.adapter as AsesoradoAdapter).submitList(it)
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
                        null -> Unit
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

                null -> Unit
            }
        }

    }

    override fun clickEditar(entidad: Asesorado) {
        UtilsCommon.ocultarTeclado(requireView())

        findNavController().navigate(
            AsesoradoFragmentDirections.actionNavHomeToOperacionAsesoradoActivity(entidad.id)
        )
    }

    override fun clickEliminar(entidad: Asesorado) {
        UtilsCommon.ocultarTeclado(requireView())

        MaterialAlertDialogBuilder(requireContext()).apply {
            setCancelable(false)
            setTitle("ELIMINAR")
            setMessage("¿Desea eliminar el registro: ${entidad.nombre}")

            setPositiveButton("SI") { dialog, _ ->
                viewModel.eliminar(entidad)
                dialog.dismiss()
            }

            setNegativeButton("NO") { dialog, _ ->
                dialog.cancel()
            }
        }.create().show()
    }

    override fun clickObjetivo(entidad: Asesorado) {

    }

}