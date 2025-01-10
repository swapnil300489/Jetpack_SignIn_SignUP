package com.jetpack.loginsignup.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.jetpack.loginsignup.networkCommunication.RetrofitInstance
import com.jetpack.loginsignup.req_res_model.UserListResponse
import com.jetpack.loginsignup.utils.Repository
import kotlinx.coroutines.launch

@Composable
fun DashboardScreen(navController: NavHostController, email: String) {

    val api = RetrofitInstance.api
    var userList by remember { mutableStateOf<List<UserListResponse>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    val repository = Repository()


    LaunchedEffect(Unit) {
        scope.launch {
            try {
                userList = repository.getAllUsers()
                errorMessage = ""
            } catch (e: Exception) {
                errorMessage = "Failed to fetch users: ${e.message}"
            } finally {
                isLoading = false
            }
        }
    }

    if (isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else if (errorMessage.isNotEmpty()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = errorMessage, color = MaterialTheme.colorScheme.error)
        }
    } else {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "User List",
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier
                    .padding(bottom = 2.dp)
                    .align(Alignment.CenterHorizontally)
            )

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(userList){
                        it-> UserItem(it)
                }

            }


        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserItem(user: UserListResponse) {
    
    Card(
        onClick = {},
        modifier = Modifier.fillMaxWidth()) {

        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "${user.name.firstname} ${user.name.lastname}", style = MaterialTheme.typography.titleMedium)
            Text(text = "Username: ${user.username}")
            Text(text = "Email: ${user.email}")
            Text(text = "Phone: ${user.phone}")
            Text(text = "Address: ${user.address.street}, ${user.address.city}, ${user.address.zipcode}")

            Spacer(modifier = Modifier.height(8.dp))

            // Display Map
            MapView(
                user.name.firstname,
                latitude = user.address.geolocation.lat.toDouble(),
                longitude = user.address.geolocation.long.toDouble()
            )

        }
    }
}

@Composable
fun MapView(name: String, latitude: Double, longitude: Double) {

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(latitude, longitude), 12f)
    }

    GoogleMap(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
        cameraPositionState = cameraPositionState
    ) {
        Marker(
            state = MarkerState(position = LatLng(latitude, longitude)),
            title = "$name's Location",
            snippet = "Lat: $latitude, Long: $longitude"
        )
    }

}
