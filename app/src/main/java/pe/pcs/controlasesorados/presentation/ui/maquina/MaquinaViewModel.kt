package pe.pcs.controlasesorados.presentation.ui.maquina

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import pe.pcs.controlasesorados.domain.model.Maquina
import pe.pcs.controlasesorados.domain.usecase.maquina.EliminarMaquinaUseCase
import pe.pcs.controlasesorados.domain.usecase.maquina.ListarMaquinaUseCase
import pe.pcs.controlasesorados.presentation.common.ResponseStatus
import pe.pcs.controlasesorados.presentation.common.makeCall
import javax.inject.Inject

@HiltViewModel
class MaquinaViewModel @Inject constructor(
    private val listarMaquinaUseCase: ListarMaquinaUseCase,
    private val eliminarMaquinaUseCase: EliminarMaquinaUseCase
) : ViewModel() {

    private val _lista = MutableStateFlow<List<Maquina>?>(emptyList())
    val lista: StateFlow<List<Maquina>?> = _lista

    private val _status =
        MutableStateFlow<ResponseStatus<List<Maquina>>?>(ResponseStatus.Loading())
    val status: StateFlow<ResponseStatus<List<Maquina>>?> = _status

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
                listarMaquinaUseCase(dato).collect {
                    _lista.value = ResponseStatus.Success(it).data
                    _status.value = ResponseStatus.Success(it)
                }
            }
        }
    }

    fun eliminar(model: Maquina) {
        viewModelScope.launch {
            _statusDelete.value = ResponseStatus.Loading()

            makeCall { eliminarMaquinaUseCase(model) }.let {
                _statusDelete.value = it
            }
        }
    }

}