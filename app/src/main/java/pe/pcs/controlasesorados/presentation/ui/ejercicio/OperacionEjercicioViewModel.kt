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
import pe.pcs.controlasesorados.domain.model.Maquina
import pe.pcs.controlasesorados.domain.model.Rutina
import pe.pcs.controlasesorados.domain.model.report.ReporteEjercicioModel
import pe.pcs.controlasesorados.domain.usecase.ejercicio.GrabarEjercicioUseCase
import pe.pcs.controlasesorados.domain.usecase.ejercicio.ObtenerEjercicioUseCase
import pe.pcs.controlasesorados.domain.usecase.maquina.ListarMaquinaUseCase
import pe.pcs.controlasesorados.domain.usecase.rutina.ListarRutinaUseCase
import pe.pcs.controlasesorados.presentation.common.ResponseStatus
import pe.pcs.controlasesorados.presentation.common.makeCall
import javax.inject.Inject

@HiltViewModel
class OperacionEjercicioViewModel @Inject constructor(
    private val grabarEjercicioUseCase: GrabarEjercicioUseCase,
    private val obtenerEjercicioUseCase: ObtenerEjercicioUseCase
) : ViewModel() {

    private val _itemEjercicio = MutableLiveData<ReporteEjercicioModel?>(null)
    val itemEjercicio: LiveData<ReporteEjercicioModel?> = _itemEjercicio

    private val _itemMaquina = MutableLiveData<Maquina?>(null)
    val itemMaquina: LiveData<Maquina?> = _itemMaquina

    private val _itemRutina = MutableLiveData<Rutina?>(null)
    val itemRutina: LiveData<Rutina?> = _itemRutina

    private val _stateObtenerEjercicio =
        MutableStateFlow<ResponseStatus<ReporteEjercicioModel?>?>(null)
    val stateObtenerEjercicio: StateFlow<ResponseStatus<ReporteEjercicioModel?>?> =
        _stateObtenerEjercicio

    private val _stateGrabar = MutableStateFlow<ResponseStatus<Int>?>(null)
    val stateGrabar: StateFlow<ResponseStatus<Int>?> = _stateGrabar

    fun resetItem() {
        _itemEjercicio.value = null
    }

    fun setRutina(entidad: Rutina?) {
        _itemRutina.value = entidad
    }

    fun setMaquina(entidad: Maquina?) {
        _itemMaquina.value = entidad
    }

    fun obtenerEjercicio(id: Int) {
        viewModelScope.launch {
            _stateObtenerEjercicio.value = ResponseStatus.Loading()

            makeCall { obtenerEjercicioUseCase(id) }.let {
                if (it is ResponseStatus.Success)
                    _itemEjercicio.value = it.data

                _stateObtenerEjercicio.value = it
            }
        }
    }

    fun grabar(entidad: Ejercicio) {
        viewModelScope.launch {
            _stateGrabar.value = ResponseStatus.Loading()

            makeCall { grabarEjercicioUseCase(entidad) }.let {
                _stateGrabar.value = it
            }
        }
    }

}