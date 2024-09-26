package edu.ucne.isaaccoste_p1_ap2.presentation.venta

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun VentaScreeen(
    viewModel: VentaViewModel = hiltViewModel(),
    VentaId: Int,
    goBack: () -> Unit,
    isEditMode: Boolean
) {
    LaunchedEffect(VentaId) {
        if (isEditMode) {
            viewModel.select(VentaId)
        }
        else {
            viewModel.select(VentaId)
        }
    }
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    ventaBodyScreen(
        uiState = uiState,
        onVentaIdChange = viewModel::onVentaIdChange,
        onDatoClienteChange = viewModel::onDatoClienteChange,
        onGalonesChange = viewModel::onGalonesChange,
        onDescuentoGalonChange = viewModel::onDescuentoGalonChange,
        onTotalDescuentoChange = viewModel::onTotalDescuentoChange,
        onPrecioChange = viewModel::onPrecioChange,
        onTotalChange = viewModel::onTotalChange,
        save = viewModel::save,
        delete = viewModel::delete,
        goBack = goBack,
        isEditMode = isEditMode
    )
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun ventaBodyScreen(
    uiState: UiState,
    onVentaIdChange: (Int) -> Unit,
    onDatoClienteChange: (String) -> Unit,
    onGalonesChange: (Double) -> Unit,
    onDescuentoGalonChange: (Double) -> Unit,
    onTotalDescuentoChange: (Double) -> Unit,
    onPrecioChange: (Double) -> Unit,
    onTotalChange: (Double) -> Unit,
    save: () -> Unit,
    delete: () -> Unit,
    goBack: () -> Unit,
    isEditMode: Boolean
) {
    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(20.dp)
        ) {
            Text(
                text = "Registro de Ventas",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.primary
            )
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp),
                label = { Text("Datos del Cliente") },
                value = uiState.DatoCliente,
                onValueChange = onDatoClienteChange
            )
            var isEditing by remember { mutableStateOf(false) }

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp)
                    .onFocusChanged { focusState ->
                        isEditing = focusState.isFocused
                    },
                label = { Text("Galones") },
                value = if (uiState.galones != 0.0) uiState.galones.toString() else "",
                onValueChange = { newValue ->
                    val galones = newValue.toDoubleOrNull() ?: 0.0
                    onGalonesChange(galones)
                },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                singleLine = true
            )
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp)
                    .onFocusChanged { focusState ->
                        isEditing = focusState.isFocused
                    },
                label = { Text("Descuento por galón") },
                value = if (uiState.descuentoGalon != 0.0) uiState.descuentoGalon.toString() else "",
                onValueChange = { newValue ->
                    val descGalones = newValue.toDoubleOrNull() ?: 0.0
                    onDescuentoGalonChange(descGalones)
                },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                singleLine = true
            )
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp)
                    .onFocusChanged { focusState ->
                        isEditing = focusState.isFocused
                    },
                label = { Text("Precio") },
                value = if (uiState.precio != 0.0) uiState.precio.toString() else "",
                onValueChange = { newValue ->
                    val precio = newValue.toDoubleOrNull() ?: 0.0
                    onPrecioChange(precio)
                },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                singleLine = true
            )
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp)
                    .onFocusChanged { focusState ->
                        isEditing = focusState.isFocused
                    },
                label = { Text("Total descuento") },
                value = if (uiState.totalDescuento != 0.0) uiState.totalDescuento.toString() else "",
                readOnly = true,
                onValueChange = { newValue ->
                    val totalDes = newValue.toDoubleOrNull() ?: 0.0
                    onTotalDescuentoChange(totalDes)
                },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                singleLine = true
            )
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp)
                    .onFocusChanged { focusState ->
                        isEditing = focusState.isFocused
                    },
                label = { Text("Total") },
                readOnly = true,
                value = if (uiState.total != 0.0) uiState.total.toString() else "",
                onValueChange = { newValue ->
                    val total = newValue.toDoubleOrNull() ?: 0.0
                    onTotalChange(total)
                },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                singleLine = true
            )
            Spacer(modifier = Modifier.padding(8.dp))
            uiState.errorMessager?.let {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 20.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(text = it, color = Color.Red)
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 5.dp, vertical = 1.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                OutlinedButton(onClick = goBack) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Volver"
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Volver")
                }
                if (!isEditMode) {
                    OutlinedButton(
                        onClick = save
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Guardar"
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Guardar")
                    }
                } else {
                    OutlinedButton(
                        onClick = delete,
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = Color.Red
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Eliminar"
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Eliminar")
                    }
                }
            }
        }
    }
}
