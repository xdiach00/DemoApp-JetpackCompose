package com.xdiach.demoappcompose.ui.places

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import com.xdiach.demoappcompose.R
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberImagePainter
import com.xdiach.demoappcompose.ui.countDistance
import com.xdiach.demoappcompose.ui.theme.DemoAppComposeTheme
import com.xdiach.demoappcompose.ui.theme.LocationGrey
import com.xdiach.model.response.places.PlacesListResponseItem

@Composable
fun PlacesListScreen(navigationCallback: (String) -> Unit) {

    val viewModel: PlacesListViewModel = viewModel()
    val places = viewModel.placesState.value

    Scaffold(topBar = { AppBar() }) {

        LazyColumn(contentPadding = PaddingValues(16.dp)) {
            if (sortingType)
                items(places.sortedBy { it.name }) { place ->
                    PlaceElement(place, navigationCallback)
                }
            else
                items(places.sortedBy {
                    countDistance(
                        it.location.latitude,
                        it.location.longitude
                    )
                }) { place ->
                    PlaceElement(place, navigationCallback)
                }
        }
    }
}

//TODO: Ask how to make Image and Sorting change onClick
@Composable
fun AppBar() {

    TopAppBar(title = {
        Text(
            "Places",
            style = MaterialTheme.typography.h1,
            color = Color.Black,
            modifier = Modifier
                .padding(start = 34.dp)
        )
    },
        actions = {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(100.dp)
            ) {

                IconButton(onClick = {
                    sortingType = !sortingType
                }) {
                    Image(
                        painter = painterResource(
                            id = if (sortingType) {
                                R.drawable.ic_abc
                            } else {
                                R.drawable.ic_dist
                            }
                        ),
                        contentDescription = null,
                        modifier = Modifier
                            .size(17.dp)
                    )
                }
            }
        }
    )
}

@Composable
fun PlaceElement(place: PlacesListResponseItem, navigationCallback: (String) -> Unit) {

    var distanceToPlace = countDistance(place.location.latitude, place.location.longitude)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(20.dp)
            .clickable {
                navigationCallback(place.uuid)
            }
    ) {
        Card(
            shape = RoundedCornerShape(20.dp),
            elevation = 5.dp,
            modifier = Modifier
                .size(width = 319.dp, height = 319.dp)
        ) {
            Image(
                painter = rememberImagePainter(place.thumbnail_image),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
            )
            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Bottom,
                modifier = Modifier
                    .padding(start = 28.dp, bottom = 19.dp)
            ) {
                Text(
                    place.name,
                    style = MaterialTheme.typography.h1,
                    color = Color.White
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_vector),
                        contentDescription = null,
                        modifier = Modifier.size(14.dp)
                    )
                    Text(
                        distanceToPlace.toInt().toString() + " km",
                        style = MaterialTheme.typography.body1,
                        color = LocationGrey
                    )
                }

            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    DemoAppComposeTheme {
        PlacesListScreen({})
    }
}