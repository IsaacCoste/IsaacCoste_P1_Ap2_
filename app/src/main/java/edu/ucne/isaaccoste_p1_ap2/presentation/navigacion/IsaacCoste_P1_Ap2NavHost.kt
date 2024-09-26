package edu.ucne.isaaccoste_p1_ap2.presentation.navigacion

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import edu.ucne.isaaccoste_p1_ap2.presentation.venta.VentaListScreen
import edu.ucne.isaaccoste_p1_ap2.presentation.venta.VentaScreeen
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
            VentaListScreen(
                createVenta = {
                    navHostController.navigate(Screen.registroScreen(0, false))
                },
                onEditVenta = {
                    navHostController.navigate(Screen.registroScreen(it, false))
                },
                onDeleteVenta = {
                    navHostController.navigate(Screen.registroScreen(it,true))
                }
            )
        }
        composable<Screen.registroScreen> {
            val args = it.toRoute<Screen.registroScreen>()
            VentaScreeen(
                VentaId = args.Id,
                goBack = { navHostController.navigateUp() },
                isEditMode = args.isEditMode
            )
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