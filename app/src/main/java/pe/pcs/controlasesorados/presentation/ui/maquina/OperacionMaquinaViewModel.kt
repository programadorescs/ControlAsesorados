package pe.pcs.controlasesorados.presentation.ui.maquina

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import pe.pcs.controlasesorados.domain.model.Maquina
import pe.pcs.controlasesorados.domain.usecase.maquina.GrabarMaquinaUseCase
import pe.pcs.controlasesorados.domain.usecase.maquina.ObtenerMaquinaUseCase
import pe.pcs.controlasesorados.presentation.common.ResponseStatus
import pe.pcs.controlasesorados.presentation.common.makeCall
import javax.inject.Inject

@HiltViewModel
class OperacionMaquinaViewModel @Inject constructor(
    private val grabarMaquinaUseCase: GrabarMaquinaUseCase,
    private val obtenerMaquinaUseCase: ObtenerMaquinaUseCase
) : ViewModel() {

    private val _item = MutableStateFlow<Maquina?>(null)
    val item: StateFlow<Maquina?> = _item

    private val _uiState = MutableStateFlow<ResponseStatus<Int>>(ResponseStatus.Success(0))
    val uiState: StateFlow<ResponseStatus<Int>> = _uiState

    private val _uiStateItem =
        MutableStateFlow<ResponseStatus<Maquina?>>(ResponseStatus.Loading())
    val uiStateItem: StateFlow<ResponseStatus<Maquina?>> = _uiStateItem

    fun resetItem() {
        _item.value = null
    }

    fun obtenerMaquina(id: Int) {
        viewModelScope.launch {
            _uiStateItem.value = ResponseStatus.Loading()

            makeCall {
                obtenerMaquinaUseCase(id)
            }.let {
                if (it is ResponseStatus.Success)
                    _item.value = it.data

                _uiStateItem.value = it
            }
        }
    }

    fun grabar(entidad: Maquina) {
        viewModelScope.launch {
            _uiState.value = ResponseStatus.Loading()

            makeCall {
                grabarMaquinaUseCase(entidad)
            }.let {
                _uiState.value = it
            }
        }
    }

}