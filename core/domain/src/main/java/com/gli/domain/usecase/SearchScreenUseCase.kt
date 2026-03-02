package com.gli.domain.usecase

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.gli.data.repository.MovieRepository
import com.gli.domain.pagingsource.SearchMoviePagingSource
import com.gli.model.response.movie.MovieModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchScreenUseCase @Inject constructor(
  private val movieRepository: MovieRepository
) {
  fun searchMovie(query: String): Flow<PagingData<MovieModel>> {
    return Pager(config = PagingConfig(20)){
      SearchMoviePagingSource(query, movieRepository)
    }.flow
  }
}