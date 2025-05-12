package com.example.community_repair_hub.Utills
//
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.setValue
//import androidx.navigation.NavHostController
//import android.widget.Toast
//import androidx.compose.ui.platform.LocalContext
//
//@Composable
//fun NavigationGuard(
//    navController: NavHostController,
//    requiredRole: String,
//    content: @Composable () -> Unit
//) {
//    val context = LocalContext.current
//    var isAuthorized by remember { mutableStateOf(false) }
//
//    LaunchedEffect(Unit) {
//        val userRole = TokenManager.getRole()
//        isAuthorized = userRole?.equals(requiredRole, ignoreCase = true) == true
//
//        if (!isAuthorized) {
//            Toast.makeText(context, "Unauthorized access", Toast.LENGTH_SHORT).show()
//            navController.navigate("auth") {
//                popUpTo(navController.graph.startDestinationId) { inclusive = true }
//            }
//        }
//    }
//
//    if (isAuthorized) {
//        content()
//    }
//}