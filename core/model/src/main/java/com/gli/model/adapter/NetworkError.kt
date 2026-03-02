package com.gli.model.adapter

import java.io.IOException

sealed class NetworkError {
  data class HttpError(
    val code: Int,
    val message: String,
    val body: String,
  ) : NetworkError()

  data class IOError(val cause: IOException) : NetworkError()

  data class UnexpectedError(val cause: Throwable) : NetworkError()
}

inline infix fun NetworkError.httpError(predicate: (error: NetworkError.HttpError) -> Unit): NetworkError {
  if (this is NetworkError.HttpError) {
    predicate.invoke(this)
  }
  return this
}

inline infix fun NetworkError.ioError(predicate: (error: NetworkError.IOError) -> Unit): NetworkError {
  if (this is NetworkError.IOError) {
    predicate.invoke(this)
  }
  return this
}

inline infix fun NetworkError.unexpectedError(predicate: (error: NetworkError.UnexpectedError) -> Unit): NetworkError {
  if (this is NetworkError.UnexpectedError) {
    predicate.invoke(this)
  }
  return this
}