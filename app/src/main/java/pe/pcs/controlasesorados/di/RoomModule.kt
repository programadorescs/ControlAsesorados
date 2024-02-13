package pe.pcs.controlasesorados.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import pe.pcs.controlasesorados.data.dao.AsesoradoDao
import pe.pcs.controlasesorados.data.dao.EjercicioDao
import pe.pcs.controlasesorados.data.dao.MaquinaDao
import pe.pcs.controlasesorados.data.dao.RutinaDao
import pe.pcs.controlasesorados.data.dao.UsuarioDao
import pe.pcs.controlasesorados.data.database.AppDatabase
import pe.pcs.controlasesorados.data.repository.AsesoradoRepositoryImpl
import pe.pcs.controlasesorados.data.repository.EjercicioRepositoryImpl
import pe.pcs.controlasesorados.data.repository.MaquinaRepositoryImpl
import pe.pcs.controlasesorados.data.repository.RutinaRepositoryImpl
import pe.pcs.controlasesorados.data.repository.UsuarioRepositoryImpl
import pe.pcs.controlasesorados.domain.repository.AsesoradoRepository
import pe.pcs.controlasesorados.domain.repository.EjercicioRepository
import pe.pcs.controlasesorados.domain.repository.MaquinaRepository
import pe.pcs.controlasesorados.domain.repository.RutinaRepository
import pe.pcs.controlasesorados.domain.repository.UsuarioRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    private const val DATABASE_NAME = "asesorados_db"

    @Singleton
    @Provides
    fun provideRoom(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME).build()
    }

    //******** Proveer el dao ********//

    @Singleton
    @Provides
    fun provideMaquinaDao(db: AppDatabase): MaquinaDao {
        return db.maquinaDao()
    }

    @Singleton
    @Provides
    fun provideUsuarioDao(db: AppDatabase): UsuarioDao {
        return db.usuarioDao()
    }

    @Singleton
    @Provides
    fun provideRutinaDao(db: AppDatabase): RutinaDao {
        return db.rutinaDao()
    }

    @Singleton
    @Provides
    fun provideEjercicioDao(db: AppDatabase): EjercicioDao {
        return db.ejercicioDao()
    }

    @Singleton
    @Provides
    fun provideAsesoradoDao(db: AppDatabase): AsesoradoDao {
        return db.asesoradoDao()
    }

    //******** Proveer el repository ********//

    @Singleton
    @Provides
    fun provideProductoRepository(dao: MaquinaDao): MaquinaRepository {
        return MaquinaRepositoryImpl(dao)
    }

    @Singleton
    @Provides
    fun provideUsuarioRepository(dao: UsuarioDao): UsuarioRepository {
        return UsuarioRepositoryImpl(dao)
    }

    @Singleton
    @Provides
    fun provideRutinaRepository(dao: RutinaDao): RutinaRepository {
        return RutinaRepositoryImpl(dao)
    }

    @Singleton
    @Provides
    fun provideEjercicioRepository(dao: EjercicioDao): EjercicioRepository {
        return EjercicioRepositoryImpl(dao)
    }

    @Singleton
    @Provides
    fun provideAsesoradoRepository(dao: AsesoradoDao): AsesoradoRepository {
        return AsesoradoRepositoryImpl(dao)
    }

}