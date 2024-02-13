package pe.pcs.controlasesorados.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import pe.pcs.controlasesorados.domain.model.Ejercicio

@Entity(
    tableName = "ejercicio",
    indices = [Index(value = ["descripcion"], unique = true)],
    foreignKeys = [
        ForeignKey(
            entity = RutinaEntity::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("idrutina"),
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = MaquinaEntity::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("idmaquina"),
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class EjercicioEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") var id: Int = 0,
    @ColumnInfo(name = "descripcion") var descripcion: String = "",
    @ColumnInfo(name = "idrutina") var idrutina: Int = 0,
    @ColumnInfo(name = "idmaquina") var idmaquina: Int = 0
)

fun Ejercicio.toDatabase() = EjercicioEntity(
    id = id,
    descripcion = descripcion,
    idrutina = idrutina,
    idmaquina = idmaquina
)