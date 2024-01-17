package pe.pcs.controlasesorados.domain.usecase.maquina

import kotlinx.coroutines.flow.Flow
import pe.pcs.controlasesorados.domain.model.Maquina
import pe.pcs.controlasesorados.domain.repository.MaquinaRepository
import javax.inject.Inject

class ListarMaquinaUseCase @Inject constructor(private val repository: MaquinaRepository) {
    operator fun invoke(dato: String): Flow<List<Maquina>> {
        return repository.listar(dato)
    }
}