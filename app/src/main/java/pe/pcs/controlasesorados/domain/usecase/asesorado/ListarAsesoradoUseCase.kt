package pe.pcs.controlasesorados.domain.usecase.asesorado

import kotlinx.coroutines.flow.Flow
import pe.pcs.controlasesorados.domain.model.Asesorado
import pe.pcs.controlasesorados.domain.repository.AsesoradoRepository
import javax.inject.Inject

class ListarAsesoradoUseCase @Inject constructor(private val repository: AsesoradoRepository) {
    operator fun invoke(dato: String): Flow<List<Asesorado>> {
        return repository.listar(dato)
    }
}