package com.jetpack.loginsignup.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun SignUpScreen(navController: NavHostController) {

    var fName by remember { mutableStateOf("") }
    var lName by remember { mutableStateOf("") }
    var contactNumber by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.padding(16.dp)
        )
        {
            Text(text = "Sign UP", style = MaterialTheme.typography.titleLarge)

            OutlinedTextField(
                value = fName,
                onValueChange = {
                fName = it
            },
                label = { Text(text = "Enter First Name")},
                placeholder = { Text(text = "Enter First name")},
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                leadingIcon = { Icon(imageVector = Icons.Default.Face, contentDescription = "NameIcon")}
            )

            OutlinedTextField(
                value = lName,
                onValueChange ={lName = it},
                label = { Text(text = "Enter last name.")},
                placeholder = { Text(text = "Enter last name")},
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                leadingIcon = { Icon(imageVector = Icons.Default.Face, contentDescription = "LnameIcon")}
            )

            OutlinedTextField(
                value = contactNumber,
                onValueChange = {contactNumber = it},
                label = { Text(text = "Enter contact number")},
                placeholder = { Text(text = "Enter contact number")},
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                leadingIcon = { Icon(imageVector = Icons.Default.Phone, contentDescription = "PhoneIcon")}
                )

            OutlinedTextField(
                value = email,
                onValueChange ={email = it},
                label = { Text(text = "Enter email id.")},
                placeholder = { Text(text = "Enter email id")},
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                leadingIcon = { Icon(imageVector = Icons.Default.Email, contentDescription = "EmailIcon")}
            )

            OutlinedTextField(
                value = password,
                onValueChange = {password = it},
                label = { Text(text = "Enter password")},
                placeholder = { Text(text = "Enter password")},
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                leadingIcon = { Icon(imageVector = Icons.Default.Lock, contentDescription = "PasswordIcon")},
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    val iconText = if (passwordVisible) "Hide" else "Show"

                    TextButton(onClick = { passwordVisible = !passwordVisible }) {
                        Text(text = iconText)
                    }
                }
            )

            if(errorMessage.isNotEmpty()){
                Text(text = errorMessage, color = MaterialTheme.colorScheme.error)
            }
            
            Button(onClick = { 
                
                errorMessage = if(fName.isBlank() || lName.isBlank() || contactNumber.isBlank() || email.isBlank() || password.isBlank()){
                    "Please enter all fields"
                }else{
                    ""
                }
            }) {
                Text(text = "SignUP")
            }
            
            TextButton(onClick =  { navController.navigate("login")}) {
                Text(text = "Already have an account? Login")
            }
        }
    }
}