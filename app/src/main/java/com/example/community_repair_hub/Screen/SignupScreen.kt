package com.example.community_repair_hub.Screen


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun SignupScreen(modifier: Modifier = Modifier, navController: NavHostController) {
    var scrollState = rememberScrollState()
    var email by remember{
        mutableStateOf("")
    }
    var name by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    var region by remember {
        mutableStateOf("")
    }
    var city by remember {
        mutableStateOf("")
    }
    var selectedRole by remember { mutableStateOf("Citizen") }
    var expandedRegion by remember { mutableStateOf(false) }
    var expandedCity by remember { mutableStateOf(false) }
    var selectedRegion by remember { mutableStateOf("") }
    var selectedCity by remember { mutableStateOf("") }
    val regions = listOf("Region A", "Region B", "Region C", "Region D")
    val cities = listOf("city A", "city B", "city C", "city D")
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(40.dp)
            .verticalScroll(scrollState)
    ) {
        Text(
            text = "Welcome  to Community Repair Hub",
            style = TextStyle(
                fontSize = 20.sp,
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center
            ),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "Empowering Communities, One Fix at a Time!",
            style = TextStyle(
                fontSize = 18.sp,
                fontWeight = FontWeight.Light,
                textAlign = TextAlign.Center,
                fontFamily = FontFamily.Cursive
            )
        )
        Spacer(modifier = Modifier.height(20.dp))
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = {
                Text(text = "Full Name")
            },
            modifier = Modifier
                .fillMaxWidth(),

            )
        Spacer(modifier = Modifier.height(20.dp))
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = {
                Text(text = "Email Address")
            },
            modifier = Modifier
                .fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(20.dp))
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = {
                Text(text = "Password")
            },
            modifier = Modifier
                .fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "Select Your Role",
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Start,
                fontFamily = FontFamily.Monospace
            ),
            modifier = Modifier
                .padding(bottom = 8.dp)
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
        ) {
            RadioButton(
                selected = selectedRole == "Citizen",
                onClick = { selectedRole = "Citizen" },
                colors = RadioButtonDefaults.colors()
            )
            Text(
                text = "Citizen",
                modifier = Modifier
                    .padding(end = 16.dp)
                    .align(androidx.compose.ui.Alignment.CenterVertically)
            )
            RadioButton(
                selected = selectedRole == "Repairteam",
                onClick = { selectedRole = "Repairteam" },
                colors = RadioButtonDefaults.colors()
            )
            Text(
                text = "Repairing Team",
                modifier = Modifier
                    .align(androidx.compose.ui.Alignment.CenterVertically)
            )

        }
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "Address",
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Start,
                fontFamily = FontFamily.Monospace
            ),
            modifier = Modifier
                .padding(bottom = 8.dp)
        )
        Text(
            text = "Region",
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Start,
                fontFamily = FontFamily.Monospace
            ),
            modifier = Modifier
                .padding(8.dp)

        )
        Box {
            TextField(
                value = selectedRegion,
                onValueChange = {},
                readOnly = true,
                label = { Text("Select Region") },
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Filled.ArrowDropDown,
                        contentDescription = "Dropdown Icon",
                        modifier = Modifier.clickable { expandedRegion = true }
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { expandedRegion = true }
            )

            DropdownMenu(
                expanded = expandedRegion,
                onDismissRequest = { expandedRegion = false }
            ) {
                regions.forEach { region ->
                    DropdownMenuItem(
                        text = { Text(text = region) },
                        onClick = {
                            selectedRegion = region
                            expandedRegion = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "City",
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Start,
                fontFamily = FontFamily.Monospace
            ),
            modifier = Modifier
                .padding(8.dp)

        )
        Box {
            TextField(
                value = selectedCity,
                onValueChange = {},
                readOnly = true,
                label = { Text("Select City") },
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Filled.ArrowDropDown,
                        contentDescription = "Dropdown Icon",
                        modifier = Modifier.clickable { expandedCity = true }
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { expandedCity = true }
            )

            DropdownMenu(
                expanded = expandedCity,
                onDismissRequest = { expandedCity = false }
            ) {
                cities.forEach { city ->
                    DropdownMenuItem(
                        text = { Text(text = city) },
                        onClick = {
                            selectedCity = city
                            expandedCity = false
                        }
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(15.dp))
        Button(
            onClick = {},
            modifier = Modifier.fillMaxWidth()
                .padding(10.dp)
                .height(60.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF7CFC00),
                contentColor = Color.Black
            )
        ) {
            Text(
                text = "Create Account",
                style = TextStyle(
                    fontSize = 22.sp,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center,
                    fontFamily = FontFamily.SansSerif
                )
            )
        }


    }
}
