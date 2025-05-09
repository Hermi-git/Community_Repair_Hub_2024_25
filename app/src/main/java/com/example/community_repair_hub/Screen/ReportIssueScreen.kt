package com.example.community_repair_hub.Screen

import android.app.Application
import android.icu.text.SimpleDateFormat
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.community_repair_hub.ViewModel.ReportIssueViewModel
import kotlinx.coroutines.delay
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportIssueScreen(modifier: Modifier = Modifier, navController: NavController) {
    val context = LocalContext.current
    val viewModel: ReportIssueViewModel = viewModel(
        factory = object : ViewModelProvider.AndroidViewModelFactory(context.applicationContext as Application) {}
    )

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            uri?.let {
                viewModel.imageUri = it
                viewModel.uploadImage(
                    uri = it,
                    context = context,
                    onSuccess = { url ->
                        Log.d("UPLOAD", "Image URL: $url")
                        viewModel.imageUrl = url
                    },
                    onError = { error ->
                        Log.e("UPLOAD", error)
                        // Show error to user
                    }
                )
            }
        }
    )

    var expandedCity by remember { mutableStateOf(false) }
    val cities = listOf("City 1", "City 2", "City 3")
    var expandedAddress by remember { mutableStateOf(false) }
    val addresses = listOf("Address 1", "Address 2", "Address 3")
    var showDatePicker by remember { mutableStateOf(false) }

    // Show error/success messages
    LaunchedEffect(viewModel.errorMessage) {
        viewModel.errorMessage?.let { message ->
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }

    LaunchedEffect(viewModel.successMessage) {
        viewModel.successMessage?.let { message ->
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            delay(2000) // Show for 2 seconds
            navController.popBackStack() // Go back after successful submission
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Report Issue") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
        ) {
            // Rest of your code remains the same
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .background(Color.Gray.copy(alpha = 0.3f))
                    .clickable {
                        imagePickerLauncher.launch(
                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                        )
                    }
                    .align(Alignment.CenterHorizontally),
                contentAlignment = Alignment.Center
            ) {
                viewModel.imageUri?.let { uri ->
                    Image(
                        painter = rememberAsyncImagePainter(uri),
                        contentDescription = "Selected Image",
                        modifier = Modifier
                            .size(120.dp)
                            .clip(CircleShape)
                    )
                } ?: run {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Upload photo",
                        tint = Color.Gray,
                        modifier = Modifier.size(48.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Category Field
            OutlinedTextField(
                value = viewModel.category,
                onValueChange = { viewModel.category = it },
                label = { Text("Category (e.g., Road, Electrical)") },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color(0xFF7CFC00),
                    unfocusedIndicatorColor = Color.Gray,
                    disabledIndicatorColor = Color.LightGray
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Location Section
            Text(
                text = "Location",
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Start,
                    fontFamily = FontFamily.Monospace
                ),
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // City Dropdown
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
                    value = viewModel.city,
                    onValueChange = {},
                    label = { Text("Select City") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = Color(0xFF7CFC00),
                        unfocusedIndicatorColor = Color.Gray,
                        disabledIndicatorColor = Color.LightGray
                    ),
                    readOnly = true,
                    trailingIcon = {
                        IconButton(onClick = { expandedCity = true }) {
                            Icon(Icons.Default.ArrowDropDown, contentDescription = "Dropdown")
                        }
                    }
                )
                DropdownMenu(
                    expanded = expandedCity,
                    onDismissRequest = { expandedCity = false }
                ) {
                    cities.forEach { city ->
                        DropdownMenuItem(
                            text = { Text(text = city) },
                            onClick = {
                                viewModel.city = city
                                expandedCity = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Address Dropdown
            Text(
                text = "Specific Address",
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
                    value = viewModel.specificAddress,
                    onValueChange = {},
                    label = { Text("Select Address") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = Color(0xFF7CFC00),
                        unfocusedIndicatorColor = Color.Gray,
                        disabledIndicatorColor = Color.LightGray
                    ),
                    readOnly = true,
                    trailingIcon = {
                        IconButton(onClick = { expandedAddress = true }) {
                            Icon(Icons.Default.ArrowDropDown, contentDescription = "Dropdown")
                        }
                    }
                )
                DropdownMenu(
                    expanded = expandedAddress,
                    onDismissRequest = { expandedAddress = false }
                ) {
                    addresses.forEach { address ->
                        DropdownMenuItem(
                            text = { Text(text = address) },
                            onClick = {
                                viewModel.specificAddress = address
                                expandedAddress = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Description Field
            Text(
                text = "Description",
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Start,
                    fontFamily = FontFamily.Monospace
                ),
                modifier = Modifier.padding(bottom = 8.dp)
            )

            OutlinedTextField(
                value = viewModel.description,
                onValueChange = { viewModel.description = it },
                label = { Text("Description of Issue") },
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color(0xFF7CFC00),
                    unfocusedIndicatorColor = Color.Gray,
                    disabledIndicatorColor = Color.LightGray
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                maxLines = 5
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Date Field
            Text(
                text = "Date of Issue",
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Start,
                    fontFamily = FontFamily.Monospace
                ),
                modifier = Modifier.padding(bottom = 8.dp)
            )

            OutlinedTextField(
                value = viewModel.date,
                onValueChange = {},
                label = { Text("dd/MM/yyyy") },
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color(0xFF7CFC00),
                    unfocusedIndicatorColor = Color.Gray,
                    disabledIndicatorColor = Color.LightGray
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { showDatePicker = true },
                readOnly = true,
                trailingIcon = {
                    IconButton(onClick = { showDatePicker = true }) {
                        Icon(
                            imageVector = Icons.Default.DateRange,
                            contentDescription = "Select Date"
                        )
                    }
                }
            )

            if (showDatePicker) {
                val datePickerState = rememberDatePickerState()

                DatePickerDialog(
                    onDismissRequest = { showDatePicker = false },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                val selectedMillis = datePickerState.selectedDateMillis
                                if (selectedMillis != null) {
                                    val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                                    viewModel.date = formatter.format(Date(selectedMillis))
                                }
                                showDatePicker = false
                            }
                        ) {
                            Text("OK")
                        }
                    },
                    dismissButton = {
                        TextButton(
                            onClick = { showDatePicker = false }
                        ) {
                            Text("Cancel")
                        }
                    }
                ) {
                    DatePicker(state = datePickerState)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Notice Card
            Card(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "⚠️ Important Notice:\n" +
                            "Submit accurate and clear reports to ensure timely repairs. " +
                            "False or incomplete information may delay responses. " +
                            "Misuse of this form will not be processed. Your cooperation helps keep our community safe.",
                    modifier = Modifier.padding(12.dp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Submit Button
            Button(
                onClick = {
                    viewModel.submitReport(
                        context = context,
                        onSuccess = {
                            // Handled by success message observer
                        },
                        onError = { error ->
                            // Handled by error message observer
                        }
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                    .height(60.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF7CFC00),
                    contentColor = Color.Black
                ),
                enabled = !viewModel.isLoading
            ) {
                if (viewModel.isLoading) {
                    CircularProgressIndicator(color = Color.White)
                } else {
                    Text("Submit")
                }
            }
        }
    }
}