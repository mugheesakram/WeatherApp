package com.test.weatherapp.ui.main


import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.test.weatherapp.ui.favourite.FavouriteDetail
import com.test.weatherapp.ui.favourite.FavouriteScreen
import com.test.weatherapp.ui.home.HomeScreen
import com.test.weatherapp.ui.routing.Screen
import com.test.weatherapp.ui.routing.WeatherAppRouter
import com.test.weatherapp.ui.search.SearchScreen
import com.test.weatherapp.ui.settings.SettingsScreen

@Composable
fun MainScreen() {
    val navController = rememberNavController()

    Crossfade(targetState = WeatherAppRouter.currentScreen) { screenState: MutableState<Screen> ->
        Scaffold(
            bottomBar = {
                BottomNavigationComponent(navController, screenState = screenState)
            },
            content = {
                MainScreenContainer(
                    navController,
                    modifier = Modifier.padding(it),
                    screenState = screenState
                )
            }
        )
    }
}

@Composable
fun BottomNavigationComponent(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    screenState: MutableState<Screen>
) {
    var selectedItem by rememberSaveable { mutableStateOf(Screen.Home.path) }

    val items = listOf(Screen.Home, Screen.Search, Screen.Favourite, Screen.Settings)
    val backStackEntry = navController.currentBackStackEntryAsState()
    BottomNavigation(modifier = modifier, backgroundColor = Color.LightGray) {
        items.forEach { item ->
            val currentRoute = backStackEntry.value?.destination?.route;
            val selected = currentRoute == item.path
            val color =
                if (selected) MaterialTheme.colors.primary else Color.DarkGray
            BottomNavigationItem(
                icon = {
                    Icon(
                        item.icon,
                        contentDescription = item.path,
                        tint = color
                    )
                },
                selected = selected,
                onClick = {
                    selectedItem = item.path
                    navController.navigate(item.path) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = false
                        }
                        restoreState = false
                    }
                }
            )
        }
    }
}

@Composable
private fun MainScreenContainer(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    screenState: MutableState<Screen>,
) {
    NavHost(
        navController = navController, startDestination = Screen.Home.path
    ) {
        composable(Screen.Home.path) {
            HomeScreen()
        }
        composable(Screen.Search.path) {
            SearchScreen(navController)
        }
        composable(Screen.Favourite.path) {
            FavouriteScreen(navController)
        }
        composable(Screen.Settings.path) {
            SettingsScreen(navController)
        }
        composable(Screen.FavouriteDetail.path) {
            FavouriteDetail(navController)

        }
    }
}