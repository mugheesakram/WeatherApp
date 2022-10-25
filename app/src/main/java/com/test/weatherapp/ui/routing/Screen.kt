package com.test.weatherapp.ui.routing

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val path: String, val icon: ImageVector) {
    object Home : Screen("home", Icons.Default.Home)
    object Search : Screen("search", Icons.Default.Search)
    object Favourite : Screen("favourite", Icons.Default.Favorite)
    object Settings : Screen("settings", Icons.Default.Settings)
    object FavouriteDetail : Screen("favouriteDetail", Icons.Default.Place)
}

object WeatherAppRouter {
    var currentScreen: MutableState<Screen> = mutableStateOf(
        Screen.Home
    )

    private var previousScreen: MutableState<Screen> = mutableStateOf(
        Screen.Home
    )

    fun navigateTo(destination: Screen) {
        previousScreen.value = currentScreen.value
        currentScreen.value = destination
    }

    fun goBack() {
        currentScreen.value = previousScreen.value
    }
}