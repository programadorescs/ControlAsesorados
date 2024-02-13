package pe.pcs.controlasesorados.domain.usecase.rutina

import kotlinx.coroutines.flow.Flow
import pe.pcs.controlasesorados.domain.model.Rutina
import pe.pcs.controlasesorados.domain.repository.RutinaRepository
import javax.inject.Inject

class ListarRutinaUseCase @Inject constructor(private val repository: RutinaRepository) {
    operator fun invoke(dato: String): Flow<List<Rutina>> {
        return repository.listar(dato)
    }
}