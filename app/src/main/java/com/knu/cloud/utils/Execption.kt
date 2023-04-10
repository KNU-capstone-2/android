package com.knu.cloud.utils

class RetrofitFailureStateException(error: String ?, val code: Int) : Exception(error)