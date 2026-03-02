package com.gli.model.extension

import com.gli.model.constant.ImageQuality
import com.gli.test.model.BuildConfig

object StringExtensions {

  fun String?.isEmptyOrBlank(): Boolean {
    return isNullOrEmpty() || isNullOrBlank()
  }

  fun String?.isNotEmptyOrBlank(): Boolean {
    return !isEmptyOrBlank()
  }

  fun String?.replaceIfNull(): String {
    return this ?: ""
  }

  fun String?.toImageUrl(imageQuality: String? = ImageQuality.W92): String {
    if(this.isEmptyOrBlank()) return ""
    return BuildConfig.BASE_IMAGE_URL+"$imageQuality"+this
  }
}