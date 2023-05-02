package com.knu.cloud.screens.instanceCreate

import android.content.res.Configuration
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.hilt.navigation.compose.hiltViewModel
import com.knu.cloud.R
import com.knu.cloud.components.LaunchButton
import com.knu.cloud.components.data_grid.*
import com.knu.cloud.components.text_input.*
import timber.log.Timber

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
    var selectedTitle by remember { mutableStateOf("Image") }
    val keyboardController = LocalSoftwareKeyboardController.current
    var volumnSizeExpanded by remember { mutableStateOf(true)}

    var uploadExpanded by remember { mutableStateOf(false) }
    var possibleExpanded by remember { mutableStateOf(true) }

    val uploadList = viewModel.uploadSource.value
    val possibleList = viewModel.possibleSource.value

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
                    selectedTitle = it
                }
                VolumeSwitchButton(bootSourceType = selectedTitle) {
                    volumnSizeExpanded = !it // <- ! 붙여준거 주의
                }
            }
            if (volumnSizeExpanded && (selectedTitle == "Image" || selectedTitle == "인스턴스 스냅샷")) {
                Row(
                    modifier = Modifier.padding(10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    VolumeSize(
                        keyboardController = keyboardController
                    ) {
                        /*  */
                    }
                    VolumeSizeSwitchButton()
                }
            }
        }

        // 할당됨
        item {
            DataGridBar(
                type = "할당됨",
                numbers = viewModel.uploadSource.value.size,
                expanded = uploadExpanded
            ) {
                uploadExpanded = it
            }
        }
        if (uploadExpanded) {
            itemsIndexed(uploadList) { index, item ->
                if (index == 0) DataGridHeader(screenType = "Source")
                DataGridElementList<Source>(
                    item = item,
                    index = index,
                    type = "할당됨",
                    screenType = "Source"
                ) { it, idx ->
                    viewModel.deleteSource(it, idx)
                }
            }
        }

        // 사용 가능
        item {
            DataGridBar(
                type = "사용 가능",
                numbers = viewModel.possibleSource.value.size,
                expanded = possibleExpanded,
            ) {
                possibleExpanded = it
            }
        }
        if (possibleExpanded) {
            itemsIndexed(possibleList) { index, item ->
                if (index == 0) DataGridHeader(screenType = "Source")
                DataGridElementList<Source>(
                    item = item,
                    index = index,
                    type = "사용 가능",
                    screenType = "Source"
                ) { it, idx ->
                    viewModel.uploadSource(it, idx)
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
            modifier = Modifier.padding(start = 5.dp).fillMaxWidth(),
        ) {
            var buttonState by remember { mutableStateOf(false) }
            TextButton(
                modifier = Modifier
                    .border(BorderStroke(1.dp, Color.Gray), shape= RoundedCornerShape(topStart = 15.dp, bottomStart = 15.dp))
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
                    .border(BorderStroke(1.dp, Color.Gray), shape= RoundedCornerShape(topEnd = 15.dp, bottomEnd = 15.dp))
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
        ProjectTextInput(
            type = TextInputType.FIELD,
            keyboardController = keyboardController,
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
            modifier = Modifier.padding(start = 5.dp).fillMaxWidth(),
        ) {
            var buttonState by remember { mutableStateOf(false) }

            TextButton(
                modifier = Modifier
                    .border(BorderStroke(1.dp, Color.Gray), shape= RoundedCornerShape(topStart = 15.dp, bottomStart = 15.dp))
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
                    .border(BorderStroke(1.dp, Color.Gray), shape= RoundedCornerShape(topEnd = 15.dp, bottomEnd = 15.dp))
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

