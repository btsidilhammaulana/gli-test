package com.gli.model.response.genre

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class GenreModel(
  @Expose
  @SerializedName("id")
  val id: Int? = null,

  @Expose
  @SerializedName("name")
  val name: String? = null
)
