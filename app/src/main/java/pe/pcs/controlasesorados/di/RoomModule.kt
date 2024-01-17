package pe.pcs.controlasesorados.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import pe.pcs.controlasesorados.data.dao.MaquinaDao
import pe.pcs.controlasesorados.data.database.AppDatabase
import pe.pcs.controlasesorados.data.repository.MaquinaRepositoryImpl
import pe.pcs.controlasesorados.domain.repository.MaquinaRepository
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

    //******** Proveer el repository ********//

    @Singleton
    @Provides
    fun provideProductoRepository(dao: MaquinaDao): MaquinaRepository {
        return MaquinaRepositoryImpl(dao)
    }

}