package pe.pcs.controlasesorados.domain.usecase.rutina

import pe.pcs.controlasesorados.domain.model.Rutina
import pe.pcs.controlasesorados.domain.repository.RutinaRepository
import javax.inject.Inject

class ObtenerRutinaUseCase @Inject constructor(private val repository: RutinaRepository) {
    suspend operator fun invoke(id: Int): Rutina? {
        return repository.obtenerPorId(id)
    }
}