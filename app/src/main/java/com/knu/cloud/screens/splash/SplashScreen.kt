package com.knu.cloud.screens.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.knu.cloud.R
import com.knu.cloud.navigation.MainDestination
import kotlinx.coroutines.delay

@Composable
fun ProjectSplashScreen(navController: NavController) {
    LaunchedEffect(key1 = true) {
        delay(2000L)
        navController.navigate(MainDestination.AUTH_ROUTE)
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.weight(.1f))
        Row(
            modifier = Modifier
                .padding(5.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Logo()
        }
        Spacer(modifier = Modifier.weight(.1f))
        CircularProgressIndicator(
            color = Color.Gray,
            modifier = Modifier
                .size(35.dp)
                .padding(5.dp)
        )
        Spacer(modifier = Modifier.height(30.dp))
    }
}

@Composable
fun Logo() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        val painter = rememberAsyncImagePainter(
            model = ImageRequest.Builder(LocalContext.current)
                .data(R.drawable.splash)
                .size(100)
                .crossfade(true)
                .build(),
        )
        if (painter.state is AsyncImagePainter.State.Loading ||
            painter.state is AsyncImagePainter.State.Error
        ) {
            CircularProgressIndicator()
        }
        Image(
            painter = painter,
            contentDescription = "Splash Img"
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = stringResource(id = R.string.Splash_title),
            fontFamily = FontFamily(Font(R.font.bmdohyeon)),
            fontSize = 30.sp,
            color = colorResource(id = R.color.Black_Main),
        )
    }
}