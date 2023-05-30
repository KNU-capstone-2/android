package com.knu.cloud.utils

import android.app.Activity
import android.content.Context
import androidx.core.content.res.ResourcesCompat
import www.sanju.motiontoast.MotionToast
import www.sanju.motiontoast.MotionToastStyle

enum class ToastStatus{
    SUCCESS,DELETE,ERROR,INFO
}

fun showMotionToastMessage(context: Context,status: ToastStatus,message: String){
    when(status){
        ToastStatus.SUCCESS -> toastSuccessMessage(context,message)
        ToastStatus.DELETE -> toastDeleteMessage(context,message)
        ToastStatus.ERROR -> toastErrorMessage(context,message)
        ToastStatus.INFO -> toastInfoMessage(context,message)
    }
}



fun toastSuccessMessage(
    context: Context,
    message: String,
) {
    MotionToast.createColorToast(
        context as Activity,
        "Success",
        message,
        MotionToastStyle.SUCCESS,
        MotionToast.GRAVITY_BOTTOM,
        MotionToast.SHORT_DURATION,
        ResourcesCompat.getFont(context, www.sanju.motiontoast.R.font.helvetica_regular)
    )
}

fun toastDeleteMessage(
    context: Context,
    message: String,
) {
    MotionToast.createColorToast(
        context as Activity,
        "Delete",
        message,
        MotionToastStyle.DELETE,
        MotionToast.GRAVITY_BOTTOM,
        MotionToast.SHORT_DURATION,
        ResourcesCompat.getFont(context as Activity, www.sanju.motiontoast.R.font.helvetica_regular)
    )
}

fun toastErrorMessage(
    context: Context,
    message: String,
) {
    MotionToast.createColorToast(
        context as Activity,
        "Error",
        message,
        MotionToastStyle.ERROR,
        MotionToast.GRAVITY_BOTTOM,
        MotionToast.SHORT_DURATION,
        ResourcesCompat.getFont(context as Activity, www.sanju.motiontoast.R.font.helvetica_regular)
    )
}

fun toastInfoMessage(
    context: Context,
    message: String,
) {
    MotionToast.createColorToast(
        context as Activity,
        "Info",
        message,
        MotionToastStyle.INFO,
        MotionToast.GRAVITY_BOTTOM,
        MotionToast.SHORT_DURATION,
        ResourcesCompat.getFont(context as Activity, www.sanju.motiontoast.R.font.helvetica_regular)
    )
}