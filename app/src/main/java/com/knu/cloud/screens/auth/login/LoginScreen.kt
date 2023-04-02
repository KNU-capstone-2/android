package com.knu.cloud.screens.auth.login

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
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
import androidx.hilt.navigation.compose.hiltViewModel
import com.knu.cloud.R
import com.knu.cloud.components.LoginLogo
import com.knu.cloud.components.text_input.ProjectTextInput
import com.knu.cloud.components.text_input.TextInputType
import com.knu.cloud.components.text_input.addFocusCleaner

@ExperimentalComposeUiApi
@Composable
fun LoginScreen(
    onLoginClick: () -> Unit,
    onSignUpClick : () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {
        Login(
            onLoginClick = onLoginClick,
            onSignUpClick = onSignUpClick
        )
    }
}

@ExperimentalComposeUiApi
@Composable
fun Login(
    onLoginClick: () -> Unit,
    onSignUpClick : () -> Unit,
    viewModel: LoginViewModel = hiltViewModel(),
) {
    Column(
        modifier = Modifier.verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(1f))
        LoginLogo()
        Spacer(modifier = Modifier.weight(1f))
        UserForm(
            onLoginClick = onLoginClick,
            onSignUpClick = onSignUpClick,
            viewModel = viewModel
        )
    }
}

@ExperimentalComposeUiApi
@Composable
fun UserForm(
    onLoginClick: () -> Unit,
    onSignUpClick : () -> Unit,
    viewModel: LoginViewModel
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val passwordFocusRequester = FocusRequester()

    var loginStateCheck by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .padding(22.dp)
            .fillMaxWidth()
            .addFocusCleaner(keyboardController!!),
        verticalArrangement = Arrangement.spacedBy(5.dp, alignment = Alignment.Bottom),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        ProjectTextInput(
            type = TextInputType.Email,
            keyboardController = keyboardController,
            passwordFocusRequester = passwordFocusRequester
        ) { email ->
            viewModel.setUserEmail(email)
        }

        ProjectTextInput(
            type = TextInputType.PASSWORD,
            keyboardController = keyboardController,
            passwordFocusRequester = passwordFocusRequester
        ) { password ->
            viewModel.setUserPassword(password)
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(align = Alignment.Start),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = loginStateCheck,
                onCheckedChange = { loginStateCheck = it }
            )
            Text(
                text = stringResource(R.string.SignIn_loginState),
                style = MaterialTheme.typography.caption
            )
        }

        Button(
            onClick = onLoginClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp),
            shape = RoundedCornerShape(15.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.Black_Main))
        ) {
            Text(
                text = stringResource(id = R.string.SignIn_login),
                color = Color.White,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }

        PolicyButton()

        Row(
            modifier = Modifier.padding(bottom = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            TextButton(
                onClick = {}
            ) {
                Text(
                    text = stringResource(id = R.string.SignIn_find_email),
                    color = Color.LightGray,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium
                )
            }
            TextButton(
                onClick = {}
            ) {
                Text(
                    text = stringResource(id = R.string.SignIn_find_password),
                    color = Color.LightGray,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium
                )
            }
            TextButton(
                onClick = onSignUpClick
            ) {
                Text(
                    text = stringResource(id = R.string.SignIn_signUp),
                    color = colorResource(id = R.color.Black_Main),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

@Composable
fun PolicyButton() {
    Row(
        modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth()
            .wrapContentWidth(align = Alignment.End),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.Info,
            contentDescription = "",
            tint = colorResource(id = R.color.skyBlue)
        )
        Text(
            text = stringResource(R.string.SignIn_policy),
            style = MaterialTheme.typography.caption,
            color = colorResource(id = R.color.skyBlue)
        )
    }
}
