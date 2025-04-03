package com.teammates.mallakhambindia.ui.screens.login

import android.os.Build
import com.teammates.mallakhambindia.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.teammates.mallakhambindia.data.Resource
import com.teammates.mallakhambindia.ui.components.LoadingDialog
import java.util.Calendar


val DefaultFont = FontFamily(Font(R.font.calibri_regular))

val currentYear: Int = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
    java.time.Year.now().value
} else {
    Calendar.getInstance().get(Calendar.YEAR)
}


@Preview
@Composable
fun LoginScreen(navController: NavController? = null, viewModel: LoginScreenViewModel = hiltViewModel()) {

    val navigateToHome by viewModel.navigateToHome.observeAsState(false)

    LaunchedEffect(navigateToHome) {
        if (navigateToHome) {
            navController?.navigate("homescreen")
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.login_bk_color))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
//            LoadingDialog(viewModel.showDialog)
            HeaderSection()
            Spacer(modifier = Modifier.height(20.dp))
            LoginCard(viewModel)
            Spacer(modifier = Modifier.height(15.dp))
            LoginButton(viewModel)
            Spacer(modifier = Modifier.height(30.dp))
            FooterSection()
        }
    }
}

@Composable
fun HeaderSection() {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Bhausaheb Ranade Navodit Mallakhamb Competition $currentYear",
            fontSize = 23.sp,
            fontFamily = DefaultFont,
            color = colorResource(R.color.black),
            modifier = Modifier.weight(1f)
        )
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Logo",
            modifier = Modifier
                .weight(1f)
                .size(200.dp)
                .padding(20.dp)
        )
    }
}

@Composable
fun LoginCard(viewModel: LoginScreenViewModel) {
    val email by viewModel.email.collectAsState()
    val password by viewModel.password.collectAsState()
    val isEmailValid by viewModel.isEmailValid.collectAsState()
    val isPasswordValid by viewModel.isPasswordValid.collectAsState()
    val locationData by viewModel.locationListData.collectAsState()

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 3.dp, vertical = 20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = email,
                onValueChange = { viewModel.onEmailChange(it) },
                label = { Text("Username") },
                isError = !isEmailValid,
                modifier = Modifier.fillMaxWidth()
            )
            if (!isEmailValid) {
                Text(
                    text = "Invalid email address",
                    color = Color.Red,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { viewModel.onPasswordChange(it) },
                label = { Text("Password") },
                visualTransformation = PasswordVisualTransformation(),
                isError = !isPasswordValid,
                modifier = Modifier.fillMaxWidth()
            )
            if (!isPasswordValid) {
                Text(
                    text = "Password must be at least 6 characters and contain both letters and numbers.",
                    color = Color.Red,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            when (locationData) {
                is Resource.Loading -> Text("Loading locations...")
                is Resource.Success -> {
                    viewModel.showDialog = false
                    val locations = (locationData as Resource.Success<List<String>>).data
                    LocationSpinner(locations, viewModel)
                }
                is Resource.Error -> Text("Error: ${(locationData as Resource.Error).message}")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocationSpinner(locations: List<String>, viewModel: LoginScreenViewModel) {
    var expanded by remember { mutableStateOf(false) }
    val selectedLocation by viewModel.selectedLocation.collectAsState()
    val isValid = selectedLocation != "Select Location"

    Box(modifier = Modifier.padding(top = 5.dp)) {
        Column {
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = it }
            ) {
                OutlinedTextField(
                    value = selectedLocation,
                    onValueChange = {},
                    readOnly = true,
                    isError = !isValid,
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(),
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                    }
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    locations.forEach { option ->
                        DropdownMenuItem(
                            text = { Text(option, fontFamily = DefaultFont) },
                            onClick = {
                                viewModel.onLocationSelected(option)
                                expanded = false
                            }
                        )
                    }
                }
            }
            if (!isValid) {
                Text(
                    text = "Please select a valid location",
                    color = Color.Red,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

@Composable
fun LoginButton(viewModel: LoginScreenViewModel) {
    val isLoginEnabled by viewModel.isLoginEnabled.collectAsState()

    Box(
        modifier = Modifier
            .width(182.dp)
            .height(40.dp)
            .background(
                color = if (isLoginEnabled) Color(0xFFFF6600) else Color.Gray,
                shape = RoundedCornerShape(26.dp)
            )
            .padding(10.dp)
            .clickable(enabled = isLoginEnabled) { viewModel.onLogin() },
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = "Login",
            fontSize = 16.sp,
            fontFamily = DefaultFont,
            color = Color.White
        )
    }
}


@Composable
fun FooterSection() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Box(modifier = Modifier.weight(1f).height(1.dp).background(colorResource(id = R.color.colorPrimaryDark)))
        Text(
            text = "\u00A9 Copyright by ",
            fontSize = 12.sp,
            fontFamily = DefaultFont,
            color = colorResource(id = R.color.black)
        )
        Text(
            text = currentYear.toString(),
            fontSize = 12.sp,
            fontFamily = DefaultFont,
            color = colorResource(id = R.color.colorAccent)
        )
        Text(
            text = " Mallakhamb India.",
            fontSize = 12.sp,
            fontFamily = DefaultFont,
            color = colorResource(id = R.color.black)
        )
        Box(modifier = Modifier.weight(1f).height(1.dp).background(colorResource(id = R.color.colorPrimaryDark)))
    }
}