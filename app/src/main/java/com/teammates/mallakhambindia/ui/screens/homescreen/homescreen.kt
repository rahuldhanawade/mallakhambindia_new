package com.teammates.mallakhambindia.ui.screens.homescreen


import com.teammates.mallakhambindia.R
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.foundation.Image
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.teammates.myapplication.ui.theme.primaryTextColor
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController) {

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerContent = {
            ModalDrawerSheet {
                CompetitionBanner()
                NavigationDrawerItem(
                    label = { Text("Item 1") },
                    selected = false,
                    onClick = { /* Handle click */ }
                )
                NavigationDrawerItem(
                    label = { Text("Item 2") },
                    selected = false,
                    onClick = { /* Handle click */ }
                )
            }
        },
        drawerState = drawerState
    ){
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Welcome") },
                    navigationIcon = {
                        IconButton(onClick = {
                            scope.launch {
                                if (drawerState.isClosed) {
                                    drawerState.open()
                                } else {
                                    drawerState.close()
                                }
                            }
                        }) {
                            Icon(
                                Icons.Default.Menu,
                                contentDescription = "Menu",
                                tint = Color.White
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = primaryTextColor, // Set the background color
                        titleContentColor = Color.White, // Set the title color
                        actionIconContentColor = Color.White // Set action icon color
                    )
                )
            }
        ){padding ->
            Box(
                modifier = Modifier.padding(padding)
            ){

            }
        }
    }
}

@Composable
fun CompetitionBanner() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .background(Color.Black)
            .padding(20.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.mkcp_2021),
                contentDescription = "Competition Logo",
                modifier = Modifier
                    .size(90.dp)
                    .background(Color.Black),
                contentScale = ContentScale.Inside
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Bhausaheb Ranade Navodit Mallakhamb Competition",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.weight(1f)
            )
        }
        Text(
            text = "",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.padding(top = 20.dp)
        )
    }
}
