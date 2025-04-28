package com.example.community_repair_hub.Screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
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
import com.example.community_repair_hub.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ViewDetailScreen(modifier: Modifier, navController: NavHostController) {
    val brandGreen = Color(0xFF7CFC00)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("View Detail Page",
                    color = Color.Black) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.Black
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = brandGreen
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {

            Image(
                painter = painterResource(id = R.drawable.img_3),
                contentDescription = "Issue Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .border(2.dp, brandGreen, RoundedCornerShape(12.dp))
            )

            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Category: Road", fontWeight = FontWeight.Bold, fontSize = 18.sp)

            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Description:", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "While walking along Main Street, I noticed a damaged electric pole near the Oak Avenue intersection. The pole appeared tilted and had visible cracks at its base, posing a potential hazard to nearby pedestrians and vehicles. Concerned about the risk of collapse or electrical accidents, I reported the issue to the local repair hub, hoping for a quick response to ensure public safety.",
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            Divider()
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Location: Garment, AA", fontSize = 16.sp)
            Spacer(modifier = Modifier.height(8.dp))

            Divider()
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Issue Date: 3/17/2025", fontSize = 16.sp)
            Spacer(modifier = Modifier.height(8.dp))

            Divider()
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Status: Not Addressed", fontSize = 16.sp)
            Spacer(modifier = Modifier.height(8.dp))

            Divider()

            Spacer(modifier = Modifier.height(16.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.repair),
                    contentDescription = "Repair Progress",
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Repair Progress", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Repair Status
            Text(text = "✅ Current Status: 🟠 In Progress", fontSize = 16.sp)

            Spacer(modifier = Modifier.height(8.dp))

            // Last Updated
            Text(text = "📅 Last Updated: 3/25/2025 - 10:45 AM", fontSize = 16.sp)

            Spacer(modifier = Modifier.height(8.dp))

            // Repair Notes
            Text(text = "🛠️ Repair Notes: The repair team is working on the issue.", fontSize = 16.sp)
        }
    }
}


