package pe.pcs.controlasesorados.presentation.ui.maquina

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import pe.pcs.controlasesorados.domain.usecase.maquina.EliminarMaquinaUseCase
import pe.pcs.controlasesorados.domain.usecase.maquina.ListarMaquinaUseCase
import javax.inject.Inject

@HiltViewModel
class MaquinaViewModel @Inject constructor(
    private val listarMaquinaUseCase: ListarMaquinaUseCase,
    private val eliminarMaquinaUseCase: EliminarMaquinaUseCase
): ViewModel() {
}