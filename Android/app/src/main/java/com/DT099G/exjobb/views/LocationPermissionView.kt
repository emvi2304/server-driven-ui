package com.DT099G.exjobb.views

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.pm.PackageManager
import android.location.Location
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresPermission
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.DT099G.exjobb.LayoutViewModel
import com.DT099G.exjobb.LoadingScreen
import com.google.android.gms.location.LocationServices

// https://developer.android.com/training/permissions/requesting
@Composable
fun requestLocationPermission(){
    val context = LocalContext.current


    // ContextCompat.checkSelfPermission kollar om  rättigheten ACCESS_COARSE_LOCATION är PERMISSION_GRANTED för appen. returnerar då true
    // Vid ändringar av rättigheter så triggas en recomposition pga mutableStateOf
    var isGranted by remember { mutableStateOf(ContextCompat.checkSelfPermission(
        context,
        Manifest.permission.ACCESS_COARSE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED) }

    val requestPermissionLauncher =
        rememberLauncherForActivityResult(
            ActivityResultContracts.RequestPermission()
        ){
            granted: Boolean ->
                isGranted = granted
        }


    when {
        // Behörighet redan godkänd
        isGranted-> {
            LocationView()
        }

        // Användaren har tidigare nekat, kan meddela varför det behövs
        ActivityCompat.shouldShowRequestPermissionRationale(
            context as Activity,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) -> {
            DeniedAccessScreen()
        }
        else -> { // om frågan ej frågats än
            RequestAccessScreen(onRetry = { requestPermissionLauncher.launch(Manifest.permission.ACCESS_COARSE_LOCATION) })
        }
    }
}



// https://github.com/android/platform-samples/blob/main/samples/location/src/main/java/com/example/platform/location/currentLocation/CurrentLocationScreen.kt
@SuppressLint("MissingPermission")
@RequiresPermission(
    anyOf = [Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION],
)
@Composable
fun LocationView() {
    val viewModel: LayoutViewModel = viewModel()
    val context = LocalContext.current
    val fusedClient = remember { LocationServices.getFusedLocationProviderClient(context) }
    var location by remember { mutableStateOf<Location?>(null) }

    LaunchedEffect(Unit) {
        fusedClient.lastLocation.addOnSuccessListener { location = it }
    }

    LoadingScreen()

    val lon = location?.longitude
    val lat = location?.latitude

    LaunchedEffect( lat, lon ) {
        if( lat != null && lon != null ) {
            viewModel.getWeatherData( access = "true", lon = lon.toString(), lat = lat.toString())
        }
    }
}

@Composable
fun DeniedAccessScreen() {
    val viewModel: LayoutViewModel = viewModel()
    LaunchedEffect( Unit ) {
        viewModel.getWeatherData( access = "false", lon = "", lat = "")
    }
}

@Composable
fun RequestAccessScreen(onRetry: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center) {
        Card(){
            Column( modifier = Modifier.padding(16.dp)) {
                Text("Din plats behövs för att kunna hämta väderdatan")
                Button( onClick = onRetry ) {
                    Text("Ok")
                }
            }
        }
    }
}