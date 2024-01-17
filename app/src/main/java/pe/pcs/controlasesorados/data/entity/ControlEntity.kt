package pe.pcs.controlasesorados.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "controles",
    foreignKeys = [
        ForeignKey(
            entity = ObjetivoEntity::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("idobjetivo"),
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class ControlEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") var id: Int = 0,
    @ColumnInfo(name = "fecha") var fecha: String = "",
    @ColumnInfo(name = "talla") var talla: Double = 0.0,
    @ColumnInfo(name = "peso") var peso: Double = 0.0,
    @ColumnInfo(name = "cintura") var cintura: Double = 0.0,
    @ColumnInfo(name = "cadera") var cadera: Double = 0.0,
    @ColumnInfo(name = "pecho") var pecho: Double = 0.0,
    @ColumnInfo(name = "brazo") var brazo: Double = 0.0,
    @ColumnInfo(name = "muslo") var muslo: Double = 0.0,
    @ColumnInfo(name = "pantorrilla") var pantorrilla: Double = 0.0,
    @ColumnInfo(name = "idobjetivo") var idobjetivo: Int = 0
)
