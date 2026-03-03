package com.gli.model.response.review

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ReviewModel(
  @Expose
  @SerializedName("author")
  val author: String? = null,

  @Expose
  @SerializedName("author_details")
  val authorDetails: AuthorModel? = null,

  @Expose
  @SerializedName("content")
  val content: String? = null,

  @Expose
  @SerializedName("created_at")
  val createdAt: String? = null,

  @Expose
  @SerializedName("id")
  val id: String? = null,

  @Expose
  @SerializedName("updated_at")
  val updatedAt: String? = null,

  @Expose
  @SerializedName("url")
  val url: String? = null,

  var isExpanded: Boolean = false
)
