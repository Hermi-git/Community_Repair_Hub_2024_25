package com.example.community_repair_hub

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.community_repair_hub.Screen.*
import com.example.community_repair_hub.Utills.NavigationGuard
import com.example.community_repair_hub.Utills.TokenManager
import com.example.community_repair_hub.ViewModel.*

@Composable
fun AppNavigation(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "auth") {
        composable("auth") {
            AuthScreen(modifier, navController)
        }
        composable("login") {
            LoginScreen(modifier, navController)
        }
        composable("signup") {
            SignupScreen(modifier, navController)
        }
        composable("home") {
            NavigationGuard(navController = navController, requiredRole = "citizen") {
                HomeScreen(modifier, navController, viewModel = HomeViewModel())
            }
        }
        composable("report") {
            NavigationGuard(navController = navController, requiredRole = "citizen") {
                ReportIssueScreen(modifier, navController)
            }
        }
        composable("viewdetail") {
            NavigationGuard(navController = navController, requiredRole = "citizen") {
                ViewDetailScreen(modifier, navController)
            }
        }
        composable("repairhome") {
            NavigationGuard(navController = navController, requiredRole = "repairteam") {
                RepairTeamHomeScreen(
                    modifier = modifier,
                    navController = navController,
                    viewModel = RepairTeamHomeViewModel()
                )
            }
        }
        composable("repairDetail") {
            NavigationGuard(navController = navController, requiredRole = "repairteam") {
                RepairDetailScreen(modifier, navController)
            }
        }
    }

}