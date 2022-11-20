package com.xdiach.demoappcompose.ui.details

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.xdiach.demoappcompose.R
import com.xdiach.demoappcompose.ui.countDistance
import com.xdiach.demoappcompose.ui.latitudeCurrent
import com.xdiach.demoappcompose.ui.longitudeCurrent
import com.xdiach.demoappcompose.ui.theme.LocationDarkGrey
import com.xdiach.demoappcompose.ui.theme.LocationGrey
import com.xdiach.demoappcompose.ui.theme.YellowMain
import com.xdiach.model.response.place.PlaceDetailsResponse
import com.xdiach.model.response.places.PlacesListResponseItem

@Composable
fun PlaceDetailsScreen(place: PlaceDetailsResponse?, navController: NavHostController) {

    val context = LocalContext.current
    val mapsIntent = openGoogleMaps(
        place?.location?.latitude ?: latitudeCurrent,
        place?.location?.longitude ?: longitudeCurrent
    )

    var distanceToPlace = countDistance(
        place?.location?.latitude ?: latitudeCurrent,
        place?.location?.longitude ?: longitudeCurrent
    )

    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxWidth()
    ) {
        Card(
            modifier = Modifier
                .height(500.dp)
        ) {
            Image(
                painter = rememberImagePainter(place?.thumbnail_image),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
            )
        }
        Row(
            modifier = Modifier
                .padding(start = 24.dp, bottom = 15.dp, top = 15.dp)
                .fillMaxSize()
        ) {
            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Bottom
            ) {
                Text(
                    place?.name ?: "name",
                    style = MaterialTheme.typography.h1,
                    color = Color.Black
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_vector_dark),
                        contentDescription = null,
                        modifier = Modifier.size(14.dp)
                    )
                    Text(
                        distanceToPlace.toInt().toString() + " km",
                        style = MaterialTheme.typography.body1,
                        color = LocationDarkGrey
                    )
                }

            }
            Spacer(modifier = Modifier.weight(1f))

            Image(
                painter = painterResource(id = R.drawable.ic_nav),
                contentDescription = null,
                modifier = Modifier
                    .size(64.dp)
                    .padding(end = 24.dp)
                    .clickable {
                        context.startActivity(mapsIntent)
                    }
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(start = 24.dp, end = 24.dp)
        ) {
            Text(
                place?.address?.city ?: "City",
                style = MaterialTheme.typography.body1,
                color = Color.Black
            )
            Text(
                place?.address?.zip + " " + place?.address?.street ?: "City",
                style = MaterialTheme.typography.body1,
                color = Color.Black
            )
            Text(
                place?.description?.trimEnd('\n') ?: "City",
                style = MaterialTheme.typography.body1,
                color = Color.Black,
                modifier = Modifier
                    .padding(top = 15.dp)
                    .wrapContentHeight()
            )
            Button(
                onClick = {
                    context.startActivity(mapsIntent)
                },
                colors = ButtonDefaults.buttonColors(backgroundColor = YellowMain),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .padding(bottom = 40.dp, top = 0.dp)
            ) {
                Text(
                    text = "Navigate",
                    color = Color.Black,
                    style = MaterialTheme.typography.button
                )
            }
        }
    }
    Image(
        painter = painterResource(id = R.drawable.ic_back),
        contentDescription = null,
        modifier = Modifier
            .padding(top = 24.dp, start = 15.dp)
            .clickable {
                navController.navigateUp()
            }
    )
}