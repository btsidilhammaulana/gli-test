package com.gli.model.response.movie

import com.gli.model.response.genre.GenreModel
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class MovieModel(
  @Expose
  @SerializedName("adult")
  val adult: Boolean? = null,

  @Expose
  @SerializedName("backdrop_path")
  val backdropPath: String? = null,

  @Expose
  @SerializedName("genres")
  val genres: List<GenreModel>? = null,

  @Expose
  @SerializedName("genre_ids")
  val genreIds: List<String>? = null,

  @Expose
  @SerializedName("id")
  val id: Int? = null,

  @Expose
  @SerializedName("original_language")
  val originalLanguage: String? = null,

  @Expose
  @SerializedName("original_title")
  val originalTitle: String? = null,

  @Expose
  @SerializedName("overview")
  val overview: String? = null,

  @Expose
  @SerializedName("popularity")
  val popularity: Double? = null,

  @Expose
  @SerializedName("poster_path")
  val posterPath: String? = null,

  @Expose
  @SerializedName("release_date")
  val releaseDate: String? = null,

  @Expose
  @SerializedName("title")
  val title: String? = null,

  @Expose
  @SerializedName("video")
  val video: Boolean? = null,

  @Expose
  @SerializedName("vote_average")
  val voteAverage: Double? = null,

  @Expose
  @SerializedName("vote_count")
  val voteCount: Int? = null
)
