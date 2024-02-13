package pe.pcs.controlasesorados.presentation.ui.dialog.buscarmaquina

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import pe.pcs.controlasesorados.domain.model.Maquina
import pe.pcs.controlasesorados.domain.usecase.maquina.ListarMaquinaUseCase
import pe.pcs.controlasesorados.presentation.common.ResponseStatus
import pe.pcs.controlasesorados.presentation.common.makeCall
import javax.inject.Inject

@HiltViewModel
class BuscarMaquinaViewModel @Inject constructor(
    private val listarMaquinaUseCase: ListarMaquinaUseCase
): ViewModel() {

    private val _listaMaquina = MutableStateFlow<List<Maquina>?>(emptyList())
    val listaMaquina: StateFlow<List<Maquina>?> = _listaMaquina

    private val _stateListaMaquina =
        MutableStateFlow<ResponseStatus<List<Maquina>>?>(ResponseStatus.Loading())
    val stateListaMaquina: StateFlow<ResponseStatus<List<Maquina>>?> = _stateListaMaquina

    fun listarMaquina(dato: String) {
        viewModelScope.launch {
            _stateListaMaquina.value = ResponseStatus.Loading()

            makeCall {
                listarMaquinaUseCase(dato).collect {
                    _listaMaquina.value = ResponseStatus.Success(it).data
                    _stateListaMaquina.value = ResponseStatus.Success(it)
                }
            }
        }
    }

}