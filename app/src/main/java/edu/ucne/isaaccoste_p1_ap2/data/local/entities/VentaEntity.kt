package edu.ucne.isaaccoste_p1_ap2.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Ventas")
data class VentaEntity(
    @PrimaryKey
    val ventaId: Int? = null,
    val DatoCliente: String = "",
    val galones: Double = 0.0,
    val descuentoGalon: Double = 0.0,
    val precio: Double = 0.0,
    val totalDescuento: Double = 0.0,
    val total: Double = 0.0
)
