package edu.ucne.isaaccoste_p1_ap2.presentation.venta

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.ucne.isaaccoste_p1_ap2.data.local.entities.VentaEntity

@Composable
fun VentaListScreen(
    viewModel: VentaViewModel = hiltViewModel(),
    createVenta: () -> Unit,
    onEditVenta: (Int) -> Unit,
    onDeleteVenta: (Int) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    VentaListBodyScreen(
        uiState = uiState,
        createVenta = createVenta,
        onEditVenta = onEditVenta,
        onDeleteVenta = onDeleteVenta
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VentaListBodyScreen(
    uiState: UiState,
    createVenta: () -> Unit,
    onEditVenta: (Int) -> Unit,
    onDeleteVenta: (Int) -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Lista de Venta") },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors()
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = createVenta,
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = Color.White
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Agregar Venta"
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(vertical = 16.dp)
            ) {
                items(uiState.ventas) { venta ->
                    ListRow(
                        venta = venta,
                        onEditVenta = onEditVenta,
                        onDeleteVenta = onDeleteVenta
                    )
                }
            }
        }
    }
}

@Composable
fun ListRow(
    venta: VentaEntity,
    onEditVenta: (Int) -> Unit,
    onDeleteVenta: (Int) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .clickable { expanded = !expanded }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 50.dp)
            ) {
                Text(
                    text = "ID: " + (venta.ventaId?.toString() ?: "Sin ID"),
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "Datos del Cliente: " + venta.DatoCliente,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "Galón: " + venta.galones.toString(),
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "Descuento por galón: " + venta.descuentoGalon.toString(),
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "Precio: " + venta.precio.toString(),
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "Total Descuento: " + venta.totalDescuento.toString(),
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "Total: " + venta.total.toString(),
                    textAlign = TextAlign.Center
                )
            }
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                text = { Text("Editar") },
                onClick = {
                    expanded = false
                    venta.ventaId?.let { onEditVenta(it) }
                }
            )
            DropdownMenuItem(
                text = { Text("Eliminar") },
                onClick = {
                    expanded = false
                    venta.ventaId?.let { onDeleteVenta(it) }
                }
            )
        }
        Divider(color = Color.Gray, thickness = 1.dp, modifier = Modifier.padding(vertical = 8.dp))
    }
}