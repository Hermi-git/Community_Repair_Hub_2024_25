package com.example.community_repair_hub.Screen

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.test.isFocused
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable


fun LoginScreen(modifier: Modifier = Modifier, navController: NavHostController){
    var email by remember {
        mutableStateOf("")
    }
    var password by remember{
        mutableStateOf("")
    }
    Column(
        modifier=Modifier
            .fillMaxWidth()
            .padding(
                start = 32.dp,
                top = 72.dp,
                end = 32.dp,
                bottom = 52.dp
            ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(
            text="Welcome back to the Community Repair Hub",
            style= TextStyle(
                fontSize = 40.sp,
                fontFamily = FontFamily.Cursive,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
            )
        )
        Spacer(Modifier.height(20.dp))
        Text(
            text="Empowering Communities, One Fix at a Time!",
            style= TextStyle(
                fontSize = 25.sp,
                fontFamily = FontFamily.Cursive,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
            )
        )
        Spacer(Modifier.height(20.dp))
        Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                ) {
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                placeholder = { Text("Email") },
                label = { Text("Email") },
                modifier = Modifier
                    .fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color(0xFF7CFC00),
                    unfocusedIndicatorColor = Color.Gray,
                    disabledIndicatorColor = Color.LightGray
                )
            )


     }
        Spacer(Modifier.height(10.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
        ) {
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                placeholder = { Text("Password") },
                label = { Text("Password") },
                modifier = Modifier
                    .fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color(0xFF7CFC00),
                    unfocusedIndicatorColor = Color.Gray,
                    disabledIndicatorColor = Color.LightGray
                )
            )
        }
        Spacer(modifier = Modifier.height(30.dp))
        Button(
            onClick = {
                navController.navigate("home")
            },
            modifier = Modifier.fillMaxWidth()
                .height(60.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF7CFC00),
                contentColor = Color.Black
            )
        ) {
            Text(
                text="Login",
                style = TextStyle(
                    fontSize = 25.sp,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center,
                    fontFamily = FontFamily.SansSerif
                )
            )
        }
        Text(
            text = "Need help with your password?",
            fontSize = 20.sp,
            color = Color.Gray,
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(top = 8.dp,
                    bottom = 8.dp
                )
                .clickable { navController.navigate("signup") },
            textAlign = TextAlign.Center
        )
        Text(
            text = "Don't have an account yet?",
            fontSize = 20.sp,
            color = Color.Gray,
            fontWeight = FontWeight.Bold,
        )
        Text(
            text = "Join us now!",
            fontSize = 20.sp,
            color = Color(0xFF00C853),
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(top = 8.dp)
                .clickable { navController.navigate("signup") },
            textAlign = TextAlign.Center
        )


    }

}