package com.example.community_repair_hub.Screen

import android.widget.Toast // Import Toast for showing messages
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator // Import for loading indicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel // Import viewModel
import androidx.navigation.NavHostController
import com.example.community_repair_hub.viewModel.SignupViewModel // Import ViewModel

@Composable
fun SignupScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewModel: SignupViewModel = viewModel() // 1. Get ViewModel instance
) {
    val scrollState = rememberScrollState()
    val uiState by viewModel.uiState.collectAsState() // 2. Collect UI State
    val context = LocalContext.current // Get context for Toasts

    // 5. Handle Success/Error messages (Example using Toast)
    LaunchedEffect(key1 = uiState.signupSuccess) {
        if (uiState.signupSuccess) {
            Toast.makeText(context, "Signup Successful!", Toast.LENGTH_SHORT).show()
            // TODO: Navigate to the next screen (e.g., Login or Home)
             navController.navigate("home") {
                 popUpTo("signup") { inclusive = true }
             }
            viewModel.resetSignupStatus() // Reset status after handling
        }
    }
    LaunchedEffect(key1 = uiState.signupError) {
        uiState.signupError?.let {
            Toast.makeText(context, "Error: $it", Toast.LENGTH_LONG).show()
            viewModel.resetSignupStatus() // Reset status after handling
        }
    }

    // 3. Local state variables are removed
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(40.dp)
            .verticalScroll(scrollState)
    ) {
        Text(
            text = "Welcome to Community Repair Hub",
            style = TextStyle(
                fontSize = 20.sp,
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center
            ),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "Empowering Communities, One Fix at a Time!",
            style = TextStyle(
                fontSize = 18.sp,
                fontWeight = FontWeight.Light,
                textAlign = TextAlign.Center,
                fontFamily = FontFamily.Cursive
            )
        )
        Spacer(modifier = Modifier.height(20.dp))

        // 4. Connect UI Elements
        OutlinedTextField(
            value = uiState.name, // Use state from ViewModel
            onValueChange = { viewModel.onNameChange(it) }, // Call ViewModel function
            label = { Text(text = "Full Name") },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color(0xFF7CFC00),
                unfocusedIndicatorColor = Color.Gray,
                disabledIndicatorColor = Color.LightGray
            ),
            singleLine = true
        )
        Spacer(modifier = Modifier.height(20.dp))
        OutlinedTextField(
            value = uiState.email, // Use state from ViewModel
            onValueChange = { viewModel.onEmailChange(it) }, // Call ViewModel function
            label = { Text(text = "Email Address") },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color(0xFF7CFC00),
                unfocusedIndicatorColor = Color.Gray,
                disabledIndicatorColor = Color.LightGray
            ),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(20.dp))
        OutlinedTextField(
            value = uiState.password, // Use state from ViewModel
            onValueChange = { viewModel.onPasswordChange(it) }, // Call ViewModel function
            label = { Text(text = "Password") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation(),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color(0xFF7CFC00),
                unfocusedIndicatorColor = Color.Gray,
                disabledIndicatorColor = Color.LightGray
            ),
            singleLine = true
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "Select Your Role",
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Start,
                fontFamily = FontFamily.Monospace
            ),
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(
                selected = uiState.selectedRole == "Citizen", // Use state from ViewModel
                onClick = { viewModel.onRoleSelected("Citizen") }, // Call ViewModel function
                colors = RadioButtonDefaults.colors()
            )
            Text(
                text = "Citizen",
                modifier = Modifier.padding(end = 16.dp)
            )
            RadioButton(
                selected = uiState.selectedRole == "Repairteam", // Use state from ViewModel
                onClick = { viewModel.onRoleSelected("Repairteam") }, // Call ViewModel function
                colors = RadioButtonDefaults.colors()
            )
            Text(
                text = "Repairing Team"
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "Address",
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Start,
                fontFamily = FontFamily.Monospace
            ),
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            text = "Region",
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Start,
                fontFamily = FontFamily.Monospace
            ),
            modifier = Modifier.padding(8.dp)
        )
        Box {
            TextField(
                value = uiState.selectedRegion, // Use state from ViewModel
                onValueChange = {}, // Not directly changeable
                readOnly = true,
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color(0xFF7CFC00),
                    unfocusedIndicatorColor = Color.Gray,
                    disabledIndicatorColor = Color.LightGray
                ),
                label = { Text("Select Region") },
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Filled.ArrowDropDown,
                        contentDescription = "Dropdown Icon",
                        modifier = Modifier.clickable { viewModel.toggleRegionDropdown() } // Call ViewModel function
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { viewModel.toggleRegionDropdown() } // Call ViewModel function
            )

            DropdownMenu(
                expanded = uiState.isRegionDropdownExpanded, // Use state from ViewModel
                onDismissRequest = { viewModel.toggleRegionDropdown(false) } // Call ViewModel function
            ) {
                uiState.regions.forEach { region -> // Use list from ViewModel
                    DropdownMenuItem(
                        text = { Text(text = region) },
                        onClick = { viewModel.onRegionSelected(region) } // Call ViewModel function
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "City",
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Start,
                fontFamily = FontFamily.Monospace
            ),
            modifier = Modifier.padding(8.dp)
        )
        Box {
            TextField(
                value = uiState.selectedCity, // Use state from ViewModel
                onValueChange = {}, // Not directly changeable
                readOnly = true,
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color(0xFF7CFC00),
                    unfocusedIndicatorColor = Color.Gray,
                    disabledIndicatorColor = Color.LightGray
                ),
                label = { Text("Select City") },
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Filled.ArrowDropDown,
                        contentDescription = "Dropdown Icon",
                        modifier = Modifier.clickable { viewModel.toggleCityDropdown() } // Call ViewModel function
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { viewModel.toggleCityDropdown() } // Call ViewModel function
            )

            DropdownMenu(
                expanded = uiState.isCityDropdownExpanded, // Use state from ViewModel
                onDismissRequest = { viewModel.toggleCityDropdown(false) } // Call ViewModel function
            ) {
                uiState.cities.forEach { city -> // Use list from ViewModel
                    DropdownMenuItem(
                        text = { Text(text = city) },
                        onClick = { viewModel.onCitySelected(city) } // Call ViewModel function
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(15.dp))

        // Show error message if signupError is not null
        uiState.signupError?.let {
            Text(
                text = it,
                color = Color.Red,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        Button(
            onClick = { viewModel.signup() }, // Call ViewModel function
            enabled = !uiState.signupInProgress, // Disable button when signing up
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .height(60.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF7CFC00),
                contentColor = Color.Black
            )
        ) {
            if (uiState.signupInProgress) {
                CircularProgressIndicator(color = Color.Black) // Show loading indicator
            } else {
                Text(
                    text = "Create Account",
                    style = TextStyle(
                        fontSize = 22.sp,
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Center,
                        fontFamily = FontFamily.SansSerif
                    )
                )
            }
        }
    }
}

