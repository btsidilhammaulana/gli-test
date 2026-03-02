package com.gli.network.extension

import com.gli.model.adapter.NetworkError
import com.gli.model.adapter.NetworkResponse
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException
import retrofit2.Response

object JsonExtensions {

  inline fun <reified T> Response<JsonObject>.toFlow(
    crossinline onReceiveData: (T) -> Unit = {}
  ) = flow {
    emit(NetworkResponse.Loading)
    try {
      emit(serialize(this@toFlow, onReceiveData))
    } catch (e: Throwable) {
      val error = NetworkError.UnexpectedError(e)
      emit(NetworkResponse.Error(error))
    }
  }

  inline fun <reified T> serialize(
    response: Response<JsonObject>,
    onReceiveData: (T) -> Unit
  ): NetworkResponse<T> {
    try {
      if (!response.isSuccessful) {
        val error = NetworkError.HttpError(
          code = response.code(),
          message = response.message(),
          body = response.errorBody()?.string() ?: "Unknown error"
        )
        return NetworkResponse.Error(error)
      }

      val gson = Gson()
      val typeToken = object : TypeToken<T>() {}.type
      val model = gson.fromJson<T>(response.body(), typeToken)

      val emptyBody = NetworkError.HttpError(
        code = 0,
        message = "empty body",
        body = "empty body"
      )

      val data = model ?: return NetworkResponse.Error(emptyBody)
      onReceiveData.invoke(data as T)
      return NetworkResponse.Success(data as T)
    } catch (e: HttpException) {
      val error = NetworkError.HttpError(
        code = e.code(),
        message = e.message(),
        body = e.response()?.errorBody()?.string() ?: "Unknown HTTP error"
      )
      return NetworkResponse.Error(error)
    } catch (e: IOException) {
      return NetworkResponse.Error(NetworkError.IOError(e))
    } catch (e: Throwable) {
      return NetworkResponse.Error(NetworkError.UnexpectedError(e))
    }
  }
}