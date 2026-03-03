package com.gli.test.screen.detail

import androidx.lifecycle.viewModelScope
import com.gli.domain.usecase.DetailMovieScreenUseCase
import com.gli.model.adapter.NetworkResponse
import com.gli.model.response.base.BaseListModel
import com.gli.model.response.credit.CreditModel
import com.gli.model.response.credit.CreditResponseModel
import com.gli.model.response.movie.MovieModel
import com.gli.model.response.review.ReviewModel
import com.gli.model.response.video.VideoModel
import com.gli.model.ui.UiState
import com.gli.test.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailMovieViewModel @Inject constructor(
  private val useCase: DetailMovieScreenUseCase
) : BaseViewModel() {
  var movieId: Int? = null
  var movieTitle: String? = null

  val getDetailMovieState = UiState<NetworkResponse<MovieModel>>(NetworkResponse.Loading)

  val getMovieTrailerState = UiState<NetworkResponse<BaseListModel<VideoModel>>>(NetworkResponse.Loading)

  val getCreditState = UiState<NetworkResponse<CreditResponseModel>>(NetworkResponse.Loading)

  val getReviewState = UiState<NetworkResponse<BaseListModel<ReviewModel>>>(NetworkResponse.Loading)

  val getSimilarState = UiState<NetworkResponse<BaseListModel<MovieModel>>>(NetworkResponse.Loading)

  val moreCredit : ArrayList<CreditModel> = arrayListOf()

  fun getDetailMovie() = viewModelScope.launch {
    movieId?.let { id ->
      useCase.getDetailMovie(id).collectLatest {
        getDetailMovieState.postValue(it)
      }
    }
  }

  fun getVideo() = viewModelScope.launch {
    movieId?.let { id ->
      useCase.getVideo(id).collectLatest {
        getMovieTrailerState.postValue(it)
      }
    }
  }

  fun getCredit() = viewModelScope.launch {
    movieId?.let { id ->
      useCase.getCredit(id).collectLatest {
        getCreditState.postValue(it)
      }
    }
  }

  fun getReview() = viewModelScope.launch {
    movieId?.let { id ->
      useCase.getReview(id).collectLatest {
        getReviewState.postValue(it)
      }
    }
  }

  fun getSimilar() = viewModelScope.launch {
    movieId?.let { id ->
      useCase.getSimilar(id).collectLatest {
        getSimilarState.postValue(it)
      }
    }
  }
}