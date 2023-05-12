package com.knu.cloud.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.knu.cloud.R

@Composable
fun LaunchButton(
    onLaunchBtnClicked:() -> Unit = {}
) {
    OutlinedButton(
        onClick = {
            onLaunchBtnClicked()
        },
        shape = RoundedCornerShape(percent = 50), // 모서리를 둥글게 처리
        modifier = Modifier
            .padding(16.dp)
            .height(48.dp)
            .wrapContentWidth(),
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_launch_instance),
            contentDescription = "launch icon",
            modifier = Modifier.size(20.dp),
            tint = Color.Black
        )
        Text(
            text = "Launch Instance",
            fontWeight = FontWeight.Bold,
            fontSize = 15.sp,
            modifier = Modifier.padding(horizontal = 16.dp) // 버튼 내부 여백
        )
    }
}