package com.example.community_repair_hub.Screen
//
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.background
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.rememberScrollState
//import androidx.compose.foundation.shape.CircleShape
//import androidx.compose.foundation.verticalScroll
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Menu
//import androidx.compose.material.icons.filled.Search
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.navigation.NavController
//import androidx.navigation.NavHostController
//import com.example.community_repair_hub.Components.IssueCard
//import com.example.community_repair_hub.Components.NavDrawer
//import com.example.community_repair_hub.R
//import com.example.community_repair_hub.ViewModel.RepairTeamHomeViewModel
//import com.example.community_repair_hub.ViewModel.SearchType
//import kotlinx.coroutines.launch
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun RepairTeamHomeScreen(
//    modifier: Modifier = Modifier,
//    navController: NavHostController,
//    viewModel: RepairTeamHomeViewModel
//) {
//    val brandGreen = Color(0xFF7CFC00)
//    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
//    val scope = rememberCoroutineScope()
//    val scrollState = rememberScrollState()
//
//    // State from ViewModel
//    val issues by viewModel.issues.collectAsState()
//    val isLoading by viewModel.isLoading.collectAsState()
//    val error by viewModel.error.collectAsState()
//    val searchQuery by viewModel.searchQuery.collectAsState()
//    val searchType by viewModel.searchType.collectAsState()
//    val selectedStatus by viewModel.selectedStatus.collectAsState()
//
//    // Local state for search
//    var showSearchOptions by remember { mutableStateOf(false) }
//
//
//    NavDrawer(navController = navController, drawerState = drawerState) {
//        Scaffold(
//            topBar = {
//                TopAppBar(
//                    title = { Text("Community Repair Hub", color = Color.Black) },
//                    navigationIcon = {
//                        IconButton(onClick = {
//                            scope.launch {
//                                if (drawerState.isClosed) drawerState.open() else drawerState.close()
//                            }
//                        }) {
//                            Icon(Icons.Default.Menu, contentDescription = "Menu", tint = Color.Black)
//                        }
//                    },
//                    actions = {
//                        IconButton(onClick = { showSearchOptions = !showSearchOptions }) {
//                            Icon(Icons.Default.Search, contentDescription = "Search", tint = Color.Black)
//                        }
//                        IconButton(onClick = { /* Navigate to Profile */ }) {
//                            Image(
//                                painter = painterResource(id = R.drawable.img_5),
//                                contentDescription = "Profile",
//                                modifier = Modifier
//                                    .size(36.dp)
//                                    .clip(CircleShape)
//                            )
//                        }
//                    },
//                    colors = TopAppBarDefaults.topAppBarColors(containerColor = brandGreen)
//                )
//            },
//            content = { paddingValues ->
//                Column(
//                    modifier = Modifier
//                        .padding(paddingValues)
//                        .verticalScroll(scrollState)
//                        .background(MaterialTheme.colorScheme.background)
//                        .padding(horizontal = 16.dp, vertical = 8.dp)
//                ) {
//
//                    if (showSearchOptions) {
//                        Column {
//                            OutlinedTextField(
//                                value = searchQuery,
//                                onValueChange = {
//                                    viewModel.setSearchQuery(it)
//                                    if (searchType != SearchType.NONE) {
//                                        viewModel.performSearch()
//                                    }
//                                },
//                                label = { Text("Search...") },
//                                trailingIcon = {
//                                    IconButton(onClick = { viewModel.performSearch() }) {
//                                        Icon(Icons.Default.Search, contentDescription = "Search")
//                                    }
//                                },
//                                modifier = Modifier.fillMaxWidth()
//                            )
