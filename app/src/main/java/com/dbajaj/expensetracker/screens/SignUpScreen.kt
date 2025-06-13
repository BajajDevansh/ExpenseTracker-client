package com.dbajaj.expensetracker.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dbajaj.expensetracker.R
import com.dbajaj.expensetracker.RetrofitClient
import com.dbajaj.expensetracker.data.UserDTO
import kotlinx.coroutines.launch

@Composable
fun SignUpScreen(){
    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val scope=rememberCoroutineScope()
    Column(modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Text("Welcome", fontSize = 35.sp)
        Text("Please sign up to continue", fontSize = 25.sp)
        Spacer(modifier = Modifier.height(30.dp))
        Row{
            ElevatedCard(modifier = Modifier.padding(end=8.dp),
                shape = CircleShape,
                elevation = androidx.compose.material3.CardDefaults.
                cardElevation(defaultElevation = 10.dp)) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_person_4_24),
                    contentDescription = "Person", modifier = Modifier.size(50.dp)
                )
            }
            ElevatedCard(modifier = Modifier.padding(end=8.dp),
                shape= RectangleShape,
                elevation = androidx.compose.material3.CardDefaults.
                cardElevation(defaultElevation = 10.dp)) {
                TextField(value = fullName,onValueChange = {fullName=it},
                    modifier = Modifier.height(50.dp),label = { Text("Full Name")},
                    singleLine = true)
            }
        }
        Spacer(modifier = Modifier.height(15.dp))
        Row{
            ElevatedCard(modifier = Modifier.padding(end=8.dp),
                shape = CircleShape,
                elevation = androidx.compose.material3.CardDefaults.
                cardElevation(defaultElevation = 10.dp)) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_email_24),
                    contentDescription = "Person", modifier = Modifier.size(50.dp)
                )
            }
            ElevatedCard(modifier = Modifier.padding(end=8.dp),
                shape= RectangleShape,
                elevation = androidx.compose.material3.CardDefaults.
                cardElevation(defaultElevation = 10.dp)) {
                TextField(value =email,onValueChange = {email=it},
                    singleLine = true, modifier = Modifier.height(50.dp),label = { Text("Email")})
            }
        }
        Spacer(modifier = Modifier.height(15.dp))
        Row{
            ElevatedCard(modifier = Modifier.padding(end=8.dp),
                shape = CircleShape,
                elevation = androidx.compose.material3.CardDefaults.
                cardElevation(defaultElevation = 10.dp)) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_password_24),
                    contentDescription = "Person", modifier = Modifier.size(50.dp)
                )
            }
            ElevatedCard(modifier = Modifier.padding(end=8.dp),
                shape = RectangleShape,
                elevation = androidx.compose.material3.CardDefaults.
                cardElevation(defaultElevation = 10.dp)) {
                TextField(value = password,onValueChange = {password=it},
                    modifier = Modifier.height(50.dp),
                    label = { Text("Password")},
                    singleLine = true,
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(
                        autoCorrectEnabled = false,
                        keyboardType = KeyboardType.Password))
            }
        }
        Spacer(modifier = Modifier.height(15.dp))
        Row(horizontalArrangement = Arrangement.Center) {
            ElevatedCard(shape = CircleShape,
                elevation = CardDefaults.cardElevation(10.dp)) {
                Button(onClick = {
                    scope.launch {
                        RetrofitClient.authApi.register(UserDTO(
                            email,fullName,password
                        ))
                    }
                    fullName=""
                    email=""
                    password=""
                }, colors = ButtonDefaults.buttonColors(
                    containerColor= Color.Transparent,
                    contentColor = if(isSystemInDarkTheme())Color.White else Color.Black
                )) {
                    Text("Sign Up")
                }
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        Text("Already have an account? Log in", fontSize = 15.sp,
            modifier = Modifier.clickable{

            })
    }
}