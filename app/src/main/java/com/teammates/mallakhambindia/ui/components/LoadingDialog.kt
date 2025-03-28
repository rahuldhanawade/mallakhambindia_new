package com.teammates.mallakhambindia.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.*
import androidx.compose.ui.graphics.Color as ComposeColor
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.teammates.mallakhambindia.R

@Composable
fun LoadingDialog(showDialog: Boolean) {
    if (showDialog) {
        Dialog(
            onDismissRequest = {},
            properties = DialogProperties(
                dismissOnBackPress = false,
                dismissOnClickOutside = true
            )
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp)
            ) {
                Card(
                    modifier = Modifier
                        .width(200.dp)
                        .height(180.dp)
                        .padding(10.dp)
                        .background(ComposeColor(0xFF303F9F)),
                    shape = RoundedCornerShape(10.dp),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 8.dp
                    )
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(5.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.logo), // Replace with your drawable
                            contentDescription = null,
                            modifier = Modifier
                                .size(100.dp)
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        CircularProgressIndicator(
                            modifier = Modifier
                                .size(50.dp)
                                .padding(10.dp),
                            color = androidx.compose.ui.graphics.Color.Black // Equivalent to @color/black
                        )
                    }
                }
            }
        }
    }
}