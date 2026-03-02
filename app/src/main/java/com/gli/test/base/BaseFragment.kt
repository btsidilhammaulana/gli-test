package com.gli.test.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.gli.model.adapter.NetworkError
import com.gli.test.base.launcher.AppLauncher
import com.gli.test.base.launcher.AppLauncherType
import com.gli.test.util.handler.ErrorHandler
import com.gli.test.util.handler.ErrorHandling

abstract class BaseFragment<VB : ViewBinding> : Fragment() {

  protected lateinit var appLauncher: AppLauncher

  protected lateinit var binding: VB

  protected lateinit var errorHandler: ErrorHandler

  override fun onAttach(context: Context) {
    super.onAttach(context)
    this.appLauncher = registerForAppLauncher()
    this.errorHandler = registerErrorHandler()
  }

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

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    this.binding = setLayout(layoutInflater, container)
    return binding.root
  }

  protected abstract fun setLayout(inflater: LayoutInflater, container: ViewGroup?): VB

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    initialization()
  }

  protected abstract fun initialization()

  /**
   * Error Handler
   */
  private val onErrorHandling = object : ErrorHandling {
    override fun onUnauthorized(error: NetworkError.HttpError) {}
  }

  private fun registerErrorHandler(): ErrorHandler {
    return ErrorHandler(context).addOnErrorHandling(onErrorHandling)
  }
}