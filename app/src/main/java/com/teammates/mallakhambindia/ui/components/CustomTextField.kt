package com.teammates.mallakhambindia.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.teammates.mallakhambindia.R
import com.teammates.mallakhambindia.ui.screens.login.DefaultFont
import com.teammates.mallakhambindia.ui.screens.login.LoginScreenViewModel


@Composable
fun CustomTextField(hint: String, icon: Int, string: String, viewModel: LoginScreenViewModel) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(colorResource(id = R.color.white))
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            contentAlignment = Alignment.Center
        ){
            Image(
                painter = painterResource(id = icon),
                contentDescription = null,
                modifier = Modifier.size(24.dp)
                    .padding(3.dp),
            )
        }
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.CenterStart,
        ) {
            if (string.isEmpty()) {
                Text(text = hint, fontSize = 14.sp, color = Color.Gray)
            }
            OutlinedTextField(
                value = string,
                textStyle = TextStyle(
                    fontFamily = DefaultFont,

                    ),
                onValueChange = {
                    viewModel.onEmailChange(it)
                },
                singleLine = true,
                modifier = Modifier.height(42.dp)
                    .fillMaxWidth()
                    .align(Alignment.CenterStart)
            )
        }
    }
}