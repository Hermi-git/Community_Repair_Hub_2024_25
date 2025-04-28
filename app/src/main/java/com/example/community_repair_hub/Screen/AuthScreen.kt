package com.example.community_repair_hub.Screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.semantics.SemanticsProperties.Text
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.community_repair_hub.R

@Composable
fun AuthScreen(modifier: Modifier=Modifier,navController: NavController){
    var scrollState = rememberScrollState()

    Column(
        modifier=Modifier.fillMaxWidth()
            .padding(16.dp)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Image(

            painter = painterResource(id = R.drawable.community1),
            contentDescription = "Image",
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .height(300.dp),
            contentScale = ContentScale.Fit
        )

        Spacer(modifier = Modifier.height(0.dp))
        Text(
            text="Community Repair Hub",
            style = TextStyle(
                fontSize = 35.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                fontFamily = FontFamily.Monospace

            )
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text="Empowering Communities, One Fix at a Time!",
            style = TextStyle(
                fontSize = 20.sp,
                fontWeight = FontWeight.Light,
                textAlign = TextAlign.Center,
                fontFamily = FontFamily.Cursive
            )
        )
        Spacer(modifier = Modifier.height(20.dp))
        Button(
            onClick = {
                navController.navigate("login")
            },
            modifier = Modifier.fillMaxWidth()
                .padding(10.dp)
                .height(80.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF7CFC00),
                contentColor = Color.Black
            )
        ) {
            Text(
                text="Login",
                style = TextStyle(
                    fontSize = 30.sp,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center,
                    fontFamily = FontFamily.SansSerif
                )
            )
        }
        Spacer(modifier = Modifier.height(15.dp))
        Button(
            onClick = {
                navController.navigate("signup")
            },
            modifier = Modifier.fillMaxWidth()
                .padding(10.dp)
                .height(80.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF7CFC00),
                contentColor = Color.Black
            )
        ) {
            Text(
                text="Register",
                style = TextStyle(
                    fontSize = 30.sp,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center,
                    fontFamily = FontFamily.SansSerif
                )
            )
        }

    }
}