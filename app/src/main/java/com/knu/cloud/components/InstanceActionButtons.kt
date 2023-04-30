package com.knu.cloud.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.knu.cloud.R

@Composable
fun InstanceActionButtons(
    StartClicked: () -> Unit,
    ReStartClicked: () -> Unit,
    StopClicked: () -> Unit
) {
    Row(
        modifier = Modifier
            .padding(10.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        GetDrawableImage(
            imageData = R.drawable.instance_start,
            onClick = { StartClicked() }
        )
        Spacer(modifier = Modifier.width(20.dp))
        GetDrawableImage(
            imageData = R.drawable.instance_restart,
            onClick = { ReStartClicked() }
        )
        Spacer(modifier = Modifier.width(20.dp))
        GetDrawableImage(
            imageData = R.drawable.instance_stop,
            onClick = { StopClicked() }
        )
    }
}

@Composable
fun GetDrawableImage(
    imageData: Int,
    onClick: () -> Unit
) {
    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(imageData)
            .size(100)
            .crossfade(true)
            .build(),
    )
    if (painter.state is AsyncImagePainter.State.Loading ||
        painter.state is AsyncImagePainter.State.Error
    ) {
        CircularProgressIndicator()
    }
    IconButton(
        onClick = onClick
    ) {
        Image(
            painter = painter,
            contentDescription = "instance button",
            modifier = Modifier.size(80.dp)
        )
    }
}