package com.gli.test.util.handler

import android.content.Context
import android.widget.Toast
import com.gli.model.adapter.NetworkError
import java.net.HttpURLConnection

class ErrorHandler(val context: Context?) {
  private var onErrorHandling: ErrorHandling? = null

  fun addOnErrorHandling(errorHandling: ErrorHandling): ErrorHandler {
    this.onErrorHandling = errorHandling
    return this
  }

  fun showError(error: NetworkError) {
    when (error) {
      is NetworkError.HttpError -> onError(error)
      is NetworkError.IOError -> onError(error)
      is NetworkError.UnexpectedError -> onError(error)
    }
  }

  private fun onError(error: NetworkError.HttpError) {
    when (error.code) {
      HttpURLConnection.HTTP_UNAUTHORIZED -> {
        onErrorHandling?.onUnauthorized(error)
      }

      else -> {
        Toast.makeText(context, error.body, Toast.LENGTH_LONG).show()
      }
    }
  }

  private fun onError(error: NetworkError.IOError) {
    error.cause.printStackTrace()
    Toast.makeText(context, error.cause.message, Toast.LENGTH_LONG).show()
  }

  private fun onError(error: NetworkError.UnexpectedError) {
    error.cause.printStackTrace()
    Toast.makeText(context, error.cause.message, Toast.LENGTH_LONG).show()
  }
}