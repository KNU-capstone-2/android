package com.knu.cloud.screens.instanceCreate

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.knu.cloud.R
import com.knu.cloud.components.data_grid.*
import com.knu.cloud.components.text_input.addFocusCleaner

@ExperimentalComposeUiApi
@Composable
fun NetworkScreen (
    viewModel: InstanceCreateViewModel
) {
    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = Color.White
    ) {
        Network(viewModel = viewModel)
    }
}

@ExperimentalComposeUiApi
@Composable
fun Network(
    viewModel: InstanceCreateViewModel,
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    var uploadExpanded by remember { mutableStateOf(false) }
    var possibleExpanded by remember { mutableStateOf(true) }

    val uploadList = viewModel.uploadNetwork.value
    val possibleList = viewModel.possibleNetwork.value

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .addFocusCleaner(keyboardController!!),
    ) {
        item {
            Text(
                text = stringResource(R.string.IC_Network_description),
                style = MaterialTheme.typography.subtitle2,
                modifier = Modifier.padding(20.dp)
            )
        }

        // 할당됨
        item {
            DataGridBar(
                type = "할당됨",
                numbers = viewModel.uploadNetwork.value.size,
                expanded = uploadExpanded
            ) {
                uploadExpanded = it
            }
        }
        if (uploadExpanded) {
            itemsIndexed(uploadList) { index, item ->
                if (index == 0) DataGridHeader(screenType = "Network")
                DataGridElementList<Network>(
                    item = item,
                    index = index,
                    type = "할당됨",
                    screenType = "Network"
                ) { it, idx ->
                    viewModel.deleteNetwork(it, idx)
                }
            }
        }

        // 사용 가능
        item {
            DataGridBar(
                type = "사용 가능",
                numbers = viewModel.possibleNetwork.value.size,
                expanded = possibleExpanded,
            ) {
                possibleExpanded = it
            }
        }
        if (possibleExpanded) {
            itemsIndexed(possibleList) { index, item ->
                if (index == 0) DataGridHeader(screenType = "Network")
                DataGridElementList<Network>(
                    item = item,
                    index = index,
                    type = "사용 가능",
                    screenType = "Network"
                ) { it, idx ->
                    viewModel.uploadNetwork(it, idx)
                }
            }
        }

        // space for footer
        item {
            Spacer(modifier = Modifier.height(100.dp))
        }
    }
}
