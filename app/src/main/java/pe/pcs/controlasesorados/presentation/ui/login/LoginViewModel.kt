package pe.pcs.controlasesorados.presentation.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import pe.pcs.controlasesorados.domain.model.Usuario
import pe.pcs.controlasesorados.domain.usecase.usuario.ExisteCuentaUsuarioUseCase
import pe.pcs.controlasesorados.domain.usecase.usuario.ObtenerUsuarioUseCase
import pe.pcs.controlasesorados.presentation.common.PreferencesKey
import pe.pcs.controlasesorados.presentation.common.PreferencesProvider
import pe.pcs.controlasesorados.presentation.common.ResponseStatus
import pe.pcs.controlasesorados.presentation.common.makeCall
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val obtenerUsuarioUseCase: ObtenerUsuarioUseCase,
    private val existeCuentaUsuarioUseCase: ExisteCuentaUsuarioUseCase
) : ViewModel() {

    private var _item = MutableLiveData<Usuario?>()
    val item: LiveData<Usuario?> = _item

    private var _cantidad = MutableLiveData<Int?>()
    val cantidad: LiveData<Int?> = _cantidad

    val emailLogin = MutableLiveData<String?>()

    private val _status = MutableLiveData<ResponseStatus<Usuario?>?>()
    val status: LiveData<ResponseStatus<Usuario?>?> = _status

    private val _statusInt = MutableLiveData<ResponseStatus<Int>?>()
    val statusInt: LiveData<ResponseStatus<Int>?> = _statusInt

    fun resetStatus() {
        _status.value = null
    }

    fun resetStatusInt() {
        _statusInt.value = null
    }

    fun obtenerUsuario(email: String, clave: String) {
        viewModelScope.launch {
            _status.value = ResponseStatus.Loading()

            makeCall { obtenerUsuarioUseCase(email, clave) }.let {
                if (it is ResponseStatus.Success)
                    _item.value = it.data

                _status.value = it
            }

            PreferencesProvider.setUsuarioLogin(
                item.value?.email.toString()
            )
        }
    }

    fun existeCuenta() {
        viewModelScope.launch {
            _statusInt.value = ResponseStatus.Loading()

            makeCall { existeCuentaUsuarioUseCase() }.let {
                if (it is ResponseStatus.Success)
                    _cantidad.value = it.data

                _statusInt.value = it
            }
        }
    }

    fun leerEmailLogin() {
        viewModelScope.launch {
            emailLogin.value = PreferencesProvider.getPreferencia(PreferencesKey.USER_LOGIN)
        }
    }

}