package com.example.community_repair_hub.Components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.community_repair_hub.R


@Composable
fun NavDrawer(modifier: Modifier=Modifier,
              navController: NavController,
              drawerState: DrawerState,
              content: @Composable () -> Unit
) {
    ModalNavigationDrawer(
        drawerState= drawerState,
        gesturesEnabled = drawerState.isOpen,
        drawerContent = {
            ModalDrawerSheet(
                modifier=Modifier.width(320.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(vertical = 60.dp, horizontal = 16.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.img_5),
                        contentDescription = "profile image",
                        modifier = modifier
                            .size(80.dp)
                            .clip(CircleShape)
                    )
                    Spacer(Modifier.height(10.dp))
                    Text(
                        text = "Abebe",
                        style = TextStyle(
                            fontSize = 25.sp,
                            fontWeight = FontWeight.SemiBold,
                            fontFamily = FontFamily.Monospace
                        )
                    )
                    Spacer(Modifier.height(10.dp))
                    NavigationDrawerItem(
                        label = { Text("Home") },
                        selected = false,
                        icon = { Icon(Icons.Outlined.Home, contentDescription = null) },
                        onClick = { }
                    )
                    NavigationDrawerItem(
                        label = { Text("Report Issue") },
                        selected = false,
                        icon = { Icon(Icons.Outlined.Warning, contentDescription = null) },
                        onClick = {navController.navigate("report") }
                    )
                    NavigationDrawerItem(label = { Text("My Report") },
                    selected = false,
                    icon = { Image(painter= painterResource(id=R.drawable.img_4),
                        modifier=Modifier
                            .height(30.dp)
                            .width(30.dp)
                        ,contentDescription = null) },
                    onClick = { }
                    )

                    Spacer(Modifier.height(50.dp))
                    Button(
                        onClick = {
                            navController.navigate("auth")
                        },
                        modifier = Modifier
                            .padding(10.dp)
                            .fillMaxWidth(), // Make the button fill the w
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(text = "Logout", color = Color.White) // Change text as needed
                    }
                        Spacer(Modifier.height(10.dp))

                }
            }
        },
        content = content
    )
}


