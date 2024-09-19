package edu.ucne.isaaccoste_p1_ap2.presentation.navigacion

import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import edu.ucne.isaaccoste_p1_ap2.ui.theme.IsaacCoste_P1_Ap2Theme

@Composable
fun IsaacCoste_P1_Ap2NavHost(
    navHostController: NavHostController
) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.listScreen
    ) {
        composable<Screen.listScreen> {
            Button(
                onClick = { navHostController.navigate(Screen.registroScreen(0)) }
            ) {
                Text(text = "Ir a la pantalla de Lista de registro")
            }
        }
        composable<Screen.registroScreen> {
            Button(
                onClick = {}
            ) {
                Text(text = "Ir a la pantalla de Registro")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    IsaacCoste_P1_Ap2Theme {
        val navController = rememberNavController()
        IsaacCoste_P1_Ap2NavHost(navHostController = navController)
    }
}