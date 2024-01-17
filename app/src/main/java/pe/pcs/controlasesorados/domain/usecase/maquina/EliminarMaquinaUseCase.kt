package pe.pcs.controlasesorados.domain.usecase.maquina

import pe.pcs.controlasesorados.domain.model.Maquina
import pe.pcs.controlasesorados.domain.repository.MaquinaRepository
import javax.inject.Inject

class EliminarMaquinaUseCase @Inject constructor(private val repository: MaquinaRepository) {
    suspend operator fun invoke(model: Maquina): Int {
        return repository.eliminar(model)
    }
}