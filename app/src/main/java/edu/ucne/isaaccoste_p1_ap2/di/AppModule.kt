package edu.ucne.isaaccoste_p1_ap2.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import edu.ucne.isaaccoste_p1_ap2.data.local.database.Parcial1Db
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {
    @Provides
    @Singleton
    fun provideParcial1DB(
        @ApplicationContext appContext: Context
    ) = Room.databaseBuilder(
        appContext,
        Parcial1Db::class.java,
        "Parcial1.Db"
    ).fallbackToDestructiveMigration()
        .build()

@Provides
@Singleton
fun provideAlgoDao(parcial1Db: Parcial1Db) = parcial1Db.ventaDao()

}