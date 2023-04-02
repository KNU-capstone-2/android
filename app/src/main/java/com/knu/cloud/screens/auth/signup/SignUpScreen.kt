package com.knu.cloud.screens.auth.signup

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.knu.cloud.R
import com.knu.cloud.components.text_input.ProjectTextInput
import com.knu.cloud.components.text_input.TextInputType
import com.knu.cloud.components.text_input.addFocusCleaner
import timber.log.Timber

@ExperimentalComposeUiApi
@Composable
fun SignUpScreen(
    onSignUpSubmitClick : () -> Unit,
    viewModel: SignUpViewModel = hiltViewModel(),
) {
    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = Color.White
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ){
            TopAppBar(
                title = { Text("") },
                navigationIcon = {
                    IconButton(onClick = { /* Handle back button click */ }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                backgroundColor = Color.Transparent,
                elevation = 0.dp
            )
            Text(
                text = stringResource(R.string.SignUp_title),
                style = MaterialTheme.typography.h5,
                modifier = Modifier.padding(start = 15.dp, bottom = 3.dp)
            )
            Text(
                text = stringResource(R.string.SignUp_subTitle),
                style = MaterialTheme.typography.caption,
                color = Color.Gray,
                modifier = Modifier.padding(start = 15.dp, bottom = 10.dp)
            )
            SignUp(
                onSignUpSubmitClick = onSignUpSubmitClick,
                viewModel = viewModel
            )
        }
    }
}

@ExperimentalComposeUiApi
@Composable
fun SignUp(
    onSignUpSubmitClick : () -> Unit,
    viewModel: SignUpViewModel
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val emailErrorCheck = viewModel.userEmailError.collectAsState()
    val emailErrorState = viewModel.userEmailErrorState.collectAsState()
    val passwordErrorCheck = viewModel.userPasswordError.collectAsState()
    val passwordErrorState = viewModel.userPasswordErrorState.collectAsState()

    var personalInfoCheck by rememberSaveable { mutableStateOf(false) }
    var expirationDateCheck by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .background(color = Color.White)
            .padding(20.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .addFocusCleaner(keyboardController!!),
    ) {
        ProjectTextInput(
            type = TextInputType.FIELD,
            label = stringResource(R.string.SignUp_email),
            keyboardController = keyboardController,
        ) { email ->
            viewModel.setUserEmail(email)
        }

        if (emailErrorCheck.value) {
            Row(
                modifier = Modifier
                    .padding(5.dp)
                    .fillMaxWidth()
                    .wrapContentWidth(align = Alignment.End),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(imageVector = Icons.Default.Warning, contentDescription = "", tint = colorResource(id = R.color.Waring))
                Text(
                    text = emailErrorState.value,
                    style = MaterialTheme.typography.caption,
                    color = colorResource(id = R.color.Waring)
                )
            }
        }else {
            Row(
                modifier = Modifier
                    .padding(5.dp)
                    .fillMaxWidth()
                    .wrapContentWidth(align = Alignment.End),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(imageVector = Icons.Default.Info, contentDescription = "", tint = colorResource(id = R.color.skyBlue))
                Text(
                    text = stringResource(R.string.SignUp_emailInfo),
                    style = MaterialTheme.typography.caption,
                    color = colorResource(id = R.color.skyBlue)
                )
            }
        }

        Spacer(modifier = Modifier.height(15.dp))

        ProjectTextInput(
            type = TextInputType.FIELD,
            label = stringResource(R.string.SignUp_password),
            keyboardController = keyboardController,
        ) { password ->
            viewModel.setUserPassword(password)
        }

        Spacer(modifier = Modifier.height(10.dp))

        ProjectTextInput(
            type = TextInputType.FIELD,
            label = stringResource(R.string.SignUp_passwordCheck),
            keyboardController = keyboardController,
        ) { passwordCheck ->
            viewModel.setUserPasswordCheck(passwordCheck)
        }

        if (passwordErrorCheck.value) {
            Row(
                modifier = Modifier
                    .padding(5.dp)
                    .fillMaxWidth()
                    .wrapContentWidth(align = Alignment.End),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(imageVector = Icons.Default.Warning, contentDescription = "", tint = colorResource(id = R.color.Waring))
                Text(
                    text = passwordErrorState.value,
                    style = MaterialTheme.typography.caption,
                    color = colorResource(id = R.color.Waring)
                )
            }
        }else {
            Row(
                modifier = Modifier
                    .padding(5.dp)
                    .fillMaxWidth()
                    .wrapContentWidth(align = Alignment.End),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(imageVector = Icons.Default.Info, contentDescription = "", tint = colorResource(id = R.color.skyBlue))
                Text(
                    text = stringResource(R.string.SignUp_passwordInfo),
                    style = MaterialTheme.typography.caption,
                    color = colorResource(id = R.color.skyBlue)
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = personalInfoCheck,
                onCheckedChange = { personalInfoCheck = it }
            )
            Text(stringResource(R.string.SignUp_personalInfoCheck))
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = expirationDateCheck,
                onCheckedChange = { expirationDateCheck = it }
            )
            Text(stringResource(R.string.SignUp_expirationDateCheck))
        }

        if (!personalInfoCheck || !expirationDateCheck) {
            Row(
                modifier = Modifier
                    .padding(5.dp)
                    .fillMaxWidth()
                    .wrapContentWidth(align = Alignment.End),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(imageVector = Icons.Default.Warning, contentDescription = "", tint = colorResource(id = R.color.Waring))
                Text(
                    text = SignUpErrorState.POLICY_NOT_SATISFIED,
                    style = MaterialTheme.typography.caption,
                    color = colorResource(id = R.color.Waring)
                )
            }
        }

        Button(
            onClick ={
                if (viewModel.passAllConditions() && personalInfoCheck && expirationDateCheck) {
                    Timber.tag("SignUpScreen").d("테스트 성공")
                    onSignUpSubmitClick()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp),
            shape = RoundedCornerShape(15.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.Black_Main))
        ) {
            Text(
                text = stringResource(R.string.SignUp_Button),
                color = Color.White,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }

    }
}