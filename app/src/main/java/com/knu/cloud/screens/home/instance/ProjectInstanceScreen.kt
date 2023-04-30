package com.knu.cloud.screens.home.instance

import android.widget.ImageButton
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.*
import androidx.compose.material.*
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.knu.cloud.R

@Composable
fun ProjectInstanceScreen (
    onInstanceCreateClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .wrapContentSize(Alignment.Center)
    ) {
        Text(text = "InstanceScreen")
//        Button(onClick = { onInstanceCreateClick() }) {
//            Text(text = "create instance")
//        }
    }
}