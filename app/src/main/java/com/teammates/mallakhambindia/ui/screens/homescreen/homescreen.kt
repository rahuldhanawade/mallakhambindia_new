package com.teammates.mallakhambindia.ui.screens.homescreen


import com.teammates.mallakhambindia.R
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.outlined.ExitToApp
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.teammates.myapplication.ui.theme.primaryTextColor
import com.teammates.myapplication.ui.theme.white
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController, viewModel: HomeScreenViewModel = hiltViewModel()) {

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }

    ModalNavigationDrawer(
        drawerContent = {
            ModalDrawerSheet {
                CompetitionBanner(viewModel)
                NavigationDrawerItem(
                    label = { Text("About Us") },
                    icon =  {
                        Icon(
                            imageVector = Icons.Outlined.Info,
                            contentDescription = "about us",
                            modifier = Modifier.padding(5.dp)
                        )
                    },
                    selected = false,
                    onClick = { /* Handle click */ }
                )
                NavigationDrawerItem(
                    label = { Text("Log Out") },
                    icon =  {
                        Icon(
                            imageVector = Icons.Outlined.ExitToApp,
                            contentDescription = "about us",
                            modifier = Modifier.padding(5.dp)
                        )
                    },
                    selected = false,
                    onClick = {
                            viewModel.logout()
                    }
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
                        containerColor = Color.Black, // Set the background color
                        titleContentColor = Color.White, // Set the title color
                        actionIconContentColor = Color.White // Set action icon color
                    )
                )
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = { showBottomSheet = true },
                    containerColor = primaryTextColor,
                    contentColor = white,
                    shape = CircleShape
                ) {
                    Icon(
                        Icons.Outlined.Menu,
                        "Floating action button."
                    )
                }
            }
        ){padding ->
            Box(
                modifier = Modifier.padding(padding)
                    .background(Color.Black),
                contentAlignment = Alignment.TopStart,
            ){
                Column{
                    HomeRadioGroup()
                    ListOfTeams()
                }
            }
            if (showBottomSheet) {
                ModalBottomSheet(
                    onDismissRequest = {
                        showBottomSheet = false
                    },
                    sheetState = sheetState
                ) {
                    Button(onClick = {
                        scope.launch { sheetState.hide() }.invokeOnCompletion {
                            if (!sheetState.isVisible) {
                                showBottomSheet = false
                            }
                        }
                    }) {
                        Text("Hide bottom sheet")
                    }
                }
            }
        }
    }
}

@Composable
fun CompetitionBanner(viewModel: HomeScreenViewModel) {
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
            text = viewModel.judgeData.judge?.name.toString(),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.padding(top = 20.dp)
        )
    }
}

@Composable
fun CustomRadioGroup() {

    val options = listOf(
        "In Complete",
        "Completed",
    )

    var selectedOption by remember { mutableStateOf("") }
    val onSelectionChange = { text: String ->
        selectedOption = text
    }

    Row(
        modifier = Modifier.fillMaxSize(),
    ) {
        options.forEach { text ->
            Column(
                modifier = Modifier
                    .padding(
                        all = 8.dp,
                    ),
            ) {
                Text(
                    text = text,
                    style = typography.bodySmall,
                    color = Color.Black,
                    modifier = Modifier
                        .clip(
                            shape = RoundedCornerShape(
                                size = 12.dp,
                            ),
                        )
                        .clickable {
                            onSelectionChange(text)
                        }
                        .background(
                            if (text == selectedOption) {
                                primaryTextColor
                            } else {
                                white
                            }
                        )
                        .padding(
                            vertical = 12.dp,
                            horizontal = 16.dp,
                        ),
                )
            }
        }
    }
}

@Composable
fun HomeRadioGroup() {

    val incomp = "In Complete"
    val comp = "Completed"

    var selectedOption by remember { mutableStateOf("") }
    val onSelectionChange = { text: String ->
        selectedOption = text
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Box(
            modifier = Modifier.weight(1f)
                .padding(5.dp)
        ){
            Text(
                text = incomp,
                style = typography.bodySmall,
                color = Color.Black,
                modifier = Modifier
                    .clip(
                        shape = RoundedCornerShape(
                            size = 12.dp,
                        ),
                    )
                    .clickable {
                        onSelectionChange(incomp)
                    }
                    .background(
                        if (incomp == selectedOption) {
                            primaryTextColor
                        } else {
                            white
                        }
                    )
                    .padding(16.dp)
                    .fillMaxWidth(),
                textAlign = TextAlign.Center,
            )
        }
        Box(
            modifier = Modifier.weight(1f)
                .padding(5.dp)
        ){
            Text(
                text = comp,
                style = typography.bodySmall,
                color = Color.Black,
                modifier = Modifier
                    .clip(
                        shape = RoundedCornerShape(
                            size = 12.dp,
                        ),
                    )
                    .clickable {
                        onSelectionChange(comp)
                    }
                    .background(
                        if (comp == selectedOption) {
                            primaryTextColor
                        } else {
                            white
                        }
                    )
                    .padding(16.dp)
                    .fillMaxWidth(),
                textAlign = TextAlign.Center,
            )
        }
    }
}

@Composable
fun ListOfTeams() {
    Box(
        modifier = Modifier.fillMaxSize()
            .background(Color.White)
            .padding(top = 5.dp)
            .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)),
    ){
        Text(
            text = "HomeScreen",
            style = typography.bodySmall,
            color = Color.Black,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            textAlign = TextAlign.Center,
        )
    }
}
