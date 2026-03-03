package com.gli.model.extension

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

object DateExtension {

  fun String.toFormattedDate(
    inputPattern: String = "yyyy-MM-dd",
    outputPattern: String = "dd MMM yyyy",
    locale: Locale = Locale.getDefault()
  ): String {
    val inputFormatter = DateTimeFormatter.ofPattern(inputPattern, locale)
    val outputFormatter = DateTimeFormatter.ofPattern(outputPattern, locale)

    return LocalDate.parse(this, inputFormatter)
      .format(outputFormatter)
  }
}