package com.gli.data.repository

import com.gli.model.adapter.NetworkResponse
import com.gli.model.response.base.BaseListModel
import com.gli.model.response.credit.CreditResponseModel
import com.gli.model.response.movie.MovieModel
import com.gli.model.response.review.ReviewModel
import com.gli.model.response.video.VideoModel
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
  suspend fun discoverMovie(page: Int): Flow<NetworkResponse<BaseListModel<MovieModel>>>

  suspend fun popularMovie(): Flow<NetworkResponse<BaseListModel<MovieModel>>>

  suspend fun detailMovie(movieId: Int): Flow<NetworkResponse<MovieModel>>

  suspend fun videos(movieId: Int): Flow<NetworkResponse<BaseListModel<VideoModel>>>

  suspend fun credit(movieId: Int): Flow<NetworkResponse<CreditResponseModel>>

  suspend fun review(movieId: Int): Flow<NetworkResponse<BaseListModel<ReviewModel>>>

  suspend fun similar(movieId: Int): Flow<NetworkResponse<BaseListModel<MovieModel>>>

  suspend fun searchMovie(query: String, page: Int): Flow<NetworkResponse<BaseListModel<MovieModel>>>
}