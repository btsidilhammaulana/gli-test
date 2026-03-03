package com.gli.model.response.credit

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class CreditModel(
  @Expose
  @SerializedName("adult")
  val adult: Boolean? = null,

  @Expose
  @SerializedName("gender")
  val gender: Long? = null,

  @Expose
  @SerializedName("id")
  val id: Int? = null,

  @Expose
  @SerializedName("known_for_department")
  val knownForDepartment: String? = null,

  @Expose
  @SerializedName("name")
  val name: String? = null,

  @Expose
  @SerializedName("original_name")
  val originalName: String? = null,

  @Expose
  @SerializedName("popularity")
  val popularity: Double? = null,

  @Expose
  @SerializedName("profile_path")
  val profilePath: String? = null,

  @Expose
  @SerializedName("cast_id")
  val castId: Int? = null,

  @Expose
  @SerializedName("character")
  val character: String? = null,

  @Expose
  @SerializedName("credit_id")
  val creditId: String? = null,

  @Expose
  @SerializedName("order")
  val order: Int? = null
) : Parcelable