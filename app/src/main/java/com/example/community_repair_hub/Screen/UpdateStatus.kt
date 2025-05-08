package com.example.community_repair_hub.Screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateStatusScreen(modifier: Modifier = Modifier, navController: NavHostController) {
    var selectedStatus by remember { mutableStateOf("") }
    var additionalNotes by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Update Issue Status", fontSize = 24.sp, fontWeight = FontWeight.Bold) }, // ✅ Larger Title
                navigationIcon = {
                    IconButton(onClick = { navController.navigate("assigned_issues") }) { // ✅ Ensured navigation works
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.Black
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF7CFC00))
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues).fillMaxSize().padding(24.dp) // ✅ Increased padding to use space
        ) {
            Text("Select Issue Status", fontSize = 22.sp, fontWeight = FontWeight.Bold, modifier = Modifier.align(Alignment.Start))

            Card(
                modifier = Modifier.fillMaxWidth().height(120.dp).padding(vertical = 8.dp), // ✅ Enlarged Status Boxes
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(12.dp)
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = selectedStatus == "Fixed",
                        onClick = { selectedStatus = "Fixed" },
                        colors = RadioButtonDefaults.colors(selectedColor = Color(0xFF006E2E))
                    )
                    Column {
                        Text("Fixed", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                        Text("The issue has been resolved", fontSize = 18.sp, color = Color.Gray)
                    }
                }
            }

            Card(
                modifier = Modifier.fillMaxWidth().height(120.dp).padding(vertical = 8.dp), // ✅ Enlarged Status Boxes
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(12.dp)
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = selectedStatus == "In Progress",
                        onClick = { selectedStatus = "In Progress" },
                        colors = RadioButtonDefaults.colors(selectedColor = Color(0xFF006E2E))
                    )
                    Column {
                        Text("In Progress", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                        Text("We're working on fixing this issue", fontSize = 18.sp, color = Color.Gray)
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Card(
                modifier = Modifier.fillMaxWidth().height(280.dp).padding(vertical = 8.dp), // ✅ Maximized Notes Box Size
                colors = CardDefaults.cardColors(containerColor = Color(0xFFEAEAEA)),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Additional Notes", fontSize = 22.sp, fontWeight = FontWeight.Bold)

                    OutlinedTextField(
                        value = additionalNotes,
                        onValueChange = { additionalNotes = it },
                        placeholder = { Text("Add any details about the repair work...") },
                        modifier = Modifier.fillMaxWidth().height(250.dp) // ✅ Increased height
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Card(
                modifier = Modifier.fillMaxWidth().height(80.dp), // ✅ Enlarged Update Button Box
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                    Button(
                        onClick = { /* Save Status */ },
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF7CFC00)),
                        modifier = Modifier.fillMaxWidth().height(60.dp) // ✅ Increased button size
                    ) {
                        Text("Update Status", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

