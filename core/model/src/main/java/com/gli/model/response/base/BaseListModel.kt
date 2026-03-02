package com.gli.model.response.base

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class BaseListModel<T>(
  @Expose
  @SerializedName("results")
  val items: List<T>? = null,

  @Expose
  @SerializedName("page")
  val currentPage: Int = 0,

  @Expose
  @SerializedName("total_pages")
  val totalPages: Int = 0,

  @Expose
  @SerializedName("total_results")
  val totalItems: Int = 0,
)