package com.gli.model.response.review

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class AuthorModel(
  @Expose
  @SerializedName("name")
  val name: String? = null,

  @Expose
  @SerializedName("username")
  val username: String? = null,

  @Expose
  @SerializedName("avatar_path")
  val avatarPath: String? = null,

  @Expose
  @SerializedName("rating")
  val rating: Double? = null
)