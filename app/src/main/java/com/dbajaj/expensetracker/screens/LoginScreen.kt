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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dbajaj.expensetracker.R
import com.dbajaj.expensetracker.RetrofitClient
import com.dbajaj.expensetracker.TokenManager
import com.dbajaj.expensetracker.data.LoginRequest
import com.dbajaj.expensetracker.viewModel.AuthViewModel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    authViewModel: AuthViewModel
){
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val scope =rememberCoroutineScope()
    val context=LocalContext.current
    Column(modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {
        Text(text = "Welcome Back", fontSize = 35.sp)
        Text(text = "Please login to continue", fontSize = 25.sp)
        Spacer(Modifier.height(30.dp))
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
                    visualTransformation = PasswordVisualTransformation(),keyboardOptions = KeyboardOptions(
                        autoCorrectEnabled = false,
                        keyboardType = KeyboardType.Password)
                )
            }
        }
        Spacer(modifier = Modifier.height(15.dp))
        Row(horizontalArrangement = Arrangement.Center) {
            ElevatedCard(shape = CircleShape,
                elevation = CardDefaults.cardElevation(10.dp)) {
                Button(onClick = {
                    scope.launch {
                        val loginRequest=LoginRequest(email,password)
                        try {
                            val loginResponse= RetrofitClient.authApi.login(loginRequest)
                            if(loginResponse.jwtToken.isNotEmpty()){
                                TokenManager.saveSession(
                                    context = context,
                                    token = loginResponse.jwtToken,
                                    username = loginResponse.username,
                                    userId = loginResponse.userId
                                )
                                authViewModel.login(loginResponse.jwtToken)

                            }
                            email=""
                            password=""
                        }catch (e:Exception){
                            println("Login Failed "+e.message)
                            email=""
                            password=""
                        }
                    }
                }, colors = ButtonDefaults.buttonColors(
                    containerColor= Color.Transparent,
                    contentColor = if(isSystemInDarkTheme())Color.White else Color.Black
                )) {
                    Text("Sign In")
                }
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        Text("Don't have an account? Sign Up", fontSize = 15.sp,
            modifier = Modifier.clickable{

            })
    }
}
