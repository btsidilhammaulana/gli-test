package com.gli.test.util.permission

import android.app.Activity
import androidx.core.app.ActivityCompat

object PermissionUtils {

  private fun Map<String, Boolean>.allDenied() = all { it.value.not() }

  private fun Map<String, Boolean>.isAnyPermissionDeniedRationale(activity: Activity?): Boolean {
    return activity != null && any {
      ActivityCompat.shouldShowRequestPermissionRationale(activity, it.key)
    }
  }

  fun Map<String, Boolean>.checkPermissionResult(
    activity: Activity?,
    onPermissionDeniedPermanently: (Map<String, Boolean>) -> Unit,
    onPermissionDeniedRationale: (Map<String, Boolean>) -> Unit,
    onPermissionGranted: () -> Unit
  ) = when {
    isEmpty() -> onPermissionGranted()
    allDenied() -> when {
      isAnyPermissionDeniedRationale(activity) -> onPermissionDeniedRationale(this)
      else -> onPermissionDeniedPermanently(this)
    }

    else -> onPermissionGranted()
  }
}
