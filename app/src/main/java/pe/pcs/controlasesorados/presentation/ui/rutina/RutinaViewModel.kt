package pe.pcs.controlasesorados.presentation.ui.rutina

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import pe.pcs.controlasesorados.domain.model.Rutina
import pe.pcs.controlasesorados.domain.usecase.rutina.EliminarRutinaUseCase
import pe.pcs.controlasesorados.domain.usecase.rutina.ListarRutinaUseCase
import pe.pcs.controlasesorados.presentation.common.ResponseStatus
import pe.pcs.controlasesorados.presentation.common.makeCall
import javax.inject.Inject

@HiltViewModel
class RutinaViewModel @Inject constructor(
    private val listarRutinaUseCase: ListarRutinaUseCase,
    private val eliminarRutinaUseCase: EliminarRutinaUseCase
): ViewModel() {

    private val _lista = MutableStateFlow<List<Rutina>?>(emptyList())
    val lista: StateFlow<List<Rutina>?> = _lista

    private val _status =
        MutableStateFlow<ResponseStatus<List<Rutina>>?>(ResponseStatus.Loading())
    val status: StateFlow<ResponseStatus<List<Rutina>>?> = _status

    private val _statusDelete = MutableLiveData<ResponseStatus<Int>?>()
    val statusDelete: LiveData<ResponseStatus<Int>?> = _statusDelete

    fun resetStatus() {
        _status.value = null
    }

    fun resetStatusDelete() {
        _statusDelete.value = null
    }

    fun listar(dato: String) {
        viewModelScope.launch {
            _status.value = ResponseStatus.Loading()

            makeCall {
                listarRutinaUseCase(dato).collect {
                    _lista.value = ResponseStatus.Success(it).data
                    _status.value = ResponseStatus.Success(it)
                }
            }
        }
    }

    fun eliminar(model: Rutina) {
        viewModelScope.launch {
            _statusDelete.value = ResponseStatus.Loading()

            makeCall { eliminarRutinaUseCase(model) }.let {
                _statusDelete.value = it
            }
        }
    }

}