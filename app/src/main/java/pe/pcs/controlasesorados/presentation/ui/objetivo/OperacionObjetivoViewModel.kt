package pe.pcs.controlasesorados.presentation.ui.objetivo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import pe.pcs.controlasesorados.domain.model.Objetivo
import pe.pcs.controlasesorados.domain.usecase.objetivo.GrabarObjetivoUseCase
import pe.pcs.controlasesorados.presentation.common.ResponseStatus
import pe.pcs.controlasesorados.presentation.common.makeCall
import javax.inject.Inject

@HiltViewModel
class OperacionObjetivoViewModel @Inject constructor(
    private val grabarObjetivoUseCase: GrabarObjetivoUseCase
) : ViewModel() {

    private val _state = MutableLiveData<ResponseStatus<Int>?>()
    val state: LiveData<ResponseStatus<Int>?> = _state

    fun resetState() {
        _state.value = null
    }

    fun grabar(entidad: Objetivo) {
        viewModelScope.launch {
            _state.value = ResponseStatus.Loading()

            makeCall { grabarObjetivoUseCase(entidad) }.let {
                _state.value = it
            }
        }
    }

}