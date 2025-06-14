package com.dbajaj.expensetracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dbajaj.expensetracker.screens.LoginScreen
import com.dbajaj.expensetracker.screens.SignUpScreen
import com.dbajaj.expensetracker.screens.TransactionScreen
import com.dbajaj.expensetracker.ui.theme.ExpenseTrackerTheme
import com.dbajaj.expensetracker.viewModel.AuthViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ExpenseTrackerTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MyApp(modifier=Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun MyApp(modifier: Modifier= Modifier,authViewModel: AuthViewModel= viewModel()){
    val navController=rememberNavController()
    LaunchedEffect(authViewModel.isLoggedIn) {
        if (authViewModel.isLoggedIn) {
            navController.navigate("transaction"){
                popUpTo("login"){
                    inclusive=true
                }
            }
        } else {
            navController.navigate("login"){
                popUpTo(0)
            }
        }
    }
    NavHost(navController = navController, startDestination = "login"){
        composable("login"){
            LoginScreen(authViewModel,navController)
        }
        composable("signUp"){
            SignUpScreen(navController)
        }
        composable("transaction"){
            TransactionScreen(navHostController = navController)
        }
    }
}