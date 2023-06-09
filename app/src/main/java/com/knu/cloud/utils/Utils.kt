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

class CustomTimberTree: Timber.DebugTree() {
    override fun createStackElementTag(element: StackTraceElement): String? {
        return "${element.className}_${element.methodName}"
    }
}

fun reformatScreenPath(currentRoute :String?) : String {
    if(currentRoute == null) return "프로젝트"
    return "프로젝트 ${ currentRoute
        .split("/")
        .drop(1)
        .joinToString(" "){ route ->
            "/ ${route.replaceFirstChar { it.uppercase() }}"
        }
    }"
}
