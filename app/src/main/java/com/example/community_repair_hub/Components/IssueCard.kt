package com.example.community_repair_hub.Components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.community_repair_hub.data.network.model.IssueResponse
import com.example.community_repair_hub.R

@Composable
fun IssueCard(issue: IssueResponse, navController: NavController) {
    Card(
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
            // Example if `issue.imageUrl` exists
            Image(
                painter = painterResource(id = R.drawable.img_3), // replace with AsyncImage if URL
                contentDescription = null,
                modifier = Modifier
                    .height(150.dp)
                    .clip(RoundedCornerShape(12.dp))
            )

            Spacer(Modifier.width(16.dp))
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.weight(1f)
            ) {
                Text("Category: ${issue.category ?: "Unknown"}")
                Text("Location: ${issue.locations?.city}, ${issue.locations?.specficArea}")
                Text("Issue Date: ${issue.Date ?: "N/A"}")
                Text(
                    text = "View Detail",
                    modifier = Modifier
                        .align(Alignment.End)
                        .clickable { navController.navigate("viewdetail/${issue._id}") }
                )
            }
        }
    }
}
