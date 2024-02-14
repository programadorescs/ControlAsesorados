package pe.pcs.controlasesorados.presentation.ui.asesorado

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import pe.pcs.controlasesorados.domain.model.Asesorado
import pe.pcs.controlasesorados.domain.usecase.asesorado.EliminarAsesoradoUseCase
import pe.pcs.controlasesorados.domain.usecase.asesorado.ListarAsesoradoUseCase
import pe.pcs.controlasesorados.presentation.common.ResponseStatus
import pe.pcs.controlasesorados.presentation.common.makeCall
import javax.inject.Inject

@HiltViewModel
class AsesoradoViewModel @Inject constructor(
    private val listarAsesoradoUseCase: ListarAsesoradoUseCase,
    private val eliminarAsesoradoUseCase: EliminarAsesoradoUseCase
): ViewModel() {

    private val _lista = MutableStateFlow<List<Asesorado>?>(emptyList())
    val lista: StateFlow<List<Asesorado>?> = _lista

    private val _status =
        MutableStateFlow<ResponseStatus<List<Asesorado>>?>(null)
    val status: StateFlow<ResponseStatus<List<Asesorado>>?> = _status

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
                listarAsesoradoUseCase(dato).collect {
                    _lista.value = ResponseStatus.Success(it).data
                    _status.value = ResponseStatus.Success(it)
                }
            }
        }
    }

    fun eliminar(model: Asesorado) {
        viewModelScope.launch {
            _statusDelete.value = ResponseStatus.Loading()

            makeCall { eliminarAsesoradoUseCase(model) }.let {
                _statusDelete.value = it
            }
        }
    }

}