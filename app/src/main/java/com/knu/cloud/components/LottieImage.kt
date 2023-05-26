package com.knu.cloud.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.airbnb.lottie.compose.*
import com.knu.cloud.R

/*
https://github.com/airbnb/lottie/blob/master/android-compose.md
 */

@Composable
fun LottieImage(
    modifier: Modifier,
    rawAnimation: Int
) {
    val composition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(rawAnimation))
    val progress by animateLottieCompositionAsState(composition, iterations = LottieConstants.IterateForever)
    LottieAnimation(
        composition = composition,
        progress = { progress },
        modifier = modifier,
    )
}