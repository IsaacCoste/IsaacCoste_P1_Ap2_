package edu.ucne.isaaccoste_p1_ap2.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import edu.ucne.isaaccoste_p1_ap2.data.local.dao.VentaDao
import edu.ucne.isaaccoste_p1_ap2.data.local.entities.VentaEntity

@Database(
    version = 2,
    exportSchema = false,
    entities = [VentaEntity::class]
)
abstract class Parcial1Db: RoomDatabase() {
    abstract fun ventaDao(): VentaDao
}