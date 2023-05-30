package com.knu.cloud.model.dialog

import com.knu.cloud.utils.ToastStatus

data class CreateInstanceDialogState(
    val showProgressDialog: Boolean = false,
    val message : String = "",
    val toastStatus: ToastStatus = ToastStatus.INFO
)