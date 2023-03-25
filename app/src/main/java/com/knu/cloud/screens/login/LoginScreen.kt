package com.knu.cloud.screens.login

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.knu.cloud.R
import com.knu.cloud.components.text_input.ProjectTextInput
import com.knu.cloud.components.text_input.TextInputType
import com.knu.cloud.components.text_input.addFocusCleaner
import com.knu.cloud.navigation.ProjectScreens

@ExperimentalComposeUiApi
@Composable
fun LoginScreen(navController: NavController) {
    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = Color.White
    ) {
        Login(navController = navController)
    }
}

@ExperimentalComposeUiApi
@Composable
fun Login(navController: NavController) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val passwordFocusRequester = FocusRequester()

    Column(
        modifier = Modifier
            .padding(24.dp)
            .fillMaxWidth()
            .addFocusCleaner(keyboardController!!),
        verticalArrangement = Arrangement.spacedBy(5.dp, alignment = Alignment.Bottom),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ProjectTextInput(
            type = TextInputType.ID,
            keyboardController = keyboardController,
            passwordFocusRequester = passwordFocusRequester
        )
        ProjectTextInput(
            type = TextInputType.PASSWORD,
            keyboardController = keyboardController,
            passwordFocusRequester = passwordFocusRequester
        )
        Button(
            onClick = {
                navController.navigate(ProjectScreens.InstanceCreateScreen.name)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp),
            shape = RoundedCornerShape(15.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.Orange))
            ) {
            Text(
                text = stringResource(id = R.string.login),
                color = Color.White,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }
        Row(
            modifier = Modifier.padding(top = 10.dp, bottom = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            TextButton(
                onClick = {}
            ) {
                Text(
                    text = stringResource(id = R.string.find_id),
                    color = Color.LightGray,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium
                )
            }
            TextButton(
                onClick = {}
            ) {
                Text(
                    text = stringResource(id = R.string.find_password),
                    color = Color.LightGray,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium
                )
            }
            TextButton(
                onClick = {}
            ) {
                Text(
                    text = stringResource(id = R.string.signUp),
                    color = colorResource(id = R.color.Orange),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}