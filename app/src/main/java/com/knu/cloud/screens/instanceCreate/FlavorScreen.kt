package com.knu.cloud.screens.instanceCreate

import androidx.compose.runtime.Composable
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.knu.cloud.R
import com.knu.cloud.components.data_grid.FlavorDataGrid

@Composable
fun FlavorScreen (
    viewModel: InstanceCreateViewModel
) {
    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = Color.White
    ) {
        InstanceState(viewModel = viewModel)
    }
}

@Composable
fun InstanceState(
    viewModel: InstanceCreateViewModel,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
    ) {
        Text(
            text = stringResource(R.string.IC_Flavor_description),
            style = MaterialTheme.typography.subtitle2,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(20.dp)
        )
        Column(
            modifier = Modifier
                .animateContentSize(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioNoBouncy,
                        stiffness = Spring.StiffnessLow
                    )
                )
        ) {
            // 할당됨
            FlavorDataGrid(
                type = "할당됨",
                dataSet = viewModel.uploadFlavor.value,
                numbers = viewModel.uploadFlavor.value.size
            ) { it, idx ->
                viewModel.deleteFlavor(it, idx)
            }
        }
        Divider(
            color = Color.LightGray.copy(alpha = 0.3f),
            thickness = 1.dp,
            modifier = Modifier.padding(5.dp)
        )
        Column(
            modifier = Modifier
                .animateContentSize(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioNoBouncy,
                        stiffness = Spring.StiffnessLow
                    )
                )
        ) {
            // 사용 가능
            FlavorDataGrid(
                type = "사용 가능",
                dataSet = viewModel.possibleFlavor.value,
                numbers = viewModel.possibleFlavor.value.size
            ) { it, idx ->
                viewModel.uploadFlavor(it, idx)
            }
        }
    }
}