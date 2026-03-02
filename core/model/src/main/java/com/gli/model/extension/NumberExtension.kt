package com.gli.model.extension

object NumberExtension {
  fun Int?.orZero(): Int {
    return this ?: 0
  }
}