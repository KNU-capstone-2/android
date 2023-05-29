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
import com.knu.cloud.R
import com.knu.cloud.components.CustomOutlinedButton
import com.knu.cloud.components.data_grid.DataGridBar
import com.knu.cloud.components.data_grid.DataGridElementList
import com.knu.cloud.components.data_grid.DataGridHeader
import com.knu.cloud.components.text_input.ProjectTextInput
import com.knu.cloud.components.text_input.TextInputType
import com.knu.cloud.components.text_input.addFocusCleaner
import com.knu.cloud.model.instanceCreate.KeypairData
import com.knu.cloud.model.keypair.KeypairCreateRequest
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
    val showCreateKeypairDialog by viewModel.showCreateKeypiarDialog   // AlertDialog 띄우기 위한 State 정의

    var uploadExpanded by remember { mutableStateOf(true) }
    var possibleExpanded by remember { mutableStateOf(true) }

    val uiState by viewModel.keypairUiState.collectAsState()

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
                onCreateClocked = {
                    viewModel.showCreateKeypairDialog()
                }
            )
            if (showCreateKeypairDialog) {
                CreateKeyPairDialog(
                    onCreateClicked = {
                        viewModel.createKeypair(it)
                    },
                    onCloseClicked = {
                        viewModel.closeCreateKeypairDialog()
                    }
                )
            }
        }
        // 할당됨
        item {
            DataGridBar(
                type = "할당됨",
                numbers = if (uiState.uploadKeypair== null) 0 else 1,
                expanded = uploadExpanded
            ) {
                uploadExpanded = it
            }
        }
        if (uploadExpanded) {
            if(uiState.uploadKeypair != null){
                itemsIndexed(listOf(uiState.uploadKeypair!!)) { index, item ->
                    if (index == 0) DataGridHeader(screenType = "Keypair")
                    DataGridElementList<KeypairData>(
                        item = item,
                        index = index,
                        type = "할당됨",
                        screenType = "Keypair"
                    ) { it, idx ->
                        viewModel.deleteKeypair(it)
                    }
                }

            }
        }

        // 사용 가능
        item {
            DataGridBar(
                type = "사용 가능",
                numbers = uiState.possibleKeypairs.size,
                expanded = possibleExpanded,
            ) {
                possibleExpanded = it
            }
        }
        if (possibleExpanded) {
            itemsIndexed(uiState.possibleKeypairs) { index, item ->
                if (index == 0) DataGridHeader(screenType = "Keypair")
                DataGridElementList<KeypairData>(
                    item = item,
                    index = index,
                    type = "사용 가능",
                    screenType = "Keypair"
                ) { it, idx ->
                    if(uiState.uploadKeypair != null) viewModel.updateKeypair(it,idx)
                    else viewModel.uploadKeypair(it,idx)
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
    onCreateClocked : () -> Unit
) {
    CustomOutlinedButton(
        onBtnClicked = { onCreateClocked() },
        title = "Create Key Pair",
        icons = R.drawable.ic_baseline_add_24
    )
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CreateKeyPairDialog(
    onCreateClicked:(KeypairCreateRequest) -> Unit,
    onCloseClicked : () -> Unit,
) {
    val txtFieldError = remember { mutableStateOf("") }
    val keyNameTxtField = remember { mutableStateOf("") }
    val keyTypeTxtField = remember { mutableStateOf("") }

    Dialog(
        onDismissRequest = { },
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        val keyboardController = LocalSoftwareKeyboardController.current

        Surface(
            modifier = Modifier
                .width(700.dp)
                .verticalScroll(rememberScrollState()),
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
                                    onCloseClicked()
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
                        OutlinedButton(
                            onClick = {
                                if (keyNameTxtField.value.isEmpty() || keyTypeTxtField.value.isEmpty()) {
                                    txtFieldError.value = "Field can not be empty"
                                    Timber.tag("KeyPairScreen").e("Field can not be empty")
                                    return@OutlinedButton
                                }
                                onCreateClicked(KeypairCreateRequest(name = keyNameTxtField.value,type = keyTypeTxtField.value))
                            },
                            shape = RoundedCornerShape(percent = 15),
                            colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.Outlined_button_color)),
                            modifier = Modifier
                                .width(150.dp)
                                .height(40.dp)
                        ) {
                            Text(
                                text = "Create Keypair",
                                color = colorResource(id = R.color.Outline_button_text_color),
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier.padding(vertical = 5.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}