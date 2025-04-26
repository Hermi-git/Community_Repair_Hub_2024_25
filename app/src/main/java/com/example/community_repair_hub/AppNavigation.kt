package com.example.community_repair_hub

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.community_repair_hub.Screen.AuthScreen
import com.example.community_repair_hub.Screen.HomeScreen
import com.example.community_repair_hub.Screen.LoginScreen
import com.example.community_repair_hub.Screen.SignUpScreen

@Composable

fun AppNavigation(modifier: Modifier=Modifier){
    var navController = rememberNavController()


    NavHost(navController = navController, startDestination = "auth"){
        composable("auth"){
            AuthScreen(modifier,navController)
        }
        composable("login"){
            LoginScreen(modifier,navController)
        }
        composable("signup"){
            SignUpScreen(modifier,navController)
        }

        composable("home"){
            HomeScreen(modifier,navController)
        }

    }


}