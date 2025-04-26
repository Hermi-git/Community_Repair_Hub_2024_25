package com.example.community_repair_hub

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.community_repair_hub.Screen.AuthScreen

@Composable

fun AppNavigation(modifier: Modifier=Modifier){
    var navController = rememberNavController()


    NavHost(navController = navController, startDestination = "auth"){
        composable("auth"){
            AuthScreen(modifier,navController)
        }

    }
}