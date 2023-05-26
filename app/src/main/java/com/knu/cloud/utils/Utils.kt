package com.knu.cloud.utils
import timber.log.Timber
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

fun convertDateFormat(input: String): String {
    Timber.tag("convertDateFormat").d("input : $input")

    val inputDateTime = ZonedDateTime.parse(input, DateTimeFormatter.ISO_DATE_TIME)
    val outputDate = inputDateTime.toLocalDateTime().toLocalDate()
    val returnDate = outputDate.format(DateTimeFormatter.ofPattern("yyyy/MM/dd"))

    Timber.tag("convertDateFormat").d("returnDate : $returnDate")
    return returnDate
}
