package com.knu.cloud.components.summary

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun StateWithText(
    title: String,
    stateIcon: Int,
    contentColor: Color,
    content: String
) {
    Text(
        text = title,
        fontWeight = FontWeight.Bold,
        color = Color.LightGray,
        fontSize = 18.sp,
    )
    Spacer(modifier = Modifier.height(5.dp))
    Row(
        modifier = Modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
//        Image(
//            painter = painterResource(id = stateIcon),
//            contentDescription = "icon",
//            modifier = Modifier.size(35.dp),
//        )
//        Spacer(modifier = Modifier.width(5.dp))
        Text(
            text = content,
            fontWeight = FontWeight.Bold,
            fontSize = 25.sp,
            color = contentColor
        )
    }
    Spacer(modifier = Modifier.height(10.dp)) // 위 아래 간격
}
