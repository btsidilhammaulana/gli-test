package com.gli.test.util.handler

import com.gli.model.adapter.NetworkError

interface ErrorHandling {
  fun onUnauthorized(error: NetworkError.HttpError)
}
