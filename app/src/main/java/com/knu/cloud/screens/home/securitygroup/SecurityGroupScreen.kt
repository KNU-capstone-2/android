package com.knu.cloud.screens.home.securitygroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun SecurityGroupScreen() {
    Column(
    modifier = Modifier
    .fillMaxSize()
    .background(Color.White)
    .wrapContentSize(Alignment.Center)
    ) {
        Text(text = "SecurityGroupScreen")
    }
}