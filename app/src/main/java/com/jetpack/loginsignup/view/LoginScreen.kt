package com.jetpack.loginsignup.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.jetpack.loginsignup.utils.Repository
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(navController: NavHostController) {

    var email by remember { mutableStateOf(("")) }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    val repository = Repository()
    val scope = rememberCoroutineScope()
    var token by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        if (isLoading) {
            // Show progress bar when loading
            CircularProgressIndicator()
        } else {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.padding(16.dp)
            ) {
                Text(text = "Sign In", style = MaterialTheme.typography.titleLarge)

                OutlinedTextField(
                    value = email,
                    onValueChange = {
                        email = it
                    },
                    label = { Text(text = "Enter email address") },
                    placeholder = { Text(text = "Enter your email id") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    leadingIcon = { Icon(imageVector = Icons.Default.Email, contentDescription = "emailIcon" )}
                )

                OutlinedTextField(
                    value = password,
                    onValueChange ={ password = it},
                    label = { Text(text = "Enter password") },
                    placeholder = { Text(text = "Enter your password")},
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    leadingIcon = { Icon(imageVector = Icons.Default.Lock, contentDescription = "emailIcon" )},
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        val iconText = if (passwordVisible) "Hide" else "Show"

                        TextButton(onClick = { passwordVisible = !passwordVisible }) {
                            Text(text = iconText)
                        }
                    }
                )

                if (errorMessage.isNotEmpty()){
                    Text(text = errorMessage, color = MaterialTheme.colorScheme.error)
                }

                if (token.isNotEmpty()) {
                    Text("Token: $token")
                    navController.navigate("dashboard/$email")
                }

                Button(onClick = {
                    errorMessage = if (email.isBlank() || password.isBlank()) {
                        "Please fill in all fields"
                    } else {

                        scope.launch {
                            isLoading = true // Show the progress bar
                            try {
                                val response = repository.login(email, password)
                                token = response.token
                            }catch (exp: Exception){
                                errorMessage = "Error: ${exp.message}"
                            }
                            finally {
                                isLoading = false // Hide the progress bar
                            }
                        }

                        ""
                    }
                }) {
                    Text("Login")
                }

                TextButton(onClick = { navController.navigate("signup")}) {
                    Text("Don't have an account? Sign up")
                }

            }
        }




    }
}