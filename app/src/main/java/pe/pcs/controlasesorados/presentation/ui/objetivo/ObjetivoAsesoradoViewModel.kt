package pe.pcs.controlasesorados.presentation.ui.objetivo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import pe.pcs.controlasesorados.domain.model.Objetivo
import pe.pcs.controlasesorados.domain.usecase.objetivo.EliminarObjetivoUseCase
import pe.pcs.controlasesorados.domain.usecase.objetivo.FinalizarObjetivoUseCase
import pe.pcs.controlasesorados.domain.usecase.objetivo.ObtenerObjetivoAsesoradoUseCase
import pe.pcs.controlasesorados.presentation.common.ResponseStatus
import pe.pcs.controlasesorados.presentation.common.makeCall
import javax.inject.Inject

@HiltViewModel
class ObjetivoAsesoradoViewModel @Inject constructor(
    private val obtenerObjetivoAsesoradoUseCase: ObtenerObjetivoAsesoradoUseCase,
    private val eliminarObjetivoUseCase: EliminarObjetivoUseCase,
    private val finalizarObjetivoUseCase: FinalizarObjetivoUseCase
) : ViewModel() {

    private val _lista = MutableStateFlow<List<Objetivo>>(emptyList())
    val lista: StateFlow<List<Objetivo>> = _lista

    private val _statusObjetivo =
        MutableStateFlow<ResponseStatus<List<Objetivo>>?>(null)
    val statusObjetivo: StateFlow<ResponseStatus<List<Objetivo>>?> = _statusObjetivo

    private val _statusDelete = MutableLiveData<ResponseStatus<Int>?>()
    val statusDelete: LiveData<ResponseStatus<Int>?> = _statusDelete

    private val _statusFinalizar = MutableLiveData<ResponseStatus<Int>?>()
    val statusFinalizar: LiveData<ResponseStatus<Int>?> = _statusFinalizar

    fun resetStatus() {
        _statusObjetivo.value = null
    }

    fun resetStatusDelete() {
        _statusDelete.value = null
    }

    fun resetStatusFinalizar() {
        _statusFinalizar.value = null
    }

    fun obtenerObjetivosAsesorado(idAsesorado: Int) {
        viewModelScope.launch {
            _statusObjetivo.value = ResponseStatus.Loading()

            makeCall {
                obtenerObjetivoAsesoradoUseCase(idAsesorado).collect {
                    _lista.value = ResponseStatus.Success(it).data
                    _statusObjetivo.value = ResponseStatus.Success(it)
                }
            }
        }
    }

    fun eliminar(model: Objetivo) {
        viewModelScope.launch {
            _statusDelete.value = ResponseStatus.Loading()

            makeCall { eliminarObjetivoUseCase(model) }.let {
                _statusDelete.value = it
            }
        }
    }

    fun finalizar(model: Objetivo) {
        viewModelScope.launch {
            _statusFinalizar.value = ResponseStatus.Loading()

            makeCall { finalizarObjetivoUseCase(model.id) }.let {
                _statusFinalizar.value = it
            }
        }
    }

}