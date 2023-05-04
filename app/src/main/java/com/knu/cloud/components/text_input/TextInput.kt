package com.knu.cloud.components.text_input

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.knu.cloud.R
import timber.log.Timber

@ExperimentalComposeUiApi
@Composable
fun ProjectTextInput(
    modifier: Modifier = Modifier,
    type: TextInputType,
    text: String = "",
    hint: String = "",
    label: String = "",
    trailingImage: Painter? = null,
    maxLines: Int = 1,
    singleLine: Boolean = false,
    keyboardController: SoftwareKeyboardController? = null,
    passwordFocusRequester: FocusRequester = FocusRequester(),
    isError: State<Boolean> = remember { mutableStateOf(false) },
    errorMsg: State<String> = remember { mutableStateOf("") },
    onValueChangeListener: (String) -> Unit = {},
) {

    when (type) {
        TextInputType.Email -> {
            TextInput(
                inputType = InputType.Email,
                modifier = modifier,
                leadingIcon = leadingIconType(InputType.Email),
                keyboardActions = KeyboardActions(
                    onNext = {
                        passwordFocusRequester.requestFocus()
                    }
                ),
                isError = isError,
                errorMsg = errorMsg,
                onValueChangeListener = onValueChangeListener
            )
        }
        TextInputType.PASSWORD -> {
            TextInput(
                inputType = InputType.Password,
                modifier = modifier,
                leadingIcon = leadingIconType(InputType.Password),
                keyboardActions = KeyboardActions(
                    onDone = {
                        keyboardController?.hide()
                    }
                ),
                focusRequester = passwordFocusRequester,
                isError = isError,
                errorMsg = errorMsg,
                onValueChangeListener = onValueChangeListener
            )
        }
        TextInputType.FIELD -> {
            TextInput(
                inputType = InputType.FIELD,
                label = label,
                keyboardActions = KeyboardActions(
                    onDone = {
                        keyboardController?.hide()
                    }
                ),
                isError = isError,
                errorMsg = errorMsg,
                onValueChangeListener = onValueChangeListener
            )
        }
    }
}

@ExperimentalComposeUiApi
@Composable
fun TextInput(
    inputType: InputType,
    modifier: Modifier = Modifier,
    leadingIcon: @Composable (() -> Unit)? = null,
    label: String = "",
    focusRequester: FocusRequester? = null,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    isError: State<Boolean> = remember { mutableStateOf(false) },
    errorMsg: State<String> = remember { mutableStateOf("") },
    onValueChangeListener: (value: String) -> Unit = {},
) {
    var textValue = rememberSaveable { mutableStateOf("") }

    OutlinedTextField(
        value = textValue.value,
        onValueChange = {
            textValue.value = it
            onValueChangeListener(it)
        },
        modifier = modifier
            .fillMaxWidth()
            .focusRequester(focusRequester ?: FocusRequester()),
        leadingIcon = leadingIcon,
        label = {
            if (inputType != InputType.FIELD) {
                Text(text = inputType.label, color = Color.Black)
            }else {
                Text(text = label, color = Color.Black)
            }
        },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color.Black,
            cursorColor = colorResource(id = R.color.Orange)
        ),
        shape = RoundedCornerShape(15.dp),
        keyboardOptions = inputType.keyboardOptions,
        visualTransformation = inputType.visualTransformation,
        keyboardActions = keyboardActions,
        isError = isError.value
    )
}

sealed class InputType(
    val label: String = "",
    val icon: ImageVector? = null,
    val keyboardOptions: KeyboardOptions,
    val visualTransformation: VisualTransformation
) {
    object Email: InputType(
        label = "이메일",
        icon = Icons.Default.Person,
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Next
        ),
        visualTransformation = VisualTransformation.None
    )
    object Password: InputType(
        label = "비밀번호",
        icon = Icons.Default.Lock,
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done,
            keyboardType = KeyboardType.Password
        ),
        visualTransformation = PasswordVisualTransformation()
    )
    object FIELD: InputType(
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done
        ),
        visualTransformation = VisualTransformation.None
    )
}

@ExperimentalComposeUiApi
fun Modifier.addFocusCleaner(keyboardController: SoftwareKeyboardController, doOnClear: () -> Unit = {}): Modifier {
    return this.pointerInput(Unit) {
        detectTapGestures(onTap = {
            Timber.tag("addFocus").e("Touch")
            doOnClear()
            keyboardController.hide()
        })
    }
}

fun leadingIconType(inputType: InputType) =
    @Composable {
        if (inputType != InputType.FIELD) {
            Icon(
                imageVector = inputType.icon!!,
                contentDescription = null
            )
        }
    }