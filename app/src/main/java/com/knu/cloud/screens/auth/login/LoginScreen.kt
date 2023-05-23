package com.knu.cloud.screens.auth.login

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.knu.cloud.R
import com.knu.cloud.components.LoginLogo
import com.knu.cloud.components.text_input.ProjectTextInput
import com.knu.cloud.components.text_input.TextInputType
import com.knu.cloud.components.text_input.addFocusCleaner
import timber.log.Timber

@OptIn(ExperimentalLifecycleComposeApi::class)
@ExperimentalComposeUiApi
@Composable
fun LoginScreen(
    onLoginClick: () -> Unit,
    onSignUpClick: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel(),
) {
    Box(
        Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Surface(
            modifier = Modifier
                .width(500.dp)
                .fillMaxHeight(),
            color = Color.White
        ) {
            val keyboardController = LocalSoftwareKeyboardController.current
            val passwordFocusRequester = FocusRequester()
            val context = LocalContext.current

            var loginStateCheck by rememberSaveable { mutableStateOf(false) }
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            uiState.userMessage?.let { message ->
                val toastMsg = stringResource(message)
                LaunchedEffect(message, toastMsg) {
                    Toast.makeText(context, toastMsg, Toast.LENGTH_SHORT).show()
                    Timber.tag("login").d("launchedEffect message : $message")
                }
            }
            LaunchedEffect(uiState.navigateToHome) {
                if (uiState.navigateToHome) {
                    onLoginClick()
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(22.dp)
                    .verticalScroll(rememberScrollState())
                    .addFocusCleaner(keyboardController!!),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.weight(0.2f))
                LoginLogo()
                Spacer(modifier = Modifier.weight(0.1f))
                Column(
                    modifier = Modifier.weight(0.7f),
                    verticalArrangement = Arrangement.Center
                ) {
                    UserForm(
                        keyboardController = keyboardController,
                        passwordFocusRequester = passwordFocusRequester,
                        viewModel = viewModel
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Checkbox(
                            checked = loginStateCheck,
                            onCheckedChange = { loginStateCheck = it }
                        )
                        Text(
                            text = stringResource(R.string.SignIn_loginState),
                            style = MaterialTheme.typography.caption
                        )
                        Row(        // 오류 메시지 띄우기
                            modifier = Modifier
//                                .padding(5.dp)
//                                .height(25.dp)
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.End
                        ) {
                            if (!uiState.navigateToHome && uiState.userMessage != null) {
                                Icon(imageVector = Icons.Default.Warning, contentDescription = "", tint = colorResource(id = R.color.Waring))
                                Text(
                                    text = stringResource(id = uiState.userMessage!!),
                                    style = MaterialTheme.typography.caption,
                                    color = colorResource(id = R.color.Waring)
                                )
                            }
                        }
                    }
                    // 로그인 버튼
                    Button(
                        onClick = {
                                  onLoginClick()
//                            viewModel.login()
                        },
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
                    Box(
                        contentAlignment = Alignment.Center
                    ){
                        Row(
                            modifier = Modifier
                                .padding(bottom = 10.dp)
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center,
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
            }
        }
    }
}

@ExperimentalComposeUiApi
@Composable
fun UserForm(
    modifier: Modifier = Modifier,
    keyboardController: SoftwareKeyboardController?,
    passwordFocusRequester: FocusRequester,
    viewModel: LoginViewModel
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(5.dp, alignment = Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        ProjectTextInput(
            type = TextInputType.Email,
            keyboardController = keyboardController,
            passwordFocusRequester = passwordFocusRequester
        ) { email ->
            viewModel.updateUserEmail(email)
        }

        ProjectTextInput(
            type = TextInputType.PASSWORD,
            keyboardController = keyboardController,
            passwordFocusRequester = passwordFocusRequester
        ) { password ->
            viewModel.updateUserPassword(password)
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
