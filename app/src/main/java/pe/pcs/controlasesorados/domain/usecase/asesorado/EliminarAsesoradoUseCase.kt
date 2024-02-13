package pe.pcs.controlasesorados.domain.usecase.asesorado

import pe.pcs.controlasesorados.domain.model.Asesorado
import pe.pcs.controlasesorados.domain.repository.AsesoradoRepository
import javax.inject.Inject

class EliminarAsesoradoUseCase @Inject constructor(private val repository: AsesoradoRepository) {
    suspend operator fun invoke(model: Asesorado): Int {
        return repository.eliminar(model)
    }
}