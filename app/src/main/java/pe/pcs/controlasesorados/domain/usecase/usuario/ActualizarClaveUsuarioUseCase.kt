package pe.pcs.controlasesorados.domain.usecase.usuario

import pe.pcs.controlasesorados.domain.repository.UsuarioRepository
import javax.inject.Inject

class ActualizarClaveUsuarioUseCase @Inject constructor(private val repository: UsuarioRepository) {

    suspend operator fun invoke(idUsuario: Int, nuevaClave: String): Int {
        return repository.actualizarClave(idUsuario, nuevaClave)
    }

}