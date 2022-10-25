package com.test.weatherapp.ui.home

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.SnackbarResult
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.test.weatherapp.R
import com.test.weatherapp.ui.base.PermissionAction
import com.test.weatherapp.ui.base.UIState
import com.test.weatherapp.ui.cityweather.CityWeather
import com.test.weatherapp.ui.utils.DefaultSnackbar
import com.test.weatherapp.ui.utils.ErrorState
import com.test.weatherapp.ui.utils.Loader
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(viewModel: HomeVM = hiltViewModel()) {
    val scaffoldState = rememberScaffoldState()
    Box {
        val currentLocationWeather by viewModel.cityData.observeAsState()
        val uiState by viewModel.uiState.observeAsState()

        currentLocationWeather?.let { CityWeather(weatherParent = it) }
        if (uiState is UIState.Loading) Loader()
        else if (uiState is UIState.Error) {
            if ((uiState as UIState.Error).code != 10001) ErrorState((uiState as UIState.Error).error.toString()) {

            }
        }
        PermissionTestUI(scaffoldState)


        DefaultSnackbar(
            snackbarHostState = scaffoldState.snackbarHostState,
            modifier = Modifier.align(Alignment.BottomCenter),
            onAction = {
                scaffoldState.snackbarHostState.currentSnackbarData?.performAction()
            },
        )
    }
}


@SuppressLint("MissingPermission")
@Composable
fun PermissionTestUI(scaffoldState: ScaffoldState, viewModel: HomeVM = hiltViewModel()) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val mFusedLocationClient: FusedLocationProviderClient? =
        LocationServices.getFusedLocationProviderClient(context)

    PermissionUI(
        context,
        Manifest.permission.ACCESS_FINE_LOCATION,
        stringResource(id = R.string.permission_location_rationale),
        scaffoldState
    ) { permissionAction ->
        when (permissionAction) {
            is PermissionAction.OnPermissionGranted -> {
                mFusedLocationClient?.lastLocation?.addOnSuccessListener { location ->
                    location.also {
                        viewModel.getWeatherOfCurrentLocation(it.latitude, it.longitude)
                    }
                }
                Log.d("Location", "Location has been granted")
                scope.launch {
                    scaffoldState.snackbarHostState.showSnackbar("Location permission granted!")
                }
            }
            is PermissionAction.OnPermissionDenied -> {
            }
        }

    }

}

@Composable
fun PermissionUI(
    context: Context,
    permission: String,
    permissionRationale: String,
    scaffoldState: ScaffoldState,
    permissionAction: (PermissionAction) -> Unit
) {

    val permissionGranted = checkIfPermissionGranted(
        context, permission
    )

    if (permissionGranted) {
        Log.d("Location", "Permission already granted, exiting..")
        permissionAction(PermissionAction.OnPermissionGranted)
        return
    }


    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            Log.d("Location", "Permission provided by user")
            // Permission Accepted
            permissionAction(PermissionAction.OnPermissionGranted)
        } else {
            Log.d("Location", "Permission denied by user")
            // Permission Denied
            permissionAction(PermissionAction.OnPermissionDenied)
        }
    }


    val showPermissionRationale = shouldShowPermissionRationale(
        context, permission
    )


    if (showPermissionRationale) {
        Log.d("Location", "Showing permission rationale for $permission")

        LaunchedEffect(showPermissionRationale) {

            val snackbarResult = scaffoldState.snackbarHostState.showSnackbar(
                message = permissionRationale,
                actionLabel = "Grant Access",
                duration = SnackbarDuration.Long

            )
            when (snackbarResult) {
                SnackbarResult.Dismissed -> {
                    Log.d("Location", "User dismissed permission rationale for $permission")
                    //User denied the permission, do nothing
                    permissionAction(PermissionAction.OnPermissionDenied)
                }
                SnackbarResult.ActionPerformed -> {
                    Log.d(
                        "Location",
                        "User granted permission for $permission rationale. Launching permission request.."
                    )
                    launcher.launch(permission)
                }
            }
        }
    } else {
        //Request permissions again
        Log.d("Location", "Requesting permission for $permission again")
        SideEffect {
            launcher.launch(permission)
        }

    }


}

fun shouldShowPermissionRationale(context: Context, permission: String): Boolean {

    val activity = context as Activity?
    return ActivityCompat.shouldShowRequestPermissionRationale(
        activity!!, permission
    )
}

fun checkIfPermissionGranted(context: Context, permission: String): Boolean {
    return (ContextCompat.checkSelfPermission(
        context, permission
    ) == PackageManager.PERMISSION_GRANTED)
}
