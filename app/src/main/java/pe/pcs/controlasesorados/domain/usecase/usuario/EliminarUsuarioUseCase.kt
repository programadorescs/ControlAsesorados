package pe.pcs.controlasesorados.domain.usecase.usuario

import pe.pcs.controlasesorados.domain.model.Usuario
import pe.pcs.controlasesorados.domain.repository.UsuarioRepository
import javax.inject.Inject

class EliminarUsuarioUseCase @Inject constructor(private val repository: UsuarioRepository) {

    suspend operator fun invoke(entidad: Usuario): Int {
        return repository.eliminar(entidad)
    }

}