package edu.ucne.isaaccoste_p1_ap2.presentation.venta

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.isaaccoste_p1_ap2.data.local.entities.VentaEntity
import edu.ucne.isaaccoste_p1_ap2.data.repository.VentaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VentaViewModel @Inject constructor(
    private val ventaRepository: VentaRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    init {
        getVentas()
    }

    fun save() {
        viewModelScope.launch {
            if (validate()) {
                ventaRepository.save(_uiState.value.ToEntity())
                nuevo()
            }
        }
    }

    private fun validate(): Boolean {
        var isValid = true
        if (_uiState.value.DatoCliente.isNullOrBlank()) {
            _uiState.update {
                it.copy(errorDatoCliente = "El nombre del cliente es obligatorio")
            }
            isValid = false
        } else {
            _uiState.update {
                it.copy(errorDatoCliente = null)
            }
        }
        if (_uiState.value.galones <= 0.0) {
            _uiState.update {
                it.copy(errorGalones = "El número de galones es obligatorio")
            }
            isValid = false
        } else {
            _uiState.update {
                it.copy(errorGalones = null)
            }
        }
        if (_uiState.value.descuentoGalon < 0.0 || _uiState.value.descuentoGalon >= _uiState.value.precio) {
            _uiState.update {
                it.copy(errorDescuentoGalon = "El descuento por galón no puede ser mayor al precio")
            }
            isValid = false
        } else {
            _uiState.update {
                it.copy(errorDescuentoGalon = null)
            }
        }
        if (_uiState.value.precio <= 0.0 || _uiState.value.precio < _uiState.value.descuentoGalon) {
            _uiState.update {
                it.copy(errorPrecio = "El precio es obligatorio y debe ser mayor que el descuento por galón")
            }
            isValid = false
        } else {
            _uiState.update {
                it.copy(errorPrecio = null)
            }
        }
        return isValid
    }
    fun nuevo() {
        _uiState.update {
            it.copy(
                ventaId = null,
                DatoCliente = "",
                galones = 0.0,
                descuentoGalon = 0.0,
                precio = 0.0,
                totalDescuento = 0.0,
                total = 0.0
            )
        }
    }
    fun select(ventaId: Int) {
        viewModelScope.launch {
            if (ventaId > 0) {
                val venta = ventaRepository.find(ventaId)
                _uiState.update {
                    it.copy(
                        ventaId = venta?.ventaId,
                        DatoCliente = venta?.DatoCliente ?: "",
                        galones = venta?.galones ?: 0.0,
                        descuentoGalon = venta?.descuentoGalon ?: 0.0,
                        precio = venta?.precio ?: 0.0,
                        totalDescuento = venta?.totalDescuento ?: 0.0,
                        total = venta?.total ?: 0.0
                    )
                }
            }
        }
    }
    fun delete() {
        viewModelScope.launch {
            ventaRepository.delete(_uiState.value.ToEntity())
        }
        nuevo()
    }
    private fun getVentas() {
        viewModelScope.launch {
            ventaRepository.getAll().collect { venta ->
                _uiState.update {
                    it.copy(ventas = venta)
                }
            }
        }
    }
    fun updateTotalValues() {
        _uiState.update { venta ->
            val newTotalDescuento = venta.galones * venta.descuentoGalon
            val newTotal = (venta.precio * venta.galones) - newTotalDescuento

            venta.copy(
                totalDescuento = newTotalDescuento,
                total = newTotal
            )
        }
    }

    fun onVentaIdChange (ventaId: Int) {
        _uiState.update {
            it.copy(ventaId = ventaId)
        }
    }
    fun onDatoClienteChange (DatoCliente: String) {
        _uiState.update {
            it.copy(DatoCliente = DatoCliente)
        }
    }
    fun onGalonesChange(galones: Double) {
        _uiState.update {
            it.copy(galones = galones)
        }
        updateTotalValues()
    }

    fun onDescuentoGalonChange(descuentoGalon: Double) {
        _uiState.update {
            it.copy(descuentoGalon = descuentoGalon)
        }
        updateTotalValues()
    }

    fun onPrecioChange(precio: Double) {
        _uiState.update {
            it.copy(precio = precio)
        }
        updateTotalValues()
    }
    fun onTotalDescuentoChange (totalDescuento: Double) {
        _uiState.update {
            it.copy(totalDescuento = totalDescuento)
        }
    }
    fun onTotalChange (total: Double) {
        _uiState.update {
            it.copy(total = total)
        }
    }

}

data class UiState(
    val ventaId: Int? = null,
    val DatoCliente: String = "",
    val galones: Double = 0.0,
    val descuentoGalon: Double = 0.0,
    val precio: Double = 0.0,
    val totalDescuento: Double = 0.0,
    val total: Double = 0.0,
    val errorDatoCliente: String? = null,
    val errorGalones: String? = null,
    val errorDescuentoGalon: String? = null,
    val errorPrecio: String? = null,
    val ventas: List<VentaEntity> = emptyList()
)

fun UiState.ToEntity() = VentaEntity(
    ventaId = ventaId,
    DatoCliente = DatoCliente,
    galones = galones,
    descuentoGalon = descuentoGalon,
    precio = precio,
    totalDescuento = totalDescuento,
    total = total
)

