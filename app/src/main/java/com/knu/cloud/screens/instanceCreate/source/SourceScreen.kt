package com.knu.cloud.screens.instanceCreate

import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.*
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.hilt.navigation.compose.hiltViewModel
import com.knu.cloud.R
import com.knu.cloud.components.data_grid.*
import com.knu.cloud.components.text_input.*
import com.knu.cloud.model.instanceCreate.ImageData

@ExperimentalComposeUiApi
@Composable
fun SourceScreen(
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
            Source(viewModel = viewModel)
        }
    }
}

@ExperimentalComposeUiApi
@Composable
fun Source(
    viewModel: InstanceCreateViewModel,
) {
    val uiState by viewModel.sourceUiState.collectAsState()

    var volumnSizeExpanded by remember { mutableStateOf(true)}
    var uploadExpanded by remember { mutableStateOf(true) }
    var possibleExpanded by remember { mutableStateOf(true) }

    val keyboardController = LocalSoftwareKeyboardController.current
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .height(600.dp) // Prevent vertical infinity maximum height constraints
            .addFocusCleaner(keyboardController!!),
    ) {
        item {
            Text(
                text = stringResource(R.string.IC_Source_description),
                style = MaterialTheme.typography.subtitle2,
                modifier = Modifier.padding(20.dp)
            )
            Row(
                modifier = Modifier.padding(10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                BootingSource() {
                    viewModel.updateSourceUiState(uiState.copy(selectedTitle = it))
                }
                VolumeSwitchButton(bootSourceType = uiState.selectedTitle) {
                    volumnSizeExpanded = !it // <- ! 붙여준거 주의
                }
            }
            if (volumnSizeExpanded && (uiState.selectedTitle == "Image" || uiState.selectedTitle == "인스턴스 스냅샷")) {
                Row(
                    modifier = Modifier.padding(10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    VolumeSize(
                        volumnSize = uiState.volumeSize,
                        keyboardController = keyboardController,
                        onSelected = {
                            try {
                                viewModel.updateSourceUiState(uiState.copy(volumeSize = it.toInt()))
                            }catch (e : NumberFormatException){
                                Toast.makeText(context,"정수를 입력하세요", Toast.LENGTH_SHORT).show()
                                viewModel.updateSourceUiState(uiState.copy(volumeSize = 1))
                            }
                            focusManager.clearFocus()
                        }
                    )
                        /*  */
                    VolumeSizeSwitchButton()
                }
            }
        }

        // 할당됨
        item {
            DataGridBar(
                type = "할당됨",
                numbers = if (uiState.uploadSource== null) 0 else 1,
                expanded = uploadExpanded
            ) {
                uploadExpanded = it
            }
        }
        if (uploadExpanded) {
            if(uiState.uploadSource != null){
                itemsIndexed(listOf(uiState.uploadSource!!)) { index, item ->
                    if (index == 0) DataGridHeader(screenType = "Source")
                    DataGridElementList<ImageData>(
                        item = item,
                        index = index,
                        type = "할당됨",
                        screenType = "Source"
                    ) { it, idx ->
                        viewModel.deleteSource(it)
                    }
                }
            }
        }

        // 사용 가능
        item {
            DataGridBar(
                type = "사용 가능",
                numbers = uiState.possibleSources.size,
                expanded = possibleExpanded,
            ) {
                possibleExpanded = it
            }
        }
        if (possibleExpanded) {
            itemsIndexed(uiState.possibleSources) { index, item ->
                if (index == 0) DataGridHeader(screenType = "Source")
                DataGridElementList<ImageData>(
                    item = item,
                    index = index,
                    type = "사용 가능",
                    screenType = "Source"
                ) { it, idx ->
                    if(uiState.uploadSource != null) viewModel.updateSource(it,idx)
                    else viewModel.uploadSource(it,idx)
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
fun RowScope.VolumeSwitchButton(
    bootSourceType: String,
    volumnSizeExpanded: (Boolean) -> Unit
) {
    val title =
        when (bootSourceType) {
            "Image" -> "새로운 볼륨 생성"
            "Volume" -> "인스턴스 삭제시 볼륨 삭제"
            "Volume Snapshot" -> "인스턴스 삭제시 볼륨 삭제"
            else -> "새로운 볼륨 생성"
        }

    Column(
        modifier = Modifier
            .padding(10.dp)
            .weight(.1f)
    ) {
        Text(
            text = title,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(10.dp)
        )
        Row(
            modifier = Modifier
                .padding(start = 5.dp)
                .fillMaxWidth(),
        ) {
            var buttonState by remember { mutableStateOf(false) }
            TextButton(
                modifier = Modifier
                    .border(
                        BorderStroke(1.dp, Color.Gray),
                        shape = RoundedCornerShape(topStart = 15.dp, bottomStart = 15.dp)
                    )
                    .height(55.dp),
                shape = RoundedCornerShape(topStart = 15.dp, bottomStart = 15.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    backgroundColor = if(buttonState) Color.White else Color.Gray,
                ),
                onClick = {
                    buttonState = !buttonState
                    volumnSizeExpanded(buttonState)
                },
                enabled = buttonState
            ) {
                Text(
                    text = "예",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(6.dp)
                )
            }
            TextButton(
                modifier = Modifier
                    .border(
                        BorderStroke(1.dp, Color.Gray),
                        shape = RoundedCornerShape(topEnd = 15.dp, bottomEnd = 15.dp)
                    )
                    .height(55.dp),
                shape = RoundedCornerShape(topEnd = 15.dp, bottomEnd = 15.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    backgroundColor = if(buttonState) Color.Gray else Color.White,
                ),
                onClick = {
                    buttonState = !buttonState
                    volumnSizeExpanded(buttonState)
                },
                enabled = !buttonState,
            ) {
                Text(
                    text = "아니오",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(6.dp)
                )
            }
        }
    }
}

@Composable
fun RowScope.BootingSource(
    onSelected: (String) -> Unit,
) {
    Column(
        modifier = Modifier
            .padding(10.dp)
            .weight(.1f)
    ) {
        Text(
            text = "부팅 소스 선택",
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(10.dp)
        )
        DropdownBootingSource() { it ->
            onSelected(it)
        }
    }
}

@Composable
fun DropdownBootingSource(
    onSelected: (String) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }
    val items = listOf("Image", "Volume", "Volume Snapshot", "인스턴스 스냅샷")
    var selectedIndex by remember { mutableStateOf(0) }
    var fieldSize by remember { mutableStateOf(Size.Zero) }

    Box (
        modifier = Modifier
            .fillMaxSize()
            .onGloballyPositioned { coordinates ->
                // This value is used to assign to
                // the DropDown the same width
                fieldSize = coordinates.size.toSize()
            }
            .clip(shape = RoundedCornerShape(15.dp))
            .border(BorderStroke(1.dp, Color.Gray), shape = RoundedCornerShape(15.dp))
            .graphicsLayer(shape = RoundedCornerShape(20.dp))
            .clickable(onClick = { expanded = true }),
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text (
                text = items[selectedIndex],
            )
            Icon(
                imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                tint = MaterialTheme.colors.secondary,
                contentDescription = "button",
            )
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .width(with(LocalDensity.current){fieldSize.width.toDp()})
        ) {
            items.forEachIndexed { index, s ->
                DropdownMenuItem(onClick = {
                    selectedIndex = index
                    expanded = false
                    onSelected(s)
                }) {
                    Text(
                        text = s,
                    )
                }
            }
        }
    }
}


@ExperimentalComposeUiApi
@Composable
fun RowScope.VolumeSize(
    volumnSize :Int,
    keyboardController: SoftwareKeyboardController? = null,
    onSelected: (String) -> Unit,
) {
    Column(
        modifier = Modifier
            .padding(10.dp)
            .weight(.1f)
    ) {
        Text(
            text = "볼륨 크기 (GB)",
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 5.dp, start = 10.dp, end = 10.dp)
        )
        var volumeSizeString by remember { mutableStateOf(volumnSize.toString()) }
        TextInput(
            text = volumeSizeString,
            inputType = InputType.FIELD,
            keyboardActions = KeyboardActions(
                onDone = {
                    keyboardController?.hide()
                    onSelected(volumeSizeString)
                }
            ),
            onValueChangeListener = { volumeSizeString = it}
        )
    }
}

@Composable
fun RowScope.VolumeSizeSwitchButton(
) {
    Column(
        modifier = Modifier
            .padding(10.dp)
            .weight(.1f)
    ) {
        Text(
            text = "인스턴스 삭제시 볼륨 삭제",
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(10.dp)
        )
        Row(
            modifier = Modifier
                .padding(start = 5.dp)
                .fillMaxWidth(),
        ) {
            var buttonState by remember { mutableStateOf(false) }

            TextButton(
                modifier = Modifier
                    .border(
                        BorderStroke(1.dp, Color.Gray),
                        shape = RoundedCornerShape(topStart = 15.dp, bottomStart = 15.dp)
                    )
                    .height(55.dp),
                shape = RoundedCornerShape(topStart = 15.dp, bottomStart = 15.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    backgroundColor = if(buttonState) Color.White else Color.Gray,
                ),
                onClick = {
                    buttonState = !buttonState
                },
                enabled = buttonState
            ) {
                Text(
                    text = "예",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(6.dp)
                )
            }
            TextButton(
                modifier = Modifier
                    .border(
                        BorderStroke(1.dp, Color.Gray),
                        shape = RoundedCornerShape(topEnd = 15.dp, bottomEnd = 15.dp)
                    )
                    .height(55.dp),
                shape = RoundedCornerShape(topEnd = 15.dp, bottomEnd = 15.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    backgroundColor = if(buttonState) Color.Gray else Color.White,
                ),
                onClick = {
                    buttonState = !buttonState
                },
                enabled = !buttonState,
            ) {
                Text(
                    text = "아니오",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(6.dp)
                )
            }
        }
    }
}

