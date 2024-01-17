package pe.pcs.controlasesorados.domain.usecase.maquina

import pe.pcs.controlasesorados.domain.model.Maquina
import pe.pcs.controlasesorados.domain.repository.MaquinaRepository
import javax.inject.Inject

class ObtenerMaquinaUseCase @Inject constructor(private val repository: MaquinaRepository) {
    suspend operator fun invoke(id: Int): Maquina? {
        return repository.obtenerPorId(id)
    }
}