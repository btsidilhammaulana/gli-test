package com.gli.test.screen.dashboard

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.gli.custom.shimmer.shimmer.adapter.ShimmerAdapter
import com.gli.model.adapter.NetworkError
import com.gli.model.adapter.error
import com.gli.model.adapter.success
import com.gli.model.response.movie.MovieModel
import com.gli.test.R
import com.gli.test.base.BaseActivity
import com.gli.test.databinding.ActivityDashboardBinding
import com.gli.test.screen.dashboard.adapter.DiscoverMovieAdapter
import com.gli.test.screen.dashboard.adapter.PopularMovieAdapter
import com.gli.test.util.extension.ContextExtensions.getDimenSizeResource
import com.gli.test.util.grid.GridItemDecoration
import com.zhpan.bannerview.BaseBannerAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DashboardActivity: BaseActivity<ActivityDashboardBinding>() {

  private val viewModel: DashboardViewModel by viewModels()

  private val movieAdapter: DiscoverMovieAdapter by lazy {
    val adapter = DiscoverMovieAdapter()
    adapter.addOnItemClickListener(object : DiscoverMovieAdapter.ItemClickListener {
      override fun onItemClicked(movie: MovieModel) { }
    })
    adapter
  }

  private val shimmerMovieAdapter: ShimmerAdapter by lazy {
    ShimmerAdapter(R.layout.shimmer_item_movie, 8)
  }

  private val popularMovieAdapter: PopularMovieAdapter by lazy {
    PopularMovieAdapter()
  }

  override fun setLayout(inflater: LayoutInflater): ActivityDashboardBinding {
    return ActivityDashboardBinding.inflate(layoutInflater)
  }

  override fun initialization() {
    setupView()
    setupObserver()
  }

  /**
   * Setup View
   */
  private fun setupView() {
    setupWindowTopInset(binding.root)
    isFullscreenActivity()
    setupToolbar()
    setupSwipeRefresh()
    setupRecyclerView()
    movieAdapter.addLoadStateListener {
      onLoadStateListener(it)
    }
    showShimmerView()
  }

  private fun onLoadStateListener(state: CombinedLoadStates) = lifecycleScope.launch {
    when (state.refresh) {
      is LoadState.Loading -> {
        /*No Implementation*/
      }
      is LoadState.NotLoading -> showDataView()
      is LoadState.Error -> showShimmerView()
    }
  }

  /**
   * Setup Toolbar
   */
  private fun setupToolbar() {
    binding.tfSearch.setOnClickListener {

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
    showShimmerView()
    viewModel.getPopularMovie()
    observeDiscoverMovie()
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

  /**
   * Setup Observer
   */
  private fun setupObserver() {
    observeDiscoverMovie()
    observePopularMovieState()
    viewModel.getPopularMovie()
  }

  /**
   * Observe get discover movie state
   */
  private fun observeDiscoverMovie() {
    lifecycleScope.launch {
      viewModel.getDiscoverMovie().collectLatest {
        movieAdapter.submitData(it)
      }
    }
  }

  private fun showShimmerView() {
    binding.vaToolbar.displayedChild = 1
    binding.vaMovie.displayedChild = 1
  }

  private fun showDataView() {
    binding.vaToolbar.displayedChild = 0
    binding.vaMovie.displayedChild = 0
  }

  /**
   * Observe get popular movie state
   */
  private fun observePopularMovieState() {
    viewModel.getPopularMovieState.observe(this) { result ->
      result.success { onGetPopularMovieSuccess(it.items) }

      result.error { onGetPopularMovieFailed(it) }
    }
  }

  private fun onGetPopularMovieSuccess(items: List<MovieModel>?) {
    binding.swipeRefresh.isRefreshing = false
    if (items.isNullOrEmpty()) return
    binding.vpPopular.run {
      adapter = popularMovieAdapter as BaseBannerAdapter<Any>
      create(items.take(5))
    }
  }

  private fun onGetPopularMovieFailed(error: NetworkError) {
    binding.swipeRefresh.isRefreshing = false
    errorHandler.showError(error)
  }

  companion object {
    fun newIntent(context: Context?): Intent {
      return Intent(context, DashboardActivity::class.java).apply {
        flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
      }
    }
  }
}