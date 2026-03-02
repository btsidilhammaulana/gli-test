package com.gli.data.repository

import com.gli.model.adapter.NetworkError
import com.gli.model.adapter.NetworkResponse
import com.gli.model.constant.api.MovieApiConstant
import com.gli.model.response.base.BaseListModel
import com.gli.model.response.credit.CreditResponseModel
import com.gli.model.response.movie.MovieModel
import com.gli.model.response.review.ReviewModel
import com.gli.model.response.video.VideoModel
import com.gli.network.api.ApiServices
import com.gli.network.extension.JsonExtensions.toFlow
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class MovieRepositoryImpl @Inject constructor(
  private val apiServices: ApiServices,
) : MovieRepository {
  override suspend fun discoverMovie(page: Int): Flow<NetworkResponse<BaseListModel<MovieModel>>> {
    return try {
      return apiServices.get(
        url = MovieApiConstant.DISCOVER_MOVIE,
        queryMap = hashMapOf(
          "page" to page.toString()
        )
      ).toFlow()
    } catch (e: Throwable) {
      flowOf(NetworkResponse.Error(NetworkError.UnexpectedError(e)))
    }
  }

  override suspend fun popularMovie(): Flow<NetworkResponse<BaseListModel<MovieModel>>> {
    return try {
      return apiServices.get(
        url = MovieApiConstant.POPULAR_MOVIE,
      ).toFlow()
    } catch (e: Throwable) {
      flowOf(NetworkResponse.Error(NetworkError.UnexpectedError(e)))
    }
  }

  override suspend fun detailMovie(movieId: Int): Flow<NetworkResponse<MovieModel>> {
    return try {
      return apiServices.get(
        url = MovieApiConstant.DETAIL_MOVIE.replace("{movie_id}", movieId.toString()),
      ).toFlow()
    } catch (e: Throwable) {
      flowOf(NetworkResponse.Error(NetworkError.UnexpectedError(e)))
    }
  }

  override suspend fun videos(movieId: Int): Flow<NetworkResponse<BaseListModel<VideoModel>>> {
    return try {
      return apiServices.get(
        url = MovieApiConstant.VIDEO_MOVIE.replace("{movie_id}", movieId.toString()),
      ).toFlow()
    } catch (e: Throwable) {
      flowOf(NetworkResponse.Error(NetworkError.UnexpectedError(e)))
    }
  }

  override suspend fun credit(movieId: Int): Flow<NetworkResponse<CreditResponseModel>> {
    return try {
      return apiServices.get(
        url = MovieApiConstant.CREDIT_MOVIE.replace("{movie_id}", movieId.toString()),
      ).toFlow()
    } catch (e: Throwable) {
      flowOf(NetworkResponse.Error(NetworkError.UnexpectedError(e)))
    }
  }

  override suspend fun review(movieId: Int): Flow<NetworkResponse<BaseListModel<ReviewModel>>> {
    return try {
      return apiServices.get(
        url = MovieApiConstant.REVIEW_MOVIE.replace("{movie_id}", movieId.toString()),
      ).toFlow()
    } catch (e: Throwable) {
      flowOf(NetworkResponse.Error(NetworkError.UnexpectedError(e)))
    }
  }

  override suspend fun similar(movieId: Int): Flow<NetworkResponse<BaseListModel<MovieModel>>> {
    return try {
      return apiServices.get(
        url = MovieApiConstant.SIMILAR_MOVIE.replace("{movie_id}", movieId.toString()),
      ).toFlow()
    } catch (e: Throwable) {
      flowOf(NetworkResponse.Error(NetworkError.UnexpectedError(e)))
    }
  }

  override suspend fun searchMovie(
    query: String,
    page: Int
  ): Flow<NetworkResponse<BaseListModel<MovieModel>>> {
    return try {
      return apiServices.get(
        url = MovieApiConstant.SEARCH_MOVIE,
        queryMap = hashMapOf(
          "query" to query,
          "page" to page.toString()
        )
      ).toFlow()
    } catch (e: Throwable) {
      flowOf(NetworkResponse.Error(NetworkError.UnexpectedError(e)))
    }
  }
}