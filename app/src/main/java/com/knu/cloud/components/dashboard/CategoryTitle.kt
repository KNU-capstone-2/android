package com.knu.cloud.components.dashboard

import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun CategoryTitle(type: String) {
    Text(
        text = type,
        modifier = Modifier
            .padding(10.dp),
        style = MaterialTheme.typography.h5
    )
}