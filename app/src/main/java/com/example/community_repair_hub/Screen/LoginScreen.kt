package com.example.community_repair_hub.Screen

import android.widget.Toast // Import Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row // Import Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize // Import fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size // Import size
import androidx.compose.foundation.layout.width // Import width
import androidx.compose.foundation.rememberScrollState // Import rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll // Import verticalScroll
import androidx.compose.material.icons.Icons // Import Icons
import androidx.compose.material.icons.filled.Email // Import Email icon
import androidx.compose.material.icons.filled.Lock // Import Lock icon
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator // Import CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon // Import Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect // Import LaunchedEffect
import androidx.compose.runtime.collectAsState // Import collectAsState
import androidx.compose.runtime.getValue // Import getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext // Import LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation // Import PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel // Import viewModel
import androidx.navigation.NavHostController
import com.example.community_repair_hub.viewModel.LoginViewModel // Import LoginViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewModel: LoginViewModel = viewModel() // 1. Get ViewModel instance
) {
    val uiState by viewModel.uiState.collectAsState() // 2. Collect UI State
    val context = LocalContext.current // Get context for Toasts
    val scrollState = rememberScrollState() // Add scroll state

    // 5. Handle Success/Error messages and Navigation
    LaunchedEffect(key1 = uiState.loginSuccess) {
        if (uiState.loginSuccess) {
            Toast.makeText(context, "Login Successful! Token: ${uiState.authToken ?: "Not Received"}", Toast.LENGTH_LONG).show()
            // TODO: Securely store the uiState.authToken
            // Navigate to home screen after successful login
            navController.navigate("home") {
                // Optional: Clear back stack up to login or start destination
                popUpTo(navController.graph.startDestinationId) { inclusive = true }
                launchSingleTop = true // Avoid multiple copies of home screen
            }
            viewModel.resetLoginStatus() // Reset status after handling
        }
    }
    LaunchedEffect(key1 = uiState.loginError) {
        uiState.loginError?.let {
            Toast.makeText(context, "Error: $it", Toast.LENGTH_LONG).show()
            viewModel.resetLoginStatus() // Reset status after handling
        }
    }

    // 3. Local state variables (email, password) are removed

    Column(
        modifier = modifier
            .fillMaxSize() // Use fillMaxSize for potential scrolling
            .padding(
                start = 32.dp,
                top = 72.dp,
                end = 32.dp,
                bottom = 52.dp
            )
            .verticalScroll(scrollState), // Make column scrollable
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Welcome back to the Community Repair Hub",
            style = TextStyle(
                fontSize = 40.sp,
                fontFamily = FontFamily.Cursive,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
            )
        )
        Spacer(Modifier.height(20.dp))
        Text(
            text = "Empowering Communities, One Fix at a Time!",
            style = TextStyle(
                fontSize = 25.sp,
                fontFamily = FontFamily.Cursive,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
            )
        )
        Spacer(Modifier.height(20.dp))

        // 4. Connect UI Elements (with improved styling)
        OutlinedTextField(
            value = uiState.email, // Use state from ViewModel
            onValueChange = { viewModel.onEmailChange(it) }, // Call ViewModel function
            placeholder = { Text("Email") },
            label = { Text("Email") },
            leadingIcon = { // Add leading icon for email
                Icon(Icons.Default.Email, contentDescription = "Email Icon")
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color(0xFF7CFC00),
                unfocusedIndicatorColor = Color.Gray,
                disabledIndicatorColor = Color.LightGray
            ),
            singleLine = true
        )

        Spacer(Modifier.height(10.dp))

        OutlinedTextField(
            value = uiState.password, // Use state from ViewModel
            onValueChange = { viewModel.onPasswordChange(it) }, // Call ViewModel function
            placeholder = { Text("Password") },
            label = { Text("Password") },
            leadingIcon = { // Add leading icon for password
                Icon(Icons.Default.Lock, contentDescription = "Password Icon")
            },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation(), // Apply password transformation
            shape = RoundedCornerShape(12.dp),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color(0xFF7CFC00),
                unfocusedIndicatorColor = Color.Gray,
                disabledIndicatorColor = Color.LightGray
            ),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(30.dp))

        // Show error message if loginError is not null
        uiState.loginError?.let {
            Text(
                text = it,
                color = Color.Red,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        Button(
            onClick = { viewModel.login() }, // Call ViewModel function
            enabled = !uiState.loginInProgress, // Disable button when logging in
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF7CFC00),
                contentColor = Color.Black
            )
        ) {
            // Improved Loading Indicator Style
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                if (uiState.loginInProgress) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp), // Control indicator size
                        color = Color.Black,
                        strokeWidth = 2.dp // Make indicator thinner
                    )
                    Spacer(Modifier.width(8.dp)) // Add space between indicator and text
                }
                Text(
                    text = if (uiState.loginInProgress) "Logging in..." else "Login", // Change text during loading
                    style = TextStyle(
                        fontSize = 25.sp,
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Center,
                        fontFamily = FontFamily.SansSerif
                    )
                )
            }
        }
        Text(
            text = "Need help with your password?",
            fontSize = 20.sp,
            color = Color.Gray,
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(top = 8.dp, bottom = 8.dp)
                .clickable { /* TODO: Navigate to password reset screen */ },
            textAlign = TextAlign.Center
        )
        Text(
            text = "Don't have an account yet?",
            fontSize = 20.sp,
            color = Color.Gray,
            fontWeight = FontWeight.Bold,
        )
        Text(
            text = "Join us now!",
            fontSize = 20.sp,
            color = Color(0xFF00C853),
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(top = 8.dp)
                .clickable { navController.navigate("signup") }, // Keep navigation to signup
            textAlign = TextAlign.Center
        )
    }
}

