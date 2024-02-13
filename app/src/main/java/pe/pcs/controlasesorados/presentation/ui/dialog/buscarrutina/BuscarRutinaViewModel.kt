package pe.pcs.controlasesorados.presentation.ui.dialog.buscarrutina

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import pe.pcs.controlasesorados.domain.model.Rutina
import pe.pcs.controlasesorados.domain.usecase.rutina.ListarRutinaUseCase
import pe.pcs.controlasesorados.presentation.common.ResponseStatus
import pe.pcs.controlasesorados.presentation.common.makeCall
import javax.inject.Inject

@HiltViewModel
class BuscarRutinaViewModel @Inject constructor(
    private val listarRutinaUseCase: ListarRutinaUseCase
): ViewModel() {

    private val _listaRutina = MutableStateFlow<List<Rutina>?>(null)
    val listaRutina: StateFlow<List<Rutina>?> = _listaRutina

    private val _stateListaRutina =
        MutableStateFlow<ResponseStatus<List<Rutina>?>>(ResponseStatus.Loading())
    val stateListaRutina: StateFlow<ResponseStatus<List<Rutina>?>> = _stateListaRutina

    fun listarRutina(dato: String) {
        viewModelScope.launch {
            _stateListaRutina.value = ResponseStatus.Loading()

            makeCall {
                listarRutinaUseCase(dato).collect {
                    _listaRutina.value = ResponseStatus.Success(it).data
                    _stateListaRutina.value = ResponseStatus.Success(it)
                }
            }
        }
    }

    /*
    private val _listaRutina = MutableStateFlow<List<Rutina>?>(null)
    val listaRutina: StateFlow<List<Rutina>?> = _listaRutina

    private val _stateListaRutina =
        MutableStateFlow<ResponseStatus<List<Rutina>?>>(ResponseStatus.Loading())
    val stateListaRutina: StateFlow<ResponseStatus<List<Rutina>?>> = _stateListaRutina

    fun listarRutina(dato: String) {
        viewModelScope.launch {
            _stateListaRutina.value = ResponseStatus.Loading()

            makeCall {
                listarRutinaUseCase(dato).collect {
                    _listaRutina.value = ResponseStatus.Success(it).data
                    _stateListaRutina.value = ResponseStatus.Success(it)
                }
            }
        }
    }
     */

}