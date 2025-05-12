package com.example.community_repair_hub.Screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.example.community_repair_hub.Components.NavDrawer
import com.example.community_repair_hub.R
import com.example.community_repair_hub.Components.IssueCard
import com.example.community_repair_hub.ViewModel.HomeViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: HomeViewModel
) {
    val brandGreen = Color(0xFF7CFC00)
    var searchValue by remember { mutableStateOf("") }
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val issues by viewModel.issues.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()
    val profileImageUrl by viewModel.profileImageUrl.collectAsState()

    // Prepare the full image URL
    val baseUrl = "http://192.168.39.252:5500"
    val fullProfileImageUrl = if (profileImageUrl?.startsWith("/") == true) {
        baseUrl + profileImageUrl

    } else {
        profileImageUrl ?: ""
    }
    println(fullProfileImageUrl)


    val filteredIssues = remember(issues, searchValue) {
        if (searchValue.isEmpty()) {
            issues
        } else {
            issues.filter { issue ->
                issue.category?.contains(searchValue, ignoreCase = true) == true ||
                        issue.locations?.city?.contains(searchValue, ignoreCase = true) == true ||
                        issue.description?.contains(searchValue, ignoreCase = true) == true
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.fetchIssues()
        viewModel.fetchUserProfile()
    }

    NavDrawer(navController = navController, drawerState = drawerState) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text("Community Repair Hub", color = Color.Black)
                    },
                    navigationIcon = {
                        IconButton(onClick = {
                            scope.launch {
                                if (drawerState.isClosed) drawerState.open() else drawerState.close()
                            }
                        }) {
                            Icon(Icons.Default.Menu, contentDescription = "Menu", tint = Color.Black)
                        }
                    },
                    actions = {
                        IconButton(onClick = { /* Profile click action */ }) {
                            SubcomposeAsyncImage(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(fullProfileImageUrl)
                                    .crossfade(true)
                                    .build(),
                                contentDescription = "Profile",
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(CircleShape),
                                loading = {
                                    CircularProgressIndicator(modifier = Modifier.size(18.dp))
                                },
                                error = {
                                    Image(
                                        painter = painterResource(id = R.drawable.img_5),
                                        contentDescription = "Default Profile",
                                        modifier = Modifier.size(36.dp).clip(CircleShape)
                                    )
                                }
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = brandGreen)
                )
            },
            content = { paddingValues ->
                Box(
                    modifier = Modifier
                        .padding(paddingValues)
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        OutlinedTextField(
                            value = searchValue,
                            onValueChange = { searchValue = it },
                            label = { Text("Search by category, location, or description") },
                            leadingIcon = {
                                Icon(Icons.Default.Search, contentDescription = "Search")
                            },
                            colors = TextFieldDefaults.colors(
                                focusedIndicatorColor = brandGreen,
                                unfocusedIndicatorColor = Color.LightGray,
                                focusedLabelColor = brandGreen,
                                cursorColor = brandGreen
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp)
                        )

                        when {
                            isLoading -> {
                                Box(
                                    modifier = Modifier.fillMaxSize(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    CircularProgressIndicator(color = brandGreen)
                                }
                            }
                            error != null -> {
                                Box(
                                    modifier = Modifier.fillMaxSize(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        verticalArrangement = Arrangement.Center
                                    ) {
                                        Text(
                                            text = error ?: "An error occurred",
                                            color = Color.Red,
                                            modifier = Modifier.padding(16.dp)
                                        )
                                        Button(
                                            onClick = { viewModel.fetchIssues() },
                                            colors = ButtonDefaults.buttonColors(
                                                containerColor = brandGreen,
                                                contentColor = Color.Black
                                            )
                                        ) {
                                            Text("Retry")
                                        }
                                    }
                                }
                            }
                            filteredIssues.isEmpty() -> {
                                Box(
                                    modifier = Modifier.fillMaxSize(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = if (searchValue.isEmpty())
                                            "No issues found"
                                        else
                                            "No matching issues found",
                                        color = Color.Gray
                                    )
                                }
                            }
                            else -> {
                                LazyColumn(
                                    modifier = Modifier.weight(1f),
                                    verticalArrangement = Arrangement.spacedBy(16.dp)
                                ) {
                                    items(filteredIssues) { issue ->
                                        IssueCard(issue = issue, navController = navController)
                                    }
                                }
                            }
                        }

                        Button(
                            onClick = { navController.navigate("report") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 16.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = brandGreen,
                                contentColor = Color.Black
                            )
                        ) {
                            Icon(Icons.Default.Add, contentDescription = "Add")
                            Spacer(Modifier.width(8.dp))
                            Text("Add Issue", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        )
    }
}