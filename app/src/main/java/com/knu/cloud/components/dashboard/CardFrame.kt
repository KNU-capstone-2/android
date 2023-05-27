package com.knu.cloud.components.dashboard

import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CardFrame(
    title: String,
    count: String,
    cardColor: Int,
    textColor1: Int = 0,
    textColor2: Int = 0
) {
    Card(
        modifier = Modifier.width(150.dp).height(150.dp).padding(5.dp),
        backgroundColor = colorResource(id = cardColor),
    ) {
        Column(
            modifier = Modifier.padding(10.dp)
        ) {
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = title,
                fontSize = 14.sp,
                color = colorResource(id = textColor1)
            )
            Spacer(modifier = Modifier.height(50.dp))
            Text(
                text = count,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = colorResource(id = textColor2)
            )
        }
    }
}
