package com.gli.test.screen.dashboard

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.gli.domain.usecase.DashboardScreenUseCase
import com.gli.model.adapter.NetworkResponse
import com.gli.model.response.base.BaseListModel
import com.gli.model.response.movie.MovieModel
import com.gli.model.ui.UiState
import com.gli.test.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
  private val dashboardScreenUseCase: DashboardScreenUseCase
) : BaseViewModel() {

  val getPopularMovieState = UiState<NetworkResponse<BaseListModel<MovieModel>>>(NetworkResponse.Loading)

  fun getPopularMovie() = viewModelScope.launch {
    dashboardScreenUseCase.getPopularMovie().collectLatest {
      getPopularMovieState.postValue(it)
    }
  }

  fun getDiscoverMovie() : Flow<PagingData<MovieModel>> {
    return dashboardScreenUseCase.getDiscoverMovie()
  }
}