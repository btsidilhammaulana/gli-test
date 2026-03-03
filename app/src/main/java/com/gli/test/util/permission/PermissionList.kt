package com.gli.test.util.permission

import android.Manifest
import android.os.Build

object PermissionList {

  val Camera = arrayOf(
    Manifest.permission.CAMERA
  )

  val Location = arrayListOf(
    Manifest.permission.ACCESS_COARSE_LOCATION,
    Manifest.permission.ACCESS_FINE_LOCATION
  )

  val Notification: Array<String>
    get() = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
      arrayOf(Manifest.permission.POST_NOTIFICATIONS)
    } else {
      arrayOf()
    }

  val Storage: Array<String>
    get() {
      return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        arrayOf()
      } else {
        arrayOf(
          Manifest.permission.READ_EXTERNAL_STORAGE,
          Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
      }
    }
}
