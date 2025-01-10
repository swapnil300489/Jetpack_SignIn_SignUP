package com.jetpack.loginsignup.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jetpack.loginsignup.utils.Constants
import com.jetpack.loginsignup.ui.theme.LoginSignUPTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LoginSignUPTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    /*Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )*/
                    MainApp(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}


@Composable
fun MainApp(modifier: Modifier = Modifier){
    //AppNavigation()
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "login") {
        composable(Constants.SIGN_IN_Screen) { LoginScreen(navController) }
        composable(Constants.SIGN_UP_Screen) { SignUpScreen(navController) }
        composable(Constants.DASHBOARD_Screen+"/{email}") {
                it->
            val email = it.arguments?.getString("email")?: ""
            DashboardScreen(navController, email) }
    }
}



@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    LoginSignUPTheme {
        Greeting("Android")
    }
}