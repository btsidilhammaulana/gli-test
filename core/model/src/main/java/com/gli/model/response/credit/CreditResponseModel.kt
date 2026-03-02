package com.gli.model.response.credit

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CreditResponseModel(
  @Expose
  @SerializedName("id")
  val id: Int? = null,

  @Expose
  @SerializedName("cast")
  val cast: List<CreditModel>? = null,
)
