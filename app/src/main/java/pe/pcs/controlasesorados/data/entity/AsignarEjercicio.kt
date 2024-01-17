package pe.pcs.controlasesorados.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "asignar_ejercicio",
    foreignKeys = [
        ForeignKey(
            entity = EjercicioEntity::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("idejercicio"),
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = ObjetivoEntity::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("idobjetivo"),
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class AsignarEjercicio(
    @ColumnInfo(name = "id") var id: Int = 0,
    @ColumnInfo(name = "dia") var dia: String = "",
    @ColumnInfo(name = "serie") var serie: String = "",
    @ColumnInfo(name = "repeticion") var repeticion: String = "",
    @ColumnInfo(name = "descanso") var descanso: String = "",
    @ColumnInfo(name = "idejercicio") var idejercicio: Int = 0,
    @ColumnInfo(name = "idobjetivo") var idobjetivo: Int = 0
)
