package com.knu.cloud.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import com.knu.cloud.R


/**
 * 인스턴스 또는 이미지 상태에 따라 색 변환해주는 함수
 * */
@Composable
fun convertStatusColor (status : String) : Color {
    return when(status){
        "ACTIVE"-> colorResource(id = R.color.instance_state_active)
        "REBOOT" -> colorResource(id = R.color.instance_state_reboot)
        "PAUSED" -> colorResource(id = R.color.instance_state_paused)
        else -> Color.Black
    }
}