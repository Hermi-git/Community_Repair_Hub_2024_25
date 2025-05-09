package com.example.community_repair_hub.Components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.community_repair_hub.R
import com.example.community_repair_hub.data.network.model.IssueResponse

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
            // Use AsyncImage for loading images from URL
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(issue.ImagURL ?: "")
                    .crossfade(true)
                    .build(),
                contentDescription = "Issue Image",
                modifier = Modifier
                    .height(150.dp)
                    .width(150.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop,
                error = painterResource(id = R.drawable.img_3) // Fallback image
            )

            Spacer(Modifier.width(16.dp))
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.weight(1f)
            ) {
                Text("Category: ${issue.category ?: "Unknown"}")
                Text("Location: ${issue.locations?.city ?: "Unknown"}, ${issue.locations?.specficArea ?: ""}")
                Text("Issue Date: ${issue.Date ?: "N/A"}")
                Text(
                    text = "View Detail",
                    modifier = Modifier
                        .align(Alignment.End)
                        .clickable {
                            issue._id?.let { id ->
                                navController.navigate("viewdetail/$id")
                            }
                        }
                )
            }
        }
    }
}