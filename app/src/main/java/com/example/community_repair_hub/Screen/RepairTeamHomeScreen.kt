package com.example.community_repair_hub.Screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.community_repair_hub.Components.NavDrawer
import com.example.community_repair_hub.R
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RepairTeamHomeScreen(modifier: Modifier = Modifier, navController: NavHostController) {
    val brandGreen = Color(0xFF7CFC00)
    var searchValue by remember { mutableStateOf("") }
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val scrollState = rememberScrollState()


    NavDrawer(
        navController = navController,
        drawerState = drawerState
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = "Community Repair Hub",
                            color = Color.Black
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = {
                            scope.launch {
                                if (drawerState.isClosed) drawerState.open() else drawerState.close()
                            }
                        }) {
                            androidx.compose.material3.Icon(imageVector = Icons.Default.Menu,
                                contentDescription = "Menu",
                                tint = Color.Black)
                        }
                    },
                    actions = {
                        IconButton(onClick = { /* Navigate to Profile */ }) {
                            Image(
                                painter = painterResource(id = R.drawable.img_5),
                                contentDescription = "Profile",
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(CircleShape)
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = brandGreen
                    )
                )
            },
            content = { paddingValues ->
                Column(
                    modifier = Modifier
                        .padding(paddingValues)
                        .verticalScroll(scrollState)
                        .background(MaterialTheme.colorScheme.background)
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Spacer(Modifier.height(8.dp))

                    // Search Bar
                    OutlinedTextField(
                        value = searchValue,
                        onValueChange = { searchValue = it },
                        label = { Text("Search here...") },
                        colors = TextFieldDefaults.colors(
                            focusedIndicatorColor = brandGreen,
                            unfocusedIndicatorColor = Color.LightGray,
                            focusedLabelColor = brandGreen,
                            cursorColor = brandGreen
                        ),
                        leadingIcon = {
                            androidx.compose.material3.Icon(imageVector = Icons.Default.Search, contentDescription = "Search Icon")
                        },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(Modifier.height(16.dp))

                    repeat(3) {
                        RepairTeamIssueCard(navController = navController, statusColor = Color.Red)
                        Spacer(Modifier.height(16.dp))
                    }
                }
            }


        )
    }
}
@Composable
fun RepairTeamIssueCard(navController: NavController, statusColor: Color) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Issue Image
            Image(
                painter = painterResource(id = R.drawable.img_3),
                contentDescription = "Issue Image",
                modifier = Modifier
                    .height(150.dp)
                    .clip(RoundedCornerShape(12.dp))
            )
            Spacer(Modifier.width(16.dp))

            // Issue Details
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.weight(1f)
            ) {

                Text(text = "Category: Road", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(8.dp))
                Text(text = "Location: Garment, AA", fontSize = 16.sp)
                Spacer(Modifier.height(8.dp))
                Text(text = "Status: Pending", fontSize = 16.sp, color = Color.Red) // Example status
                Spacer(Modifier.height(8.dp))


                Text(
                    text = "View Details",
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    modifier = Modifier
                        .align(Alignment.End)
                        .clickable { navController.navigate("viewDetail") }
                )

                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd as Alignment.Horizontal)
                        .padding(8.dp)
                        .size(20.dp)
                        .clip(CircleShape)
                        .background(statusColor)
                )
            }
        }
    }
}
