package com.gli.network

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.gli.model.anotation.Token
import com.gli.test.model.BuildConfig
import com.gli.test.network.R
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class NetworkClient(
  @param:ApplicationContext private val context: Context,
  @param:Token private val token: String?,
) {
  companion object {
    private const val CONNECTION_TIMEOUT: Long = 30
    private const val CACHE_SIZE = 16 * 1024 * 1024L
  }

  fun <T> create(clazz: Class<T>): T {
    val client = getOkHttpClient()
    Retrofit.Builder()
      .baseUrl(context.getString(R.string.base_url))
      .client(client)
      .addConverterFactory(GsonConverterFactory.create())
      .build()
      .also { return it.create(clazz) }
  }

  private fun getOkHttpClient(): OkHttpClient {
    return OkHttpClient.Builder()
      .addInterceptor(getHeaderInterceptor())
      .addInterceptor(getLoggingInterceptor())
      .addInterceptor(getChuckerInterceptor())
      .connectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
      .writeTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
      .readTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
      .cache(Cache(context.cacheDir, CACHE_SIZE))
      .retryOnConnectionFailure(true)
      .build()
  }

  private fun getLoggingInterceptor(): HttpLoggingInterceptor {
    return HttpLoggingInterceptor().apply {
      level = if (BuildConfig.DEBUG)
        HttpLoggingInterceptor.Level.BODY
      else
        HttpLoggingInterceptor.Level.NONE
    }
  }

  private fun getChuckerInterceptor(): ChuckerInterceptor {
    return ChuckerInterceptor.Builder(context)
      .collector(ChuckerCollector(context))
      .maxContentLength(250000L)
      .redactHeaders(emptySet())
      .alwaysReadResponseBody(true)
      .build()
  }

  private fun getHeaderInterceptor(): Interceptor {
    return Interceptor { chain ->
      val original = chain.request()
      val modified = original.newBuilder()
      modified.addHeader("Content-Type", "application/json")
      if (token?.isBlank() == false) {
        modified.addHeader("Authorization", "Bearer $token")
      }
      modified.method(original.method, original.body)
      val request: Request = modified.build()
      chain.proceed(request)
    }
  }
}