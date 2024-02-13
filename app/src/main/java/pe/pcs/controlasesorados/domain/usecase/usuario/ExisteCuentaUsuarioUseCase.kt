package pe.pcs.controlasesorados.domain.usecase.usuario

import pe.pcs.controlasesorados.domain.repository.UsuarioRepository
import javax.inject.Inject

class ExisteCuentaUsuarioUseCase @Inject constructor(private val repository: UsuarioRepository) {

    suspend operator fun invoke(): Int {
        return repository.existeCuenta()
    }

}