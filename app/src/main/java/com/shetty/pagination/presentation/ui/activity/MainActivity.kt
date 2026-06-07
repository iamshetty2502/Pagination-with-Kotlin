package com.shetty.pagination.presentation.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.shetty.pagination.presentation.ui.screen.DetailScreen
import com.shetty.pagination.presentation.ui.screen.LoginScreen
import com.shetty.pagination.presentation.ui.screen.MainScreen
import com.shetty.pagination.presentation.ui.screen.SplashScreen
import com.shetty.pagination.presentation.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()

                    NavHost(navController = navController, startDestination = "splash") {
                        composable("splash") {
                            SplashScreen(onTimeout = {
                                navController.navigate("login") {
                                    popUpTo("splash") { inclusive = true }
                                }
                            })
                        }
                        composable("login") {
                            LoginScreen(onLoginSuccess = {
                                navController.navigate("list") {
                                    popUpTo("login") { inclusive = true }
                                }
                            })
                        }
                        composable("list") {
                            MainScreen(
                                viewModel = mainViewModel,
                                onNavigateToDetail = { restaurant ->
                                    mainViewModel.selectRestaurant(restaurant)
                                    navController.navigate("detail")
                                }
                            )
                        }
                        composable(route = "detail") {
                            DetailScreen(
                                onBack = { navController.popBackStack() },
                                mainViewModel = mainViewModel
                            )
                        }
                    }
                }
            }
        }
    }
}


