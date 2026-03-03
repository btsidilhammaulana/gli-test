package com.gli.test.screen.splash

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import com.gli.test.base.BaseActivity
import com.gli.test.databinding.ActivitySplashBinding
import com.gli.test.screen.dashboard.DashboardActivity
import com.gli.test.util.permission.PermissionList
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashScreen : BaseActivity<ActivitySplashBinding>() {

  override fun isFullscreenActivity(): Boolean = true

  override fun onCreate(savedInstanceState: Bundle?) {
    installSplashScreen()
    super.onCreate(savedInstanceState)
  }

  override fun setLayout(inflater: LayoutInflater): ActivitySplashBinding {
    return ActivitySplashBinding.inflate(layoutInflater)
  }

  override fun initialization() {
    checkNotificationPermissions()
  }

  private fun checkNotificationPermissions() = lifecycleScope.launch {
    delay(500)
    appLauncher.requestPermissions(PermissionList.Notification) {
      gotoDashboardPage()
    }
  }

  private fun gotoDashboardPage() {
    DashboardActivity.newIntent(this).also { startActivity(it) }
    finish()
  }
}