package com.test.weatherapp.ui.favourite

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.test.weatherapp.R
import com.test.weatherapp.data.models.weathermodels.WeatherParent
import com.test.weatherapp.ui.routing.Screen
import com.test.weatherapp.ui.theme.lightPurple
import com.test.weatherapp.ui.utils.getAnnotatedString
import com.test.weatherapp.utils.toCelsius

@Composable
fun FavouriteScreen(navController: NavHostController, viewModel: FavouriteVM = hiltViewModel()) {

    val cityList by viewModel.favouriteCities.observeAsState()
    val modifier = Modifier
    Box(
        Modifier
            .fillMaxSize()
            .fillMaxWidth()
    ) {
        Column(
            modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (cityList?.isNotEmpty() == true) LazyColumn(
                verticalArrangement = Arrangement.spacedBy(
                    10.dp
                )
            ) {
                itemsIndexed(cityList as List<WeatherParent>) { index, item ->
                    CityItem(item) {
                        navController.currentBackStackEntry?.savedStateHandle?.apply {
                            set("WeatherParent", it)
                        }
                        navController.navigate(Screen.FavouriteDetail.path)
                    }
                }
            }
        }
    }
}


@Composable
fun CityItem(weatherParent: WeatherParent, navigateOnClick: (WeatherParent) -> Unit) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 5.dp)
            .background(color = lightPurple, shape = RoundedCornerShape(10.dp))
            .clickable {
                navigateOnClick(weatherParent)
            }) {
        Text(
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(start = 10.dp),
            text = weatherParent.city.name.toString(),
            style = TextStyle(fontSize = 18.sp),
            maxLines = 2,
        )
        Text(
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(start = 40.dp),
            text = getAnnotatedString(
                stringId = R.string.degree_celsius,
                stringAttachment = weatherParent.list?.get(0)?.main?.temp?.toCelsius().toString(),
                42f
            ),
            style = TextStyle(fontSize = 20.sp)
        )

        Text(
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(start = 40.dp),
            text = weatherParent.list?.get(0)?.weather?.get(0)?.main.toString(),
            style = TextStyle(fontSize = 24.sp)
        )
    }
}