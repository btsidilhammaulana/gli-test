package com.gli.domain.usecase

import com.gli.data.repository.MovieRepository
import com.gli.model.adapter.NetworkResponse
import com.gli.model.response.base.BaseListModel
import com.gli.model.response.credit.CreditResponseModel
import com.gli.model.response.movie.MovieModel
import com.gli.model.response.review.ReviewModel
import com.gli.model.response.video.VideoModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DetailMovieScreenUseCase @Inject constructor(
  private val movieRepository: MovieRepository
) {
  suspend fun getDetailMovie(movieId: Int): Flow<NetworkResponse<MovieModel>> {
    return movieRepository.detailMovie(movieId)
  }

  suspend fun getVideo(movieId: Int): Flow<NetworkResponse<BaseListModel<VideoModel>>> {
    return movieRepository.videos(movieId)
  }

  suspend fun getCredit(movieId: Int): Flow<NetworkResponse<CreditResponseModel>> {
    return movieRepository.credit(movieId)
  }

  suspend fun getReview(movieId: Int): Flow<NetworkResponse<BaseListModel<ReviewModel>>> {
    return movieRepository.review(movieId)
  }

  suspend fun getSimilar(movieId: Int): Flow<NetworkResponse<BaseListModel<MovieModel>>> {
    return movieRepository.similar(movieId)
  }
}