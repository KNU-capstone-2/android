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
import com.knu.cloud.model.instanceCreate.FlavorResponse

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

    var uploadExpanded by remember { mutableStateOf(false) }
    var possibleExpanded by remember { mutableStateOf(true) }

    val uploadList = viewModel.uploadFlavor.value
    val possibleList = viewModel.possibleFlavor.value

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
                numbers = viewModel.uploadFlavor.value.size,
                expanded = uploadExpanded
            ) {
                uploadExpanded = it
            }
        }
        if (uploadExpanded) {
            itemsIndexed(uploadList) { index, item ->
                if (index == 0) DataGridHeader(screenType = "Flavor")
                DataGridElementList<FlavorResponse>(
                    item = item,
                    index = index,
                    type = "할당됨",
                    screenType = "Flavor"
                ) { it, idx ->
                    viewModel.deleteFlavor(it, idx)
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
                if (index == 0) DataGridHeader(screenType = "Flavor")
                DataGridElementList<FlavorResponse>(
                    item = item,
                    index = index,
                    type = "사용 가능",
                    screenType = "Flavor"
                ) { it, idx ->
                    viewModel.uploadFlavor(it, idx)
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