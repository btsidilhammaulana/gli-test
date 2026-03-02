package com.gli.domain.pagingsource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.gli.data.repository.MovieRepository
import com.gli.model.adapter.success
import com.gli.model.response.movie.MovieModel
import kotlinx.coroutines.flow.collectLatest

class DiscoverMoviePagingSource(
  private val movieRepository: MovieRepository
) : PagingSource<Int, MovieModel>() {
  override fun getRefreshKey(state: PagingState<Int, MovieModel>): Int? {
    return state.anchorPosition
  }

  override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieModel> {
    val position = params.key ?: 1
    return try {
      val data = arrayListOf<MovieModel>()
      var totalPages = 0

      movieRepository.discoverMovie(position).collectLatest { result ->
        result.success {
          data.addAll(it.items as List<MovieModel>)
          totalPages = it.totalPages
        }
      }

      LoadResult.Page(
        data = data,
        prevKey = if (position == 1) null else position - 1,
        nextKey = if(totalPages != position) position + 1 else null
      )
    } catch (e: Exception) {
      LoadResult.Error(e)
    }
  }
}