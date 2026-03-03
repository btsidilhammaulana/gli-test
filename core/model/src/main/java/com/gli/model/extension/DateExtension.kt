package com.gli.model.extension

import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

object DateExtension {

  fun String.toFormattedDate(
    outputPattern: String = "dd MMM yyyy",
    locale: Locale = Locale.getDefault()
  ): String {
    return try {
      val instant = Instant.parse(this)
      val formatter = DateTimeFormatter.ofPattern(outputPattern, locale)
        .withZone(ZoneId.systemDefault())

      val zonedDateTime = instant.atZone(ZoneId.systemDefault())
      formatter.format(zonedDateTime)
    } catch (e: Exception) {
      this
    }
  }
}