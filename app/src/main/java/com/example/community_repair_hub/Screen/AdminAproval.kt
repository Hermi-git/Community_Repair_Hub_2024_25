@file:OptIn(ExperimentalMaterial3Api::class)
package com.example.community_repair_hub.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.community_repair_hub.R

@Composable
fun AdminApprovalScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        CenterAlignedTopAppBar(
            title = {
                Text("Community Repair Hub", color = Color.Black)
            },
            navigationIcon = {
                IconButton(onClick = {}) {
                    Icon(Icons.Default.Menu, contentDescription = "Menu", tint = Color.Black)
                }
            },
            actions = {
                Image(
                    painter = painterResource(id = R.drawable.img_5),
                    contentDescription = "Profile",
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                )
            },
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = Color(0xFF00B74A)
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = "",
            onValueChange = {},
            placeholder = { Text("Search here . .") },
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
                .background(Color(0xFFD9FDD3), shape = RoundedCornerShape(10.dp)),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor = Color(0xFFD9FDD3),
                focusedContainerColor = Color(0xFFD9FDD3),
                unfocusedBorderColor = Color.Transparent,
                focusedBorderColor = Color.Transparent
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        val users = listOf(
            Pair("John Doe", "Repair team"),
            Pair("John Doe", "Repair team"),
            Pair("John Doe", "Repair team")
        )

        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

        }
    }
}


@Composable
fun ApprovalUserCard(name: String, role: String) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5)),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.img_5),
                contentDescription = null,
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(text = "Name: $name", fontWeight = FontWeight.Bold)
                Text(text = "Role: $role")
            }

            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Button(
                    onClick = { /* TODO: Handle accept */ },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00C851))
                ) {
                    Text("Accepted", color = Color.White)
                }

                Button(
                    onClick = { /* TODO: Handle decline */ },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFDE3C3C))
                ) {
                    Text("Decline", color = Color.White)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AdminApprovalPreview() {
    AdminApprovalScreen(modifier = Modifier)
}
