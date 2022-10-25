package com.test.weatherapp.ui.settings

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController

@Composable
fun SettingsScreen(navController: NavHostController, homeVM: SettingsVM = viewModel()) {
    Text(text = "Settings")
}