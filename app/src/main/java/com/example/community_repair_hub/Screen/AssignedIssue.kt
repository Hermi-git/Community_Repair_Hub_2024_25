package com.example.community_repair_hub.Screen

import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.community_repair_hub.R

data class Issue(
    val imageResId: Int,
    val category: String,
    val location: String,
    val issueDate: String,
    val status: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AssignedIssuesScreen(modifier: Modifier = Modifier, navController: NavHostController) {
    val brandGreen = Color(0xFF7CFC00)
    val lazyListState = rememberLazyListState()
    val issues = listOf(
        Issue(R.drawable.img_3, "Road", "Garment, AA", "3/17/2025", "Not Addressed"),
        Issue(R.drawable.img_3, "Electricity", "Bole, AA", "3/18/2025", "In Progress"),
        Issue(R.drawable.img_3, "Water Supply", "Mexico, AA", "3/19/2025", "Completed")
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Assigned Issues Page", color = Color.Black) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.Black
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = brandGreen)
            )
        }
    ) { paddingValues ->
        LazyColumn(
            state = lazyListState,
            modifier = Modifier.padding(paddingValues).padding(16.dp)
        ) {
            items(issues) { issue ->
                IssueCard(issue, brandGreen)
                Spacer(modifier = Modifier.height(16.dp)) // ✅ Space between issue boxes
            }
        }
    }
}
@Composable
fun IssueCard(issue: Issue, brandGreen: Color) {
    Card(
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(2.dp, brandGreen),
        modifier = Modifier.fillMaxWidth().padding(8.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = issue.imageResId),
                contentDescription = "Issue Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(120.dp)
                    .clip(RoundedCornerShape(12.dp))
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Surface(
                            color = when (issue.status) {
                                "Completed" -> Color.Green
                                "In Progress" -> Color(0xFFFFA500)
                                else -> Color.Red
                            },
                            shape = CircleShape,
                            modifier = Modifier.size(10.dp)
                        ) {}

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = issue.status,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF568B9F)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))
                Text("Category: ${issue.category}", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Text(text = "Location: ${issue.location}",fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Text("Issue Date: ${issue.issueDate}", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Spacer(modifier = Modifier.height(16.dp))
                Box(modifier = Modifier.fillMaxWidth()) {
                    TextButton(
                        onClick = { /* Navigate or update action */ },
                        modifier = Modifier.align(Alignment.BottomEnd).height(40.dp)
                    ) {
                        Text(
                            text = "View & Update",
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF568B9F)
                        )

                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AssignedIssuesPreview() {
    AssignedIssuesScreen(navController = rememberNavController())
}
