package pe.pcs.controlasesorados.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "asesorado",
    indices = [Index(value = ["dni"], unique = true)]
)
data class AsesoradoEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") var id: Int = 0,
    @ColumnInfo(name = "nombre") var nombre: String = "",
    @ColumnInfo(name = "dni") var dni: String = "",
    @ColumnInfo(name = "fecnac") var fecnac: String = "",
    @ColumnInfo(name = "sexo") var sexo: String = "",
    @ColumnInfo(name = "direccion") var direccion: String = "",
    @ColumnInfo(name = "telefono") var telefono: String = ""
)
