package com.test.weatherapp.ui.search

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.test.weatherapp.R
import com.test.weatherapp.ui.base.UIState
import com.test.weatherapp.ui.cityweather.CityWeather
import com.test.weatherapp.ui.utils.ErrorState
import com.test.weatherapp.ui.utils.Loader

@Composable
fun SearchScreen(navController: NavHostController, viewModel: SearchVM = hiltViewModel()) {
    val cityData by viewModel.cityData.observeAsState()
    val uiState by viewModel.uiState.observeAsState()
    val enteredCityName by viewModel.enteredCityName.observeAsState()
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current

    val modifier = Modifier
    Box(
        Modifier
            .fillMaxSize()
            .fillMaxWidth()
    ) {
        Column(
            modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            enteredCityName?.let {
                TextField(
                    value = it,
                    modifier = Modifier.fillMaxWidth(),
                    onValueChange = { text ->
                        viewModel.setCityName(text)
                    },
                    singleLine = true,
                    placeholder = { Text(stringResource(id = R.string.search_city_name)) },
                    keyboardActions = KeyboardActions(onSearch = {
                        focusManager.clearFocus()
                        viewModel.getWeatherByCityName(enteredCityName.toString())
                    }),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Search,
                    ),
                    isError = uiState is UIState.Error && (uiState as UIState.Error).code == 10001
                )
                if (uiState is UIState.Error && (uiState as UIState.Error).code == 10001) {
                    Text(
                        text = (uiState as UIState.Error).error.toString(),
                        color = MaterialTheme.colors.error,
                        style = MaterialTheme.typography.caption,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                }
            }
            if (uiState is UIState.Loading) Loader()
            else if (uiState is UIState.Error) {
                if ((uiState as UIState.Error).code != 10001) ErrorState((uiState as UIState.Error).error.toString()) {

                }
            }
            cityData?.let {
                CityWeather(modifier = modifier, weatherParent = it)
                TextButton(
                    modifier = modifier
                        .padding(top = 20.dp)
                        .height(50.dp),
                    onClick = { viewModel.addToFavourite() },
                    colors = ButtonDefaults.textButtonColors(
                        backgroundColor = MaterialTheme.colors.primary,
                        contentColor = MaterialTheme.colors.onPrimary,
                    )
                ) {
                    Text(
                        text = stringResource(id = R.string.add_to_favourite)
                    )
                }
            }
        }
    }
}