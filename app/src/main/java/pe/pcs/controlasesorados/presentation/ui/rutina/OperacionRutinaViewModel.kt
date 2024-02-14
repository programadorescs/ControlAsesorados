package pe.pcs.controlasesorados.presentation.ui.rutina

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import pe.pcs.controlasesorados.domain.model.Rutina
import pe.pcs.controlasesorados.domain.usecase.rutina.GrabarRutinaUseCase
import pe.pcs.controlasesorados.domain.usecase.rutina.ObtenerRutinaUseCase
import pe.pcs.controlasesorados.presentation.common.ResponseStatus
import pe.pcs.controlasesorados.presentation.common.makeCall
import javax.inject.Inject

@HiltViewModel
class OperacionRutinaViewModel @Inject constructor(
    private val grabarRutinaUseCase: GrabarRutinaUseCase,
    private val obtenerRutinaUseCase: ObtenerRutinaUseCase
): ViewModel() {

    private val _item = MutableStateFlow<Rutina?>(null)
    val item: StateFlow<Rutina?> = _item

    private val _uiState = MutableStateFlow<ResponseStatus<Int>?>(null)
    val uiState: StateFlow<ResponseStatus<Int>?> = _uiState

    private val _uiStateItem =
        MutableStateFlow<ResponseStatus<Rutina?>?>(null)
    val uiStateItem: StateFlow<ResponseStatus<Rutina?>?> = _uiStateItem

    fun resetItem() {
        _item.value = null
    }

    fun obtenerRutina(id: Int) {
        viewModelScope.launch {
            _uiStateItem.value = ResponseStatus.Loading()

            makeCall {
                obtenerRutinaUseCase(id)
            }.let {
                if (it is ResponseStatus.Success)
                    _item.value = it.data

                _uiStateItem.value = it
            }
        }
    }

    fun grabar(entidad: Rutina) {
        viewModelScope.launch {
            _uiState.value = ResponseStatus.Loading()

            makeCall {
                grabarRutinaUseCase(entidad)
            }.let {
                _uiState.value = it
            }
        }
    }

}