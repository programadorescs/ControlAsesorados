package pe.pcs.controlasesorados.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import pe.pcs.controlasesorados.domain.model.Rutina

@Entity(
    tableName = "rutina",
    indices = [Index(value = ["descripcion"], unique = true)]
)
data class RutinaEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") var id: Int = 0,
    @ColumnInfo(name = "descripcion") var descripcion: String = ""
)

fun Rutina.toDatabase() = RutinaEntity(
    id = id,
    descripcion = descripcion
)