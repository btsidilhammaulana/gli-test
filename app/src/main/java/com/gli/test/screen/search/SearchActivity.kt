package com.gli.test.screen.search

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.gli.custom.shimmer.adapter.ShimmerAdapter
import com.gli.model.response.movie.MovieModel
import com.gli.test.R
import com.gli.test.base.BaseActivity
import com.gli.test.databinding.ActivitySearchBinding
import com.gli.test.screen.dashboard.adapter.DiscoverMovieAdapter
import com.gli.test.screen.detail.DetailMovieActivity
import com.gli.test.util.extension.ContextExtensions.getDimenSizeResource
import com.gli.test.util.extension.ToolbarExtensions.setAsActionBar
import com.gli.test.util.grid.GridItemDecoration
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlin.getValue

@AndroidEntryPoint
class SearchActivity : BaseActivity<ActivitySearchBinding>() {

  private val viewModel: SearchViewModel by viewModels()

  private val movieAdapter: DiscoverMovieAdapter by lazy {
    val adapter = DiscoverMovieAdapter()
    adapter.addOnItemClickListener(object : DiscoverMovieAdapter.ItemClickListener {
      override fun onItemClicked(movie: MovieModel) {
        movie.id?.let { id -> movie.title?.let { title -> navigateToDetail(id, title) } }
      }
    })
    adapter
  }

  private val shimmerMovieAdapter: ShimmerAdapter by lazy {
    ShimmerAdapter(R.layout.shimmer_item_movie, 8)
  }

  override fun setLayout(inflater: LayoutInflater): ActivitySearchBinding {
    return ActivitySearchBinding.inflate(inflater)
  }

  override fun initialization() {
    setupWindowTopInset(binding.root)
    setupView()
  }

  override fun onResume() {
    super.onResume()
  }

  /**
   * Setup View
   */
  private fun setupView() {
    setupToolbar()
    setupSwipeRefresh()
    setupRecyclerView()
    showEmptyQuery()

    movieAdapter.addLoadStateListener {
      onLoadStateListener(it)
    }
  }

  private fun onLoadStateListener(state: CombinedLoadStates) = lifecycleScope.launch {
    when (state.refresh) {
      is LoadState.Loading -> {
        if (movieAdapter.itemCount == 0) {
          showShimmerView()
        }
      }

      is LoadState.NotLoading -> {
        if (binding.tfSearch.getText().isBlank()) {
          showEmptyQuery()
          return@launch
        }

        if (movieAdapter.itemCount > 0) {
          showDataView()
          return@launch
        }

        showEmptyMovie()
      }

      is LoadState.Error -> showShimmerView()
    }
  }

  /**
   * Setup toolbar
   */
  private fun setupToolbar() {
    setAsActionBar(binding.toolbar)
    binding.tfSearch.run {
      requestFocus()
      addTextChangedListener {
        searchMovie(it.toString())
      }
    }
  }

  /**
   * Setup Swipe Refresh
   */
  private fun setupSwipeRefresh() {
    binding.swipeRefresh.setOnRefreshListener {
      setRefresh()
    }
  }

  /**
   * Setup Data When Refresh
   */
  private fun setRefresh() {
    binding.tfSearch.run {
      searchMovie(getText())
    }
  }

  /**
   * Setup Recycler View
   */
  private fun setupRecyclerView() {
    binding.rvMovie.run {
      layoutManager = GridLayoutManager(context, 2)
      adapter = movieAdapter
      isNestedScrollingEnabled = true

      addItemDecoration(GridItemDecoration(2, getDimenSizeResource(R.dimen.spacing), true))
    }

    binding.rvShimmer.run {
      layoutManager = GridLayoutManager(context, 2)
      adapter = shimmerMovieAdapter
      isNestedScrollingEnabled = true

      addItemDecoration(GridItemDecoration(2, getDimenSizeResource(R.dimen.spacing), true))
    }
  }

  private fun searchMovie(query: String) {
    lifecycleScope.launch {
      viewModel.searchMovie(query).collectLatest {
        movieAdapter.submitData(it)
      }
    }
  }

  private fun showDataView() {
    binding.swipeRefresh.isRefreshing = false
    binding.vaMovie.displayedChild = 0
  }

  private fun showEmptyMovie() {
    binding.swipeRefresh.isRefreshing = false
    binding.vaMovie.displayedChild = 1
  }

  private fun showEmptyQuery() {
    binding.swipeRefresh.isRefreshing = false
    binding.vaMovie.displayedChild = 2
  }

  private fun showShimmerView() {
    binding.swipeRefresh.isRefreshing = false
    binding.vaMovie.displayedChild = 3
  }

  /**
   * Navigation
   */
  private fun navigateToDetail(movieId: Int, movieTitle: String) {
    startActivity(DetailMovieActivity.newIntent(this, movieId, movieTitle))
  }

  companion object {
    fun newIntent(context: Context?): Intent {
      return Intent(context, SearchActivity::class.java)
    }
  }
}