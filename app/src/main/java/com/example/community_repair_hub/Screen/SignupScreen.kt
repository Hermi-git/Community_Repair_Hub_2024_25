package com.example.community_repair_hub.Screen

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.community_repair_hub.ViewModel.SignupViewModel
import com.example.community_repair_hub.ViewModel.SignupViewModelFactory
import com.example.community_repair_hub.Utills.TokenManager
import com.example.community_repair_hub.data.network.repository.AuthRepository

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignupScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewModel: SignupViewModel = viewModel(
        factory = SignupViewModelFactory(
            tokenManager = TokenManager,
            authRepository = AuthRepository
        )
    )
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    // Image picker launcher
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { viewModel.onImagePicked(it) }
    }

    // Handle successful signup
    LaunchedEffect(key1 = uiState.signupSuccess) {
        if (uiState.signupSuccess) {
            Toast.makeText(context, "Signup Successful! Please login.", Toast.LENGTH_SHORT).show()
            navController.navigate("login") {
                popUpTo(navController.graph.startDestinationId) { inclusive = true }
                launchSingleTop = true
            }
            viewModel.resetSignupStatus()
        }
    }

    // Handle signup errors
    LaunchedEffect(key1 = uiState.signupError) {
        uiState.signupError?.let {
            Toast.makeText(context, "Error: $it", Toast.LENGTH_LONG).show()
            viewModel.resetSignupStatus()
        }
    }

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

        // Profile Image Picker
        Text(
            text = "Profile Image",
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Monospace
            )
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            if (uiState.imageUri != null) {
                Image(
                    painter = rememberAsyncImagePainter(uiState.imageUri),
                    contentDescription = "Profile Image",
                    modifier = Modifier
                        .size(64.dp)
                        .clip(CircleShape)
                )
            }
            Spacer(Modifier.width(16.dp))
            Button(onClick = { imagePickerLauncher.launch("image/*") }) {
                Text("Choose Image")
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = uiState.name,
            onValueChange = { viewModel.onNameChange(it) },
            label = { Text(text = "Full Name") },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(0xFF7CFC00),
                unfocusedBorderColor = Color.Gray,
                disabledBorderColor = Color.LightGray
            ),
            singleLine = true
        )
        Spacer(modifier = Modifier.height(20.dp))
        OutlinedTextField(
            value = uiState.email,
            onValueChange = { viewModel.onEmailChange(it) },
            label = { Text(text = "Email Address") },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(0xFF7CFC00),
                unfocusedBorderColor = Color.Gray,
                disabledBorderColor = Color.LightGray
            ),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(20.dp))
        OutlinedTextField(
            value = uiState.password,
            onValueChange = { viewModel.onPasswordChange(it) },
            label = { Text(text = "Password") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(0xFF7CFC00),
                unfocusedBorderColor = Color.Gray,
                disabledBorderColor = Color.LightGray
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
                selected = uiState.selectedRole == "Citizen",
                onClick = { viewModel.onRoleSelected("Citizen") },
                colors = RadioButtonDefaults.colors()
            )
            Text(
                text = "Citizen",
                modifier = Modifier.padding(end = 16.dp)
            )
            RadioButton(
                selected = uiState.selectedRole == "RepairTeam",
                onClick = { viewModel.onRoleSelected("RepairTeam") },
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
            if (uiState.isLoadingRegions) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    color = Color(0xFF7CFC00)
                )
            } else {
                OutlinedTextField(
                    value = uiState.selectedRegion,
                    onValueChange = {},
                    readOnly = true,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color(0xFF7CFC00),
                        unfocusedBorderColor = Color.Gray,
                        disabledBorderColor = Color.LightGray
                    ),
                    label = { Text("Select Region") },
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Filled.ArrowDropDown,
                            contentDescription = "Dropdown Icon",
                            modifier = Modifier.clickable { viewModel.toggleRegionDropdown() }
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { viewModel.toggleRegionDropdown() }
                )

                DropdownMenu(
                    expanded = uiState.isRegionDropdownExpanded,
                    onDismissRequest = { viewModel.toggleRegionDropdown(false) }
                ) {
                    if (uiState.regions.isEmpty()) {
                        DropdownMenuItem(
                            text = { Text("No regions available") },
                            onClick = { }
                        )
                    } else {
                        uiState.regions.forEach { region ->
                            DropdownMenuItem(
                                text = { Text(text = region) },
                                onClick = { viewModel.onRegionSelected(region) }
                            )
                        }
                    }
                }

                if (uiState.regions.isEmpty() && !uiState.isLoadingRegions) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "No regions available",
                            color = Color.Red,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                        Button(
                            onClick = { viewModel.loadRegionsAndCities() },
                            modifier = Modifier.padding(top = 8.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF7CFC00),
                                contentColor = Color.Black
                            )
                        ) {
                            Text("Retry")
                        }
                    }
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
            OutlinedTextField(
                value = uiState.selectedCity,
                onValueChange = {},
                readOnly = true,
                enabled = uiState.selectedRegion.isNotEmpty(),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color(0xFF7CFC00),
                    unfocusedBorderColor = Color.Gray,
                    disabledBorderColor = Color.LightGray
                ),
                label = { Text("Select City") },
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Filled.ArrowDropDown,
                        contentDescription = "Dropdown Icon",
                        modifier = Modifier.clickable { viewModel.toggleCityDropdown() }
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { viewModel.toggleCityDropdown() }
            )

            DropdownMenu(
                expanded = uiState.isCityDropdownExpanded,
                onDismissRequest = { viewModel.toggleCityDropdown(false) }
            ) {
                uiState.cities.forEach { city ->
                    DropdownMenuItem(
                        text = { Text(text = city) },
                        onClick = { viewModel.onCitySelected(city) }
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
            onClick = { viewModel.signup(context) }, // Pass context here!
            enabled = !uiState.signupInProgress,
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
                CircularProgressIndicator(color = Color.Black)
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