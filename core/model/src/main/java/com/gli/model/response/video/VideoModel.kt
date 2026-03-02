package com.gli.model.response.video

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class VideoModel (
  @Expose
  @SerializedName("name")
  val name: String? = null,

  @Expose
  @SerializedName("key")
  val key: String? = null,

  @Expose
  @SerializedName("site")
  val site: String? = null,

  @Expose
  @SerializedName("size")
  val size: Long? = null,

  @Expose
  @SerializedName("type")
  val type: String? = null,

  @Expose
  @SerializedName("official")
  val official: Boolean? = null,

  @Expose
  @SerializedName("published_at")
  val publishedAt: String? = null,

  @Expose
  @SerializedName("id")
  val id: String? = null
)