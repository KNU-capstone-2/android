package com.knu.cloud.screens.instanceCreate

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@ExperimentalComposeUiApi
@Composable
fun InstanceCreateScreen(
    navController: NavController,
    viewModel: InstanceCreateViewModel = hiltViewModel()
) {
    var step by remember { mutableStateOf("Detail") }

    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = Color.White
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TextButton(onClick = { step = "Detail"}) {
                    Text(
                        text = "Detail"
                    )
                }
                TextButton(onClick = { step = "Flavor" }) {
                    Text(
                        text = "Flavor"
                    )
                }
                TextButton(onClick = { step = "Source" }) {
                    Text(
                        text = "Source"
                    )
                }
                TextButton(onClick = { step = "Network" }) {
                    Text(
                        text = "Network"
                    )
                }
            }
            when (step) {
                "Detail" -> DetailScreen(viewModel = viewModel)
                "Flavor" -> FlavorScreen(viewModel = viewModel)
                "Source" -> SourceScreen(viewModel = viewModel)
                "Network" -> NetworkScreen(viewModel = viewModel)
            }
        }
    }
}