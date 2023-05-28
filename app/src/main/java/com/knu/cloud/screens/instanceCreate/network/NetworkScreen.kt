package com.knu.cloud.screens.instanceCreate

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.knu.cloud.R
import com.knu.cloud.components.data_grid.*
import com.knu.cloud.components.text_input.addFocusCleaner
import com.knu.cloud.model.instanceCreate.NetworkData

@ExperimentalComposeUiApi
@Composable
fun NetworkScreen (
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
            Network(viewModel = viewModel)
        }
    }
}

@ExperimentalComposeUiApi
@Composable
fun Network(
    viewModel: InstanceCreateViewModel,
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    var uploadExpanded by remember { mutableStateOf(true) }
    var possibleExpanded by remember { mutableStateOf(true) }

    val uiState by viewModel.networkUiState.collectAsState()

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .height(600.dp) // Prevent vertical infinity maximum height constraints
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
                numbers = if (uiState.uploadNetwork== null) 0 else 1,
                expanded = uploadExpanded
            ) {
                uploadExpanded = it
            }
        }
        if (uploadExpanded) {
            if(uiState.uploadNetwork != null){
                itemsIndexed(listOf(uiState.uploadNetwork!!)) { index, item ->
                    if (index == 0) DataGridHeader(screenType = "Network")
                    DataGridElementList<NetworkData>(
                        item = item,
                        index = index,
                        type = "할당됨",
                        screenType = "Network"
                    ) { it, idx ->
                        viewModel.deleteNetwork(it)
                    }
                }
            }
        }

        // 사용 가능
        item {
            DataGridBar(
                type = "사용 가능",
                numbers = uiState.possibleNetworks.size,
                expanded = possibleExpanded,
            ) {
                possibleExpanded = it
            }
        }
        if (possibleExpanded) {
            itemsIndexed(uiState.possibleNetworks) { index, item ->
                if (index == 0) DataGridHeader(screenType = "Network")
                DataGridElementList<NetworkData>(
                    item = item,
                    index = index,
                    type = "사용 가능",
                    screenType = "Network"
                ) { it, idx ->
                    if(uiState.uploadNetwork != null) viewModel.updateNetwork(it,idx)
                    else viewModel.uploadNetwork(it,idx)
                }
            }
        }

        // space for footer
        item {
            Spacer(modifier = Modifier.height(100.dp))
        }
    }
}
