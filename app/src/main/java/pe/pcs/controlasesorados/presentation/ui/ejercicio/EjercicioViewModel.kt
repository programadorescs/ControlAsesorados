package pe.pcs.controlasesorados.presentation.ui.ejercicio

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import pe.pcs.controlasesorados.domain.model.Ejercicio
import pe.pcs.controlasesorados.domain.model.report.ReporteEjercicioModel
import pe.pcs.controlasesorados.domain.usecase.ejercicio.EliminarEjercicioUseCase
import pe.pcs.controlasesorados.domain.usecase.ejercicio.ListarEjercicioUseCase
import pe.pcs.controlasesorados.presentation.common.ResponseStatus
import pe.pcs.controlasesorados.presentation.common.makeCall
import javax.inject.Inject

@HiltViewModel
class EjercicioViewModel @Inject constructor(
    private val listarEjercicioUseCase: ListarEjercicioUseCase,
    private val eliminarEjercicioUseCase: EliminarEjercicioUseCase
): ViewModel() {

    private val _lista = MutableStateFlow<List<ReporteEjercicioModel>?>(emptyList())
    val lista: StateFlow<List<ReporteEjercicioModel>?> = _lista

    private val _status =
        MutableStateFlow<ResponseStatus<List<ReporteEjercicioModel>>?>(ResponseStatus.Loading())
    val status: StateFlow<ResponseStatus<List<ReporteEjercicioModel>>?> = _status

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
                listarEjercicioUseCase(dato).collect {
                    _lista.value = ResponseStatus.Success(it).data
                    _status.value = ResponseStatus.Success(it)
                }
            }
        }
    }

    fun eliminar(model: Ejercicio) {
        viewModelScope.launch {
            _statusDelete.value = ResponseStatus.Loading()

            makeCall { eliminarEjercicioUseCase(model) }.let {
                _statusDelete.value = it
            }
        }
    }

}