package com.gli.model.adapter

sealed class NetworkResponse<out R> {
  data object Loading : NetworkResponse<Nothing>()

  data class Success<T>(val body: T) : NetworkResponse<T>()

  data class Error(val error: NetworkError) : NetworkResponse<Nothing>()

  data class ThrowableError(val error: Throwable) : NetworkResponse<Nothing>()
}

inline infix fun <T> NetworkResponse<T>.loading(predicate: () -> Unit): NetworkResponse<T> {
  if (this is NetworkResponse.Loading) {
    predicate.invoke()
  }
  return this
}

inline infix fun <T> NetworkResponse<T>.success(predicate: (data: T) -> Unit): NetworkResponse<T> {
  if (this is NetworkResponse.Success) {
    predicate.invoke(this.body)
  }
  return this
}

inline infix fun <T> NetworkResponse<T>.error(predicate: (data: NetworkError) -> Unit) {
  if (this is NetworkResponse.Error) {
    predicate.invoke(this.error)
  }
}