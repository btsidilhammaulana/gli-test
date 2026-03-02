package com.gli.test.base

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.viewbinding.ViewBinding
import com.gli.model.adapter.NetworkError
import com.gli.test.R
import com.gli.test.base.launcher.AppLauncher
import com.gli.test.base.launcher.AppLauncherType
import com.gli.test.util.handler.ErrorHandler
import com.gli.test.util.handler.ErrorHandling

abstract class BaseActivity<VB : ViewBinding> : AppCompatActivity() {

  protected lateinit var appLauncher: AppLauncher

  protected lateinit var binding: VB

  protected lateinit var errorHandler: ErrorHandler

  override fun onCreate(savedInstanceState: Bundle?) {
    enableEdgeToEdge(statusBarStyle = SystemBarStyle.dark(Color.WHITE))
    super.onCreate(savedInstanceState)
    setupWindowInset()
    setupOnBackPressed()
    appLauncher = registerForAppLauncher()
    setContentView(getContentView())
    initialization()

    this.errorHandler = registerErrorHandler()
  }

  /**
   * Content View
   */
  private fun getContentView(): View {
    this.binding = setLayout(layoutInflater)
    return binding.root
  }

  protected abstract fun setLayout(inflater: LayoutInflater): VB

  protected abstract fun initialization()

  /**
   * Window Inset Handler For Page
   */
  private fun setupWindowInset() = with(window.decorView) {
    setBackgroundColor(
      ContextCompat.getColor(
        this@BaseActivity,
        R.color.custom_color_page_background
      )
    )

    val insetController = WindowCompat.getInsetsController(window, this).apply {
      systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
    }

    ViewCompat.setOnApplyWindowInsetsListener(this) { v, insets ->
      if (isFullscreenActivity()) {
        insetController.hide(WindowInsetsCompat.Type.systemBars())
      } else {
        insetController.show(WindowInsetsCompat.Type.systemBars())
        val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
        val imeInsets = insets.getInsets(WindowInsetsCompat.Type.ime())
        if (imeInsets.bottom <= systemBars.bottom) {
          v.setPadding(systemBars.left, 0, systemBars.right, systemBars.bottom)
        } else {
          v.setPadding(systemBars.left, 0, systemBars.right, imeInsets.bottom)
        }
      }

      insets
    }
  }

  protected fun setupWindowTopInset(root: View) {
    ViewCompat.setOnApplyWindowInsetsListener(root) { v, insets ->
      val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
      v.setPadding(0, systemBars.top, 0, 0)
      insets
    }
  }

  /**
   * Toggle For Fullscreen Activity
   */
  protected open fun isFullscreenActivity(): Boolean = false

  /**
   * Back Press Handler
   */
  private fun setupOnBackPressed() {
    onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
      override fun handleOnBackPressed() {
        onActivityBackPressed()
      }
    })
  }

  /**
   * Override this if you want custom back press handler
   */
  open fun onActivityBackPressed() {
    finish()
  }

  /**
   * App Launcher Registration
   */
  private fun registerForAppLauncher(): AppLauncher {
    /**
     * set default to Activity Result for enabling new startActivityForResult feature
     * you can override addActivityLauncherType for enabling other feature
     */
    val defaultLauncherType = arrayListOf(
      AppLauncherType.ACTIVITY_RESULT,
      AppLauncherType.REQUEST_PERMISSIONS
    )

    return AppLauncher(this, addAppLauncherType(defaultLauncherType))
  }

  protected open fun addAppLauncherType(launcherTypes: ArrayList<AppLauncherType>): List<AppLauncherType> {
    if (!launcherTypes.contains(AppLauncherType.ACTIVITY_RESULT)) {
      launcherTypes.add(AppLauncherType.ACTIVITY_RESULT)
    }

    if (!launcherTypes.contains(AppLauncherType.REQUEST_PERMISSIONS)) {
      launcherTypes.add(AppLauncherType.REQUEST_PERMISSIONS)
    }

    return launcherTypes
  }

  /**
   * Error Handler
   */
  private val onErrorHandling = object : ErrorHandling {
    override fun onUnauthorized(error: NetworkError.HttpError) {
      onLoggedOut()
    }
  }

  private fun onLoggedOut() {

  }

  private fun registerErrorHandler(): ErrorHandler {
    return ErrorHandler(this).addOnErrorHandling(onErrorHandling)
  }
}