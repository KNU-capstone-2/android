package com.knu.cloud.utils

import android.app.Activity
import android.content.Context
import androidx.compose.runtime.Composable
import androidx.core.content.res.ResourcesCompat
import www.sanju.motiontoast.MotionToast
import www.sanju.motiontoast.MotionToastStyle

fun ToastSuccessMessage(
    context: Context,
    message: String,
) {
    MotionToast.darkToast(
        context as Activity,
        "메세지",
        message,
        MotionToastStyle.SUCCESS,
        MotionToast.GRAVITY_BOTTOM,
        MotionToast.SHORT_DURATION,
        ResourcesCompat.getFont(context as Activity, www.sanju.motiontoast.R.font.helvetica_regular)
    )
}

fun ToastDeleteMessage(
    context: Context,
    message: String,
) {
    MotionToast.darkToast(
        context as Activity,
        "메세지",
        message,
        MotionToastStyle.DELETE,
        MotionToast.GRAVITY_BOTTOM,
        MotionToast.SHORT_DURATION,
        ResourcesCompat.getFont(context as Activity, www.sanju.motiontoast.R.font.helvetica_regular)
    )
}

fun ToastErrorMessage(
    context: Context,
    message: String,
) {
    MotionToast.darkToast(
        context as Activity,
        "메세지",
        message,
        MotionToastStyle.ERROR,
        MotionToast.GRAVITY_BOTTOM,
        MotionToast.SHORT_DURATION,
        ResourcesCompat.getFont(context as Activity, www.sanju.motiontoast.R.font.helvetica_regular)
    )
}

fun ToastInfoMessage(
    context: Context,
    message: String,
) {
    MotionToast.darkToast(
        context as Activity,
        "메세지",
        message,
        MotionToastStyle.INFO,
        MotionToast.GRAVITY_BOTTOM,
        MotionToast.SHORT_DURATION,
        ResourcesCompat.getFont(context as Activity, www.sanju.motiontoast.R.font.helvetica_regular)
    )
}