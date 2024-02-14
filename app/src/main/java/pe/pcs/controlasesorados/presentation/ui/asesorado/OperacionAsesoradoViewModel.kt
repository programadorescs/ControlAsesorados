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
import pe.pcs.controlasesorados.domain.usecase.asesorado.GrabarAsesoradoUseCase
import pe.pcs.controlasesorados.domain.usecase.asesorado.ObtenerAsesoradoUseCase
import pe.pcs.controlasesorados.presentation.common.ResponseStatus
import pe.pcs.controlasesorados.presentation.common.makeCall
import javax.inject.Inject

@HiltViewModel
class OperacionAsesoradoViewModel @Inject constructor(
    private val grabarAsesoradoUseCase: GrabarAsesoradoUseCase,
    private val obtenerAsesoradoUseCase: ObtenerAsesoradoUseCase
): ViewModel() {

    private val _itemAsesorado= MutableLiveData<Asesorado?>(null)
    val itemAsesorado: LiveData<Asesorado?> = _itemAsesorado

    private val _stateObtenerAsesorado = MutableStateFlow<ResponseStatus<Asesorado?>?>(null)
    val stateObtenerAsesorado: StateFlow<ResponseStatus<Asesorado?>?> = _stateObtenerAsesorado

    private val _stateGrabar = MutableStateFlow<ResponseStatus<Int>?>(null)
    val stateGrabar: StateFlow<ResponseStatus<Int>?> = _stateGrabar

    fun resetItem() {
        _itemAsesorado.value = null
    }

    fun obtenerAsesorado(id: Int) {
        viewModelScope.launch {
            _stateObtenerAsesorado.value = ResponseStatus.Loading()

            makeCall { obtenerAsesoradoUseCase(id) }.let {
                if (it is ResponseStatus.Success)
                    _itemAsesorado.value = it.data

                _stateObtenerAsesorado.value = it
            }
        }
    }

    fun grabar(entidad: Asesorado) {
        viewModelScope.launch {
            _stateGrabar.value = ResponseStatus.Loading()

            makeCall { grabarAsesoradoUseCase(entidad) }.let {
                _stateGrabar.value = it
            }
        }
    }

}