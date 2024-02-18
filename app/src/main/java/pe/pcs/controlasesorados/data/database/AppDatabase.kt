package pe.pcs.controlasesorados.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import pe.pcs.controlasesorados.data.dao.AsesoradoDao
import pe.pcs.controlasesorados.data.dao.EjercicioDao
import pe.pcs.controlasesorados.data.dao.MaquinaDao
import pe.pcs.controlasesorados.data.dao.ObjetivoDao
import pe.pcs.controlasesorados.data.dao.RutinaDao
import pe.pcs.controlasesorados.data.dao.UsuarioDao
import pe.pcs.controlasesorados.data.entity.AsesoradoEntity
import pe.pcs.controlasesorados.data.entity.AsignarEjercicio
import pe.pcs.controlasesorados.data.entity.ControlEntity
import pe.pcs.controlasesorados.data.entity.EjercicioEntity
import pe.pcs.controlasesorados.data.entity.MaquinaEntity
import pe.pcs.controlasesorados.data.entity.ObjetivoEntity
import pe.pcs.controlasesorados.data.entity.RutinaEntity
import pe.pcs.controlasesorados.data.entity.UsuarioEntity

@Database(
    entities = [
        AsesoradoEntity::class,
        AsignarEjercicio::class,
        ControlEntity::class,
        EjercicioEntity::class,
        MaquinaEntity::class,
        ObjetivoEntity::class,
        RutinaEntity::class,
        UsuarioEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun rutinaDao(): RutinaDao

    abstract fun maquinaDao(): MaquinaDao

    abstract fun asesoradoDao(): AsesoradoDao

    abstract fun ejercicioDao(): EjercicioDao

    abstract fun usuarioDao(): UsuarioDao

    abstract fun objetivoDao(): ObjetivoDao

}