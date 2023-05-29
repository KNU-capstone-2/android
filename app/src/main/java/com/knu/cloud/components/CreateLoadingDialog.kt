package com.knu.cloud.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.knu.cloud.R

@Composable
fun CreateLoadingDialog(

) {
    Dialog(
        onDismissRequest = {}
    ) {
        Box(
            modifier = Modifier
                .height(250.dp)
        ) {
            Column(
                modifier = Modifier
            ) {
                Spacer(modifier = Modifier.height(130.dp))
                Box(
                    modifier = Modifier
                        .height(490.dp)
                        .width(250.dp)
                        .background(
                            color = Color.White,
                            shape = RoundedCornerShape(25.dp, 10.dp, 25.dp, 10.dp)
                        )
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Spacer(modifier = Modifier.height(24.dp))

                        Text(
                            text = "리소스 프로비저닝 중..",
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .padding(top = 10.dp)
                                .fillMaxWidth(),
                            fontSize = 15.sp,
                            color = MaterialTheme.colors.onSecondary,
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
            LottieImage(
                modifier = Modifier
                    .size(200.dp)
                    .align(Alignment.TopCenter),
                rawAnimation = R.raw.create
            )
        }
    }
}
