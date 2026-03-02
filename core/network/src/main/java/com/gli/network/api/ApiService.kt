package com.gli.network.api

import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.QueryMap
import retrofit2.http.QueryName
import retrofit2.http.Url

interface ApiServices {

  @POST
  suspend fun post(
    @Url url: String,
    @Body requestBody: Any,
    @HeaderMap headers: Map<String, String> = hashMapOf(),
    @QueryMap queryMap: Map<String, String> = hashMapOf()
  ): Response<JsonObject>

  @POST
  suspend fun post(
    @Url url: String,
    @Body requestBody: Any,
    @HeaderMap headers: Map<String, String> = hashMapOf(),
    @QueryName vararg queryNames: String = arrayOf()
  ): Response<JsonObject>

  @GET
  suspend fun get(
    @Url url: String,
    @HeaderMap headers: Map<String, String> = hashMapOf(),
    @QueryMap queryMap: Map<String, String> = hashMapOf()
  ): Response<JsonObject>

  @GET
  suspend fun get(
    @Url url: String,
    @HeaderMap headers: Map<String, String> = hashMapOf(),
    @QueryName vararg queryNames: String = arrayOf()
  ): Response<JsonObject>

  @PUT
  suspend fun put(
    @Url url: String,
    @Body request: Any? = null,
    @HeaderMap headers: Map<String, String> = hashMapOf(),
    @QueryMap queryMap: Map<String, String> = hashMapOf()
  ): Response<JsonObject>

  @PUT
  suspend fun put(
    @Url url: String,
    @Body request: Any? = null,
    @HeaderMap headers: Map<String, String> = hashMapOf(),
    @QueryName vararg queryNames: String = arrayOf()
  ): Response<JsonObject>

  @PATCH
  suspend fun patch(
    @Url url: String,
    @Body request: Any? = null,
    @HeaderMap headers: Map<String, String> = hashMapOf(),
    @QueryMap queryMap: Map<String, String> = hashMapOf()
  ): Response<JsonObject>

  @PATCH
  suspend fun patch(
    @Url url: String,
    @Body request: Any? = null,
    @HeaderMap headers: Map<String, String> = hashMapOf(),
    @QueryName vararg queryNames: String = arrayOf()
  ): Response<JsonObject>

  @DELETE
  suspend fun delete(
    @Url url: String,
    @Body request: Any? = null,
    @HeaderMap headers: Map<String, String> = hashMapOf(),
    @QueryMap queryMap: Map<String, String> = hashMapOf()
  ): Response<JsonObject>

  @DELETE
  suspend fun delete(
    @Url url: String,
    @Body request: Any? = null,
    @HeaderMap headers: Map<String, String> = hashMapOf(),
    @QueryName vararg queryNames: String = arrayOf()
  ): Response<JsonObject>
}