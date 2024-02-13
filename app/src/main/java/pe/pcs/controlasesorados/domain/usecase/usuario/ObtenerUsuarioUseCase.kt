package pe.pcs.controlasesorados.domain.usecase.usuario

import pe.pcs.controlasesorados.domain.model.Usuario
import pe.pcs.controlasesorados.domain.repository.UsuarioRepository
import javax.inject.Inject

class ObtenerUsuarioUseCase @Inject constructor(private val repository: UsuarioRepository) {

    suspend operator fun invoke(email: String, clave: String): Usuario? {
        return repository.obtenerUsuario(email, clave)
    }

}