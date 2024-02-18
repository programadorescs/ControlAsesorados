package pe.pcs.controlasesorados.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import pe.pcs.controlasesorados.domain.model.Objetivo

@Entity(
    tableName = "objetivo",
    indices = [Index(value = ["descripcion"], unique = true)],
    foreignKeys = [
        ForeignKey(
            entity = AsesoradoEntity::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("idasesorado"),
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class ObjetivoEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") var id: Int = 0,
    @ColumnInfo(name = "fecha") var fecha: String = "",
    @ColumnInfo(name = "descripcion") var descripcion: String = "",
    @ColumnInfo(name = "obs") var obs: String = "",
    @ColumnInfo(name = "estado") var estado: String = "",
    @ColumnInfo(name = "idasesorado") var idasesorado: Int = 0
)

fun Objetivo.toDatabase() = ObjetivoEntity(
    id = id,
    fecha = fecha,
    descripcion = descripcion,
    obs = obs,
    estado = estado,
    idasesorado = idAsesorado
)