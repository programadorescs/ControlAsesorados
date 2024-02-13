package pe.pcs.controlasesorados.domain.usecase.usuario

import kotlinx.coroutines.flow.Flow
import pe.pcs.controlasesorados.domain.model.Usuario
import pe.pcs.controlasesorados.domain.repository.UsuarioRepository
import javax.inject.Inject

class ListarUsuarioUseCase @Inject constructor(private val repository: UsuarioRepository) {

    operator fun invoke(dato: String): Flow<List<Usuario>> {
        return repository.listar(dato)
    }

}