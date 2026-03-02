package com.gli.test.screen.dashboard

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import com.gli.test.base.BaseActivity
import com.gli.test.databinding.ActivityDashboardBinding

class DashboardActivity: BaseActivity<ActivityDashboardBinding>() {
  override fun setLayout(inflater: LayoutInflater): ActivityDashboardBinding {
    return ActivityDashboardBinding.inflate(layoutInflater)
  }

  override fun initialization() {

  }

  companion object {
    fun newIntent(context: Context?): Intent {
      return Intent(context, DashboardActivity::class.java).apply {
        flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
      }
    }
  }
}