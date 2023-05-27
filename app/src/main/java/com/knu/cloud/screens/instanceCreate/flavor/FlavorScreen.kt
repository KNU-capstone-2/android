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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.knu.cloud.R
import com.knu.cloud.components.data_grid.*
import com.knu.cloud.components.text_input.addFocusCleaner
import com.knu.cloud.model.instanceCreate.FlavorData

@ExperimentalComposeUiApi
@Composable
fun FlavorScreen (
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
            Flavor(viewModel = viewModel)
        }
    }

}

@ExperimentalComposeUiApi
@Composable
fun Flavor(
    viewModel: InstanceCreateViewModel,
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    var uploadExpanded by remember { mutableStateOf(true) }
    var possibleExpanded by remember { mutableStateOf(true) }

    val uiState by viewModel.flavorUiState.collectAsState()

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .height(600.dp) // Prevent vertical infinity maximum height constraints
            .addFocusCleaner(keyboardController!!),
    ) {
        item {
            Text(
                text = stringResource(R.string.IC_Flavor_description),
                style = MaterialTheme.typography.subtitle2,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(20.dp)
            )
        }
        // 할당됨
        item {
            DataGridBar(
                type = "할당됨",
                numbers = if (uiState.uploadFlavor== null) 0 else 1,
                expanded = uploadExpanded
            ) {
                uploadExpanded = it
            }
        }
        if (uploadExpanded) {
            if(uiState.uploadFlavor != null){
                itemsIndexed(listOf(uiState.uploadFlavor!!)) { index, item ->
                    if (index == 0) DataGridHeader(screenType = "Flavor")
                    DataGridElementList<FlavorData>(
                        item = item,
                        index = index,
                        type = "할당됨",
                        screenType = "Flavor"
                    ) { it, idx ->
                        viewModel.deleteFlavor(it)
                    }
                }
            }
        }

        // 사용 가능
        item {
            DataGridBar(
                type = "사용 가능",
                numbers = uiState.possibleFlavors.size,
                expanded = possibleExpanded,
            ) {
                possibleExpanded = it
            }
        }
        if (possibleExpanded) {
            itemsIndexed(uiState.possibleFlavors) { index, item ->
                if (index == 0) DataGridHeader(screenType = "Flavor")
                DataGridElementList<FlavorData>(
                    item = item,
                    index = index,
                    type = "사용 가능",
                    screenType = "Flavor"
                ) { it, idx ->
                    if(uiState.uploadFlavor != null) viewModel.updateFlavor(it,idx)
                    else viewModel.uploadFlavor(it,idx)
                }
            }
        }

        // space for footer
        item {
            Spacer(modifier = Modifier.height(100.dp))
        }
    }
}



/*
        Column(
            modifier = Modifier
                .animateContentSize(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioNoBouncy,
                        stiffness = Spring.StiffnessLow
                    )
                )
        )
        애니메이션
 */