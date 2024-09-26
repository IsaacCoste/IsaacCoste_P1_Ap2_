package edu.ucne.isaaccoste_p1_ap2.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import edu.ucne.isaaccoste_p1_ap2.data.local.entities.VentaEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface VentaDao {
    @Upsert
    suspend fun save(algo: VentaEntity)
    @Query(
        """
        SELECT * FROM Ventas
        WHERE ventaId = :id 
        LIMIT 1
    """
    )
    suspend fun find(id: Int): VentaEntity?
    @Delete
    suspend fun delete(algo: VentaEntity)
    @Query("SELECT * FROM ventas")
    fun getAll(): Flow<List<VentaEntity>>
}