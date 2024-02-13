package pe.pcs.controlasesorados.presentation.ui.crearcuenta

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import pe.pcs.controlasesorados.domain.model.Usuario
import pe.pcs.controlasesorados.domain.usecase.usuario.GrabarCuentaUsuarioUseCase
import pe.pcs.controlasesorados.presentation.common.PreferencesProvider
import pe.pcs.controlasesorados.presentation.common.ResponseStatus
import pe.pcs.controlasesorados.presentation.common.makeCall
import javax.inject.Inject

@HiltViewModel
class CrearCuentaViewModel @Inject constructor(
    private val grabarCuentaUsuarioUseCase: GrabarCuentaUsuarioUseCase
) : ViewModel() {

    private var _item = MutableLiveData<Usuario?>()
    val item: LiveData<Usuario?> = _item

    private val _status = MutableLiveData<ResponseStatus<Usuario?>?>()
    val status: LiveData<ResponseStatus<Usuario?>?> = _status

    private fun handleResponseStatus(responseStatus: ResponseStatus<Usuario?>) {
        if (responseStatus is ResponseStatus.Success) {
            _item.value = responseStatus.data
        }

        _status.value = responseStatus
    }

    fun resetStatus() {
        _status.value = null
    }

    fun grabarCuenta(entidad: Usuario) {
        viewModelScope.launch {
            _status.value = ResponseStatus.Loading()

            makeCall { grabarCuentaUsuarioUseCase(entidad) }.let {
                if (it is ResponseStatus.Success)
                    _item.value = it.data

                _status.value = it
            }

            PreferencesProvider.setUsuarioLogin(
                entidad.email.trim()
            )
        }
    }
}