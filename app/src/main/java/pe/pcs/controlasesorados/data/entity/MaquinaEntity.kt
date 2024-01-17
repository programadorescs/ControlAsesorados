package pe.pcs.controlasesorados.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import pe.pcs.controlasesorados.domain.model.Maquina

@Entity(
    tableName = "maquina",
    indices = [Index(value = ["descripcion"], unique = true)]
)
data class MaquinaEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") var id: Int = 0,
    @ColumnInfo(name = "descripcion") var descripcion: String = ""
)

fun Maquina.toDatabase() = MaquinaEntity(
    id = id,
    descripcion = descripcion
)