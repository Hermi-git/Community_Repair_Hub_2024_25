package com.example.community_repair_hub.Screen

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.community_repair_hub.ViewModel.IssueDetailViewModel
import com.example.community_repair_hub.data.network.repository.IssueRepository

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AssignedIssueScreen(
    issueId: String,
    navController: NavHostController,
    viewModel: IssueDetailViewModel = remember { IssueDetailViewModel(IssueRepository()) }
) {
    val issue by viewModel.issue.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()
    val brandGreen = Color(0xFF7CFC00)
    var showStatusDialog by remember { mutableStateOf(false) }
    var showNotesDialog by remember { mutableStateOf(false) }
    var newStatus by remember { mutableStateOf("") }
    var newNotes by remember { mutableStateOf("") }

    LaunchedEffect(issueId) {
        viewModel.fetchIssue(issueId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Assigned Issue", color = Color.Black) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.Black
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = brandGreen)
            )
        }
    ) { paddingValues ->
        when {
            isLoading -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
            error != null -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Error: $error", color = Color.Red)
            }
            issue != null -> {
                val baseUrl = "http://192.168.39.252:5500"
                val imageUrl = if (issue?.imageURL?.startsWith("/") == true) {
                    baseUrl + issue?.imageURL
                } else {
                    issue?.imageURL ?: ""
                }
                Column(
                    modifier = Modifier
                        .padding(paddingValues)
                        .verticalScroll(rememberScrollState())
                        .padding(16.dp)
                ) {
                    // Action Buttons Card
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        shape = RoundedCornerShape(12.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Button(
                                onClick = { showStatusDialog = true },
                                colors = ButtonDefaults.buttonColors(containerColor = brandGreen)
                            ) {
                                Icon(Icons.Default.Edit, contentDescription = "Update Status")
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("Update Status")
                            }
                            Button(
                                onClick = { showNotesDialog = true },
                                colors = ButtonDefaults.buttonColors(containerColor = brandGreen)
                            ) {
                                Icon(Icons.Default.Edit, contentDescription = "Add Notes")
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("Add Notes")
                            }
                        }
                    }

                    // Issue Details Card
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        shape = RoundedCornerShape(12.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            AsyncImage(
                                model = imageUrl,
                                contentDescription = "Issue Image",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(200.dp)
                                    .clip(RoundedCornerShape(12.dp))
                                    .border(2.dp, brandGreen, RoundedCornerShape(12.dp))
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Text("Category: ${issue?.category ?: "-"}", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                            Spacer(modifier = Modifier.height(8.dp))
                            Text("Description:", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(issue?.description ?: "-", fontSize = 14.sp)
                            Spacer(modifier = Modifier.height(16.dp))
                            Divider()
                            Spacer(modifier = Modifier.height(8.dp))
                            Text("Location: ${issue?.locations?.city ?: "-"}, ${issue?.locations?.specificArea ?: "-"}", fontSize = 16.sp)
                            Spacer(modifier = Modifier.height(8.dp))
                            Divider()
                            Spacer(modifier = Modifier.height(8.dp))
                            Text("Issue Date: ${issue?.issueDate?.take(10) ?: "-"}", fontSize = 16.sp)
                            Spacer(modifier = Modifier.height(8.dp))
                            Divider()
                            Spacer(modifier = Modifier.height(8.dp))
                            Text("Status: ${issue?.status ?: "-"}", fontSize = 16.sp)
                            Spacer(modifier = Modifier.height(8.dp))
                            Divider()
                            Spacer(modifier = Modifier.height(16.dp))
                            Text("✅ Current Status: ${issue?.status ?: "-"}", fontSize = 16.sp)
                            Spacer(modifier = Modifier.height(8.dp))
                            Text("📅 Last Updated: ${issue?.updatedAt ?: "-"}", fontSize = 16.sp)
                            Spacer(modifier = Modifier.height(8.dp))
                            Text("🛠️ Repair Notes: ${issue?.repairNotes ?: "No notes"}", fontSize = 16.sp)
                        }
                    }
                }
            }
        }
    }

    // Status Update Dialog
    if (showStatusDialog) {
        AlertDialog(
            onDismissRequest = { showStatusDialog = false },
            title = { Text("Update Status") },
            text = {
                Column {
                    Text("Current Status: ${issue?.status}")
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = newStatus,
                        onValueChange = { newStatus = it },
                        label = { Text("New Status") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        // TODO: Implement status update logic
                        showStatusDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = brandGreen)
                ) {
                    Text("Update")
                }
            },
            dismissButton = {
                TextButton(onClick = { showStatusDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }

    // Notes Update Dialog
    if (showNotesDialog) {
        AlertDialog(
            onDismissRequest = { showNotesDialog = false },
            title = { Text("Add Repair Notes") },
            text = {
                Column {
                    Text("Current Notes: ${issue?.repairNotes ?: "No notes"}")
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = newNotes,
                        onValueChange = { newNotes = it },
                        label = { Text("New Notes") },
                        modifier = Modifier.fillMaxWidth(),
                        minLines = 3
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        // TODO: Implement notes update logic
                        showNotesDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = brandGreen)
                ) {
                    Text("Update")
                }
            },
            dismissButton = {
                TextButton(onClick = { showNotesDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}