package com.test.weatherapp.ui.cityweather

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.test.weatherapp.R
import com.test.weatherapp.data.models.weathermodels.WeatherParent
import com.test.weatherapp.data.models.weathermodels.WeatherPerDay
import com.test.weatherapp.ui.theme.lightPurple
import com.test.weatherapp.ui.utils.getAnnotatedString
import com.test.weatherapp.utils.serverFormatToWeekDayMonthDateAndTime
import com.test.weatherapp.utils.toCelsius


@Composable
fun CityWeather(modifier: Modifier = Modifier, weatherParent: WeatherParent) {
    Box {
        Column(
            modifier = modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 30.dp),
                text = weatherParent.city.name.toString(),
                style = TextStyle(fontSize = 24.sp)
            )

            Text(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 50.dp),
                text = getAnnotatedString(
                    stringId = R.string.degree_celsius,
                    stringAttachment = weatherParent.list?.get(0)?.main?.temp?.toCelsius()
                        .toString(),
                    66f
                ),
                style = TextStyle(fontSize = 32.sp)
            )

            Text(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 50.dp),
                text = weatherParent.list?.get(0)?.weather?.get(0)?.main.toString(),
                style = TextStyle(fontSize = 18.sp)
            )

            Text(
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(top = 50.dp, bottom = 30.dp),
                text = stringResource(id = R.string.five_day_three_hour_forecast),
                style = TextStyle(fontSize = 18.sp, color = MaterialTheme.colors.primary)
            )
            LazyRow {
                itemsIndexed(
                    weatherParent.list?.subList(
                        1, weatherParent.list.lastIndex
                    ) as List<WeatherPerDay>
                ) { index, item ->
                    WeatherPerDayItem(item)
                }
            }
        }
    }
}

@Composable
fun WeatherPerDayItem(weatherPerDay: WeatherPerDay) {
    Column(
        Modifier
            .background(color = lightPurple)
            .clip(RoundedCornerShape(10.dp))
            .padding(24.dp)
    ) {
        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = weatherPerDay.dt_txt?.serverFormatToWeekDayMonthDateAndTime().toString(),
            style = TextStyle(fontSize = 18.sp),
            maxLines = 2,
        )

        Text(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 30.dp),
            text = getAnnotatedString(
                stringId = R.string.degree_celsius,
                stringAttachment = weatherPerDay.main?.temp?.toCelsius().toString(),
                42f
            ),
            style = TextStyle(fontSize = 20.sp)
        )

        Text(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 30.dp),
            text = weatherPerDay.weather?.get(0)?.main.toString(),
            style = TextStyle(fontSize = 24.sp)
        )
    }
}