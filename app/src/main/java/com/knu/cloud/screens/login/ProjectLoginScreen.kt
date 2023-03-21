package com.knu.cloud.screens.login

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.knu.cloud.R
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
        TextInput(
            InputType.Name,
            keyboardActions = KeyboardActions(
                onNext = {
                    passwordFocusRequester.requestFocus()
                    keyboardController.hide()
                }
            ),
        )
        TextInput(
            InputType.Password,
            keyboardActions = KeyboardActions(
                onDone = {
                    keyboardController.hide()
                }
            ),
            focusRequester = passwordFocusRequester,
        )
        Button(
            onClick = {
                navController.navigate(ProjectScreens.HomeScreen.name)
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


@ExperimentalComposeUiApi
@Composable
fun TextInput(
    inputType: InputType,
    focusRequester: FocusRequester? = null,
    keyboardActions: KeyboardActions,
    onAction: KeyboardActions = KeyboardActions.Default
) {
    var value by remember {
        mutableStateOf("")
    }
    OutlinedTextField(
        value = value,
        onValueChange = {value = it},
        modifier = Modifier
            .fillMaxWidth()
            .focusRequester(focusRequester ?: FocusRequester()),
        leadingIcon = {
            Icon(
                imageVector = inputType.icon,
                contentDescription = null
            )
        },
        label = {Text(text= inputType.label, color = Color.Black) },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color.Black,
            cursorColor = colorResource(id = R.color.Orange)
        ),
        shape = RoundedCornerShape(15.dp),
        keyboardOptions = inputType.keyboardOptions,
        visualTransformation = inputType.visualTransformation,
        keyboardActions = keyboardActions
    )
}

sealed class InputType(
    val label: String,
    val icon: ImageVector,
    val keyboardOptions: KeyboardOptions,
    val visualTransformation: VisualTransformation
) {
    object Name: InputType(
        label = "아이디",
        icon = Icons.Default.Person,
        KeyboardOptions(
            imeAction = ImeAction.Next
        ),
        visualTransformation = VisualTransformation.None
    )
    object Password: InputType(
        label = "비밀번호",
        icon = Icons.Default.Lock,
        KeyboardOptions(
            imeAction = ImeAction.Done,
            keyboardType = KeyboardType.Password
        ),
        visualTransformation = PasswordVisualTransformation()
    )
}

@ExperimentalComposeUiApi
fun Modifier.addFocusCleaner(keyboardController: SoftwareKeyboardController, doOnClear: () -> Unit = {}): Modifier {
    return this.pointerInput(Unit) {
        detectTapGestures(onTap = {
            doOnClear()
            keyboardController.hide()
        })
    }
}