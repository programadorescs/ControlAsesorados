package pe.pcs.controlasesorados.presentation.ui.ejercicio

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import pe.pcs.controlasesorados.databinding.FragmentEjercicioBinding
import pe.pcs.controlasesorados.domain.model.Ejercicio
import pe.pcs.controlasesorados.domain.model.report.ReporteEjercicioModel
import pe.pcs.controlasesorados.presentation.adapter.EjercicioAdapter
import pe.pcs.controlasesorados.presentation.common.ResponseStatus
import pe.pcs.controlasesorados.presentation.common.UtilsCommon
import pe.pcs.controlasesorados.presentation.common.UtilsMessage

@AndroidEntryPoint
class EjercicioFragment : Fragment(), EjercicioAdapter.IOnClickListener {

    private lateinit var binding: FragmentEjercicioBinding
    private val viewModel by viewModels<EjercicioViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEjercicioBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListener()
        initUiState()

        viewModel.listar("")
    }

    private fun initListener() {
        binding.rvLista.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = EjercicioAdapter(this@EjercicioFragment)
        }

        binding.etBuscar.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                viewModel.listar(p0.toString().trim())
            }

        })

        binding.fabNuevo.setOnClickListener {
            findNavController().navigate(
                EjercicioFragmentDirections.actionNavEjercicioToOperacionEjercicioActivity(0)
            )
        }
    }

    private fun initUiState() {

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.lista.collect {
                    (binding.rvLista.adapter as EjercicioAdapter).submitList(it)
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.status.collect {
                    when (it) {
                        is ResponseStatus.Error -> {
                            binding.progressBar.isVisible = false
                            UtilsMessage.showAlertOk("ERROR", it.message, requireContext())
                            viewModel.resetStatus()
                        }

                        is ResponseStatus.Loading -> binding.progressBar.isVisible = true
                        is ResponseStatus.Success -> binding.progressBar.isVisible = false
                        null -> Unit
                    }
                }
            }
        }
    }

    override fun clickEditar(entidad: ReporteEjercicioModel) {
        UtilsCommon.ocultarTeclado(requireView())

        findNavController().navigate(
            EjercicioFragmentDirections.actionNavEjercicioToOperacionEjercicioActivity(entidad.id)
        )
    }

    override fun clickEliminar(entidad: ReporteEjercicioModel) {
        UtilsCommon.ocultarTeclado(requireView())

        MaterialAlertDialogBuilder(requireContext()).apply {
            setCancelable(false)
            setTitle("ELIMINAR")
            setMessage("¿Desea eliminar el registro: ${entidad.descripcion}")

            setPositiveButton("SI") { dialog, _ ->
                viewModel.eliminar(
                    Ejercicio().apply {
                        id = entidad.id
                    }
                )
                dialog.dismiss()
            }

            setNegativeButton("NO") { dialog, _ ->
                dialog.cancel()
            }
        }.create().show()
    }
}