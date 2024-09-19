package edu.ucne.isaaccoste_p1_ap2.presentation.navigacion

import kotlinx.serialization.Serializable

sealed class Screen {
    @Serializable
    data object listScreen : Screen()
    @Serializable
    data class registroScreen(val Id: Int) : Screen()
}