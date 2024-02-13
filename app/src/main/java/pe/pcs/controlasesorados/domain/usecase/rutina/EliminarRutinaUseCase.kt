package pe.pcs.controlasesorados.domain.usecase.rutina

import pe.pcs.controlasesorados.domain.model.Rutina
import pe.pcs.controlasesorados.domain.repository.RutinaRepository
import javax.inject.Inject

class EliminarRutinaUseCase @Inject constructor(private val repository: RutinaRepository) {
    suspend operator fun invoke(model: Rutina): Int {
        return repository.eliminar(model)
    }
}