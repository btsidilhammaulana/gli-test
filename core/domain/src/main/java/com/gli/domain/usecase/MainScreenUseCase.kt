package com.gli.domain.usecase

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.gli.data.repository.MovieRepository
import com.gli.domain.pagingsource.DiscoverMoviePagingSource
import com.gli.model.adapter.NetworkResponse
import com.gli.model.response.base.BaseListModel
import com.gli.model.response.movie.MovieModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MainScreenUseCase @Inject constructor(
  private val movieRepository: MovieRepository
) {
  fun getDiscoverMovie(): Flow<PagingData<MovieModel>> {
    return Pager(config = PagingConfig(20)) {
      DiscoverMoviePagingSource(movieRepository)
    }.flow
  }

  suspend fun getPopularMovie(): Flow<NetworkResponse<BaseListModel<MovieModel>>>{
    return movieRepository.popularMovie()
  }
}