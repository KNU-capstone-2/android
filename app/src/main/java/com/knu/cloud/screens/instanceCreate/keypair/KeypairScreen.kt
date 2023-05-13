package com.knu.cloud.screens.instanceCreate.keypair

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import com.knu.cloud.components.data_grid.DataGridBar
import com.knu.cloud.components.data_grid.DataGridElementList
import com.knu.cloud.components.data_grid.DataGridHeader
import com.knu.cloud.components.text_input.ProjectTextInput
import com.knu.cloud.components.text_input.TextInputType
import com.knu.cloud.components.text_input.addFocusCleaner
import com.knu.cloud.model.instanceCreate.Keypair
import com.knu.cloud.screens.instanceCreate.InstanceCreateViewModel
import timber.log.Timber

@ExperimentalComposeUiApi
@Composable
fun KeypairScreen(
    viewModel: InstanceCreateViewModel = hiltViewModel()
) {
    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = Color.White
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Keypair(viewModel = viewModel)
        }
    }

}

@ExperimentalComposeUiApi
@Composable
fun Keypair(
    viewModel: InstanceCreateViewModel,
) {
    val showCreateKeyPairDialog = remember { mutableStateOf(false) }    // AlertDialog 띄우기 위한 State 정의

    var uploadExpanded by remember { mutableStateOf(false) }
    var possibleExpanded by remember { mutableStateOf(true) }

    val uploadList = viewModel.uploadKeypair.value
    val possibleList = viewModel.possibleKeypair.value

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .height(600.dp) // Prevent vertical infinity maximum height constraints
    ) {
        item {
            Text(
                text = "A key pair allows you to SSH into your newly create instance. You may select an existing key pair, import a key pair or generate a new key pair",
                style = MaterialTheme.typography.subtitle2,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(20.dp)
            )
            CreateKeyPairButton(
                showCreateKeyPairDialog = showCreateKeyPairDialog
            )
            if (showCreateKeyPairDialog.value) {
                Test(
                    value = "",
                    setShowDialog = { it ->
                        showCreateKeyPairDialog.value = it
                    },
                    viewModel = viewModel
                )
            }
        }
        // 할당됨
        item {
            DataGridBar(
                type = "할당됨",
                numbers = viewModel.uploadKeypair.value.size,
                expanded = uploadExpanded
            ) {
                uploadExpanded = it
            }
        }
        if (uploadExpanded) {
            itemsIndexed(uploadList) { index, item ->
                if (index == 0) DataGridHeader(screenType = "Keypair")
                DataGridElementList<Keypair>(
                    item = item,
                    index = index,
                    type = "할당됨",
                    screenType = "Keypair"
                ) { it, idx ->
                    viewModel.deleteKeypair(it, idx)
                }
            }
        }

        // 사용 가능
        item {
            DataGridBar(
                type = "사용 가능",
                numbers = viewModel.possibleFlavor.value.size,
                expanded = possibleExpanded,
            ) {
                possibleExpanded = it
            }
        }
        if (possibleExpanded) {
            itemsIndexed(possibleList) { index, item ->
                if (index == 0) DataGridHeader(screenType = "Keypair")
                DataGridElementList<Keypair>(
                    item = item,
                    index = index,
                    type = "사용 가능",
                    screenType = "Keypair"
                ) { it, idx ->
                    viewModel.uploadKeypair(it, idx)
                }
            }
        }

        // space for footer
        item {
            Spacer(modifier = Modifier.height(100.dp))
        }
    }
}

@Composable
fun CreateKeyPairButton(
    showCreateKeyPairDialog: MutableState<Boolean>
) {
    OutlinedButton(
        onClick = {
            showCreateKeyPairDialog.value = true
        },
        shape = RoundedCornerShape(percent = 30), // 모서리를 둥글게 처리
        modifier = Modifier
            .padding(10.dp)
            .height(38.dp)
            .wrapContentWidth(),
    ) {
        Icon(
            imageVector = Icons.Filled.Add,
            contentDescription = "create icon",
            modifier = Modifier.size(20.dp),
            tint = Color.Black
        )
        Text(
            text = "Create Key Pair",
            fontWeight = FontWeight.Bold,
            fontSize = 15.sp,
            modifier = Modifier.padding(horizontal = 16.dp) // 버튼 내부 여백
        )
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun Test(
    value: String,
    setShowDialog: (Boolean) -> Unit,
    viewModel: InstanceCreateViewModel
) {
    val txtFieldError = remember { mutableStateOf("") }
    val keyNameTxtField = remember { mutableStateOf(value) }
    val keyTypeTxtField = remember { mutableStateOf(value) }

    Dialog(
        onDismissRequest = { setShowDialog(true) },
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        val keyboardController = LocalSoftwareKeyboardController.current

        Surface(
            modifier = Modifier
                .width(700.dp)
                .verticalScroll(rememberScrollState()), /*TODO 여기서부터 스크롤 테스트해보고 시작하면 됨!!!*/
            shape = RoundedCornerShape(16.dp),
            color = Color.White
        ) {
            Box(
                modifier = Modifier.addFocusCleaner(keyboardController!!),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Create Key Pair",
                            style = TextStyle(
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )
                        )
                        Icon(
                            imageVector = Icons.Filled.Close,
                            contentDescription = "",
                            tint = colorResource(android.R.color.darker_gray),
                            modifier = Modifier
                                .width(30.dp)
                                .height(30.dp)
                                .clickable {
                                    setShowDialog(false)
                                }
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Divider(
                        color = Color.LightGray,
                        thickness = 1.dp,
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "Key Pair Name",
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(top = 25.dp, start = 8.dp, end = 15.dp, bottom = 5.dp)
                    )
                    ProjectTextInput(
                        type = TextInputType.FIELD,
                        keyboardController = keyboardController,
                    ) { keyName ->
                        keyNameTxtField.value = keyName
                        Timber.tag("KeypairScreen").d(keyNameTxtField.value)
                    }

                    Text(
                        text = "Key Type",
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(top = 25.dp, start = 8.dp, end = 15.dp, bottom = 5.dp)
                    )
                    ProjectTextInput(
                        type = TextInputType.FIELD,
                        keyboardController = keyboardController,
                    ) { keyType ->
                        keyTypeTxtField.value = keyType
                        Timber.tag("KeypairScreen").d(keyTypeTxtField.value)
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.End
                    ) {
                        Button(
                            onClick = {
                                if (keyNameTxtField.value.isEmpty() || keyTypeTxtField.value.isEmpty()) {
                                    txtFieldError.value = "Field can not be empty"
                                    Timber.tag("KeyPairScreen").e("Field can not be empty")
                                    return@Button
                                }
                                viewModel.setKeyName(keyNameTxtField.value)
                                viewModel.setKeyType(keyTypeTxtField.value)
                                setShowDialog(false)
                            },
                            shape = RoundedCornerShape(percent = 20),
                            modifier = Modifier
                                .width(150.dp)
                                .height(40.dp)
                        ) {
                            Text(text = "Create Keypair")
                        }
                        Spacer(modifier = Modifier.width(20.dp))
                        Button(
                            onClick = {
                                if (keyNameTxtField.value.isEmpty() || keyTypeTxtField.value.isEmpty()) {
                                    txtFieldError.value = "Field can not be empty"
                                    Timber.tag("KeyPairScreen").e("Field can not be empty")
                                    return@Button
                                }
                                viewModel.setKeyName(keyNameTxtField.value)
                                viewModel.setKeyType(keyTypeTxtField.value)
                                setShowDialog(false)
                            },
                            shape = RoundedCornerShape(percent = 20),
                            modifier = Modifier
                                .width(80.dp)
                                .height(40.dp)
                        ) {
                            Text(text = "Done")
                        }
                    }
                }
            }
        }
    }
}