package com.xdiach.demoappcompose.ui

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.core.app.ActivityCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.xdiach.demoappcompose.ui.details.PlaceDetailsScreen
import com.xdiach.demoappcompose.ui.details.PlaceDetailsViewModel
import com.xdiach.demoappcompose.ui.places.PlacesListScreen
import com.xdiach.demoappcompose.ui.theme.DemoAppComposeTheme
import android.location.Location

var longitudeCurrent: Double = 50.100558
var latitudeCurrent: Double = 14.424798

class MainActivity : ComponentActivity() {

    lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DemoAppComposeTheme {
                SystemBarsColors {
                    PlacesApp()
                }
            }
        }

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        checkLocationPermission()
    }

    private fun checkLocationPermission() {
        val loc = fusedLocationProviderClient.lastLocation

        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            )
            != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            )
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                101
            )
        }

        loc.addOnSuccessListener {
            if (it != null) {
                latitudeCurrent = it.latitude
                longitudeCurrent = it.longitude
            }
        }
    }
}
// TODO: Ask how to apply goBack on Image in PlaceDetailsScreen without adding to the BackStack

@Composable
private fun PlacesApp() {
    val navController = rememberNavController()

    NavHost(navController, startDestination = "destination_places_list") {
        composable(route = "destination_places_list") {
            PlacesListScreen { navigationPlaceId ->
                navController.navigate("destination_place_detail/$navigationPlaceId")
            }
        }
        composable(
            route = "destination_place_detail/{place_id}",
            arguments = listOf(navArgument("place_id") {
                type = NavType.StringType
            })
        ) {
            val viewModel: PlaceDetailsViewModel = viewModel()
            PlaceDetailsScreen(viewModel.placeState.value, navController)
        }
    }
}

@Composable
fun SystemBarsColors(
    statusBar: Color = MaterialTheme.colors.primary,
    useDarkIconsStatus: Boolean = MaterialTheme.colors.isLight,
    navigationBar: Color = MaterialTheme.colors.primary,
    useDarkIconsNavigation: Boolean = MaterialTheme.colors.isLight,
    content: @Composable () -> Unit,
) {
    val systemUiController = rememberSystemUiController()
    SideEffect {
        systemUiController.setStatusBarColor(
            color = statusBar,
            darkIcons = useDarkIconsStatus
        )
        systemUiController.setNavigationBarColor(
            color = navigationBar,
            darkIcons = useDarkIconsNavigation
        )
    }

    content()
}

fun countDistance(latitude: Double, longitude: Double): Float {
    val startPoint = Location("locationA")
    startPoint.latitude = latitudeCurrent
    startPoint.longitude = longitudeCurrent

    val endPoint = Location("locationA")
    endPoint.latitude = latitude
    endPoint.longitude = longitude

    return startPoint.distanceTo(endPoint) / 1000
}
