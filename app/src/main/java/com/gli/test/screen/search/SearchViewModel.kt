package com.gli.test.screen.search

import androidx.paging.PagingData
import com.gli.domain.usecase.SearchScreenUseCase
import com.gli.model.response.movie.MovieModel
import com.gli.test.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
  private val searchScreenUseCase: SearchScreenUseCase
) : BaseViewModel() {

  fun searchMovie(query: String): Flow<PagingData<MovieModel>> {
    return searchScreenUseCase.searchMovie(query)
  }
}