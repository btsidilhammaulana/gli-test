package com.gli.test.screen.detail

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import coil3.load
import coil3.request.transformations
import coil3.transform.RoundedCornersTransformation
import com.gli.model.adapter.NetworkError
import com.gli.model.adapter.error
import com.gli.model.adapter.loading
import com.gli.model.adapter.success
import com.gli.model.constant.ImageQuality
import com.gli.model.extension.DateExtension.toFormattedDate
import com.gli.model.extension.StringExtensions.toImageUrl
import com.gli.model.response.base.BaseListModel
import com.gli.model.response.credit.CreditModel
import com.gli.model.response.credit.CreditResponseModel
import com.gli.model.response.movie.MovieModel
import com.gli.model.response.review.ReviewModel
import com.gli.model.response.video.VideoModel
import com.gli.test.R
import com.gli.test.base.BaseActivity
import com.gli.test.databinding.ActivityDetailMovieBinding
import com.gli.test.screen.detail.adapter.credit.CreditAdapter
import com.gli.test.screen.detail.adapter.genre.GenreAdapter
import com.gli.test.screen.detail.adapter.review.ReviewAdapter
import com.gli.test.screen.detail.adapter.similar.SimilarAdapter
import com.gli.test.util.extension.ContextExtensions.getDimenSizeResource
import com.gli.test.util.extension.ToolbarExtensions.setAsActionBar
import com.gli.test.util.grid.GridItemDecoration
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailMovieActivity : BaseActivity<ActivityDetailMovieBinding>() {

  private val viewModel: DetailMovieViewModel by viewModels()

  private val genreAdapter: GenreAdapter by lazy {
    GenreAdapter()
  }

  private val creditAdapter: CreditAdapter by lazy {
    CreditAdapter()
  }

  private val reviewAdapter: ReviewAdapter by lazy {
    ReviewAdapter()
  }

  private val similarAdapter: SimilarAdapter by lazy {
    val adapter = SimilarAdapter()
    adapter.addOnItemClickListener(object : SimilarAdapter.ItemClickListener {
      override fun onItemClicked(movie: MovieModel) {
        movie.id?.let { movie.title?.let { it1 -> navigateToDetail(it, it1) } }
      }
    })
    adapter
  }

  override fun setLayout(inflater: LayoutInflater): ActivityDetailMovieBinding {
    return ActivityDetailMovieBinding.inflate(inflater)
  }

  override fun initialization() {
    loadExtra()
    setupView()
    setupObserver()
  }

  private fun loadExtra() {
    viewModel.movieId = intent.getIntExtra(EXTRA_MOVIE_ID, 0)
    viewModel.movieTitle = intent.getStringExtra(EXTRA_MOVIE_TITLE)
  }

  /**
   * Setup View
   */
  private fun setupView() {
    setupWindowTopInset(binding.root)
    setupToolbar()
    setupFloatingActionButton()
    setupGenreRecyclerView()
    setupCreditRecyclerView()
    setupReviewRecyclerView()
    setupSimilarRecyclerView()

    viewModel.getDetailMovie()
    viewModel.getVideo()
    viewModel.getCredit()
    viewModel.getReview()
    viewModel.getSimilar()
  }

  /**
   * Setup Toolbar
   */
  private fun setupToolbar() {
    setAsActionBar(binding.toolbar)
    binding.toolbar.title = viewModel.movieTitle
  }

  /**
   * Setup Floating Action Button
   */
  private fun setupFloatingActionButton() {
    binding.fabToTop.setOnClickListener {
      binding.nsDetail.smoothScrollTo(0, 0)
    }
  }

  /**
   * Setup Genre Recycler View
   */
  private fun setupGenreRecyclerView() {
    binding.contentDetail.rvGenre.run {
      layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
      adapter = genreAdapter
      isEnabled = false
      isNestedScrollingEnabled = true
    }
  }

  /**
   * Setup Credit Recycler View
   */
  private fun setupCreditRecyclerView() {
    binding.contentDetail.rvCredit.run {
      layoutManager = GridLayoutManager(context, 2, GridLayoutManager.HORIZONTAL, false)
      adapter = creditAdapter
      isNestedScrollingEnabled = true
      clipToPadding = false
      clipChildren = false
    }
  }

  /**
   * Setup Review Recycler View
   */
  private fun setupReviewRecyclerView() {
    binding.contentDetail.rvReview.run {
      layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
      adapter = reviewAdapter
      isNestedScrollingEnabled = false
      clipToPadding = false
      clipChildren = false
    }
  }

  /**
   * Setup Similar Recycler View
   */
  private fun setupSimilarRecyclerView() {
    binding.contentDetail.rvSimilar.run {
      layoutManager = GridLayoutManager(context, 2)
      adapter = similarAdapter
      isNestedScrollingEnabled = false
      clipToPadding = false
      clipChildren = false
      addItemDecoration(GridItemDecoration(2, getDimenSizeResource(R.dimen.spacing), true))
    }
  }

  /**
   * Setup Observer
   */
  private fun setupObserver() {
    observeDetailMovieState()
    observeMovieTrailerState()
    observeOnGetCreditState()
    observeGetReviewState()
    observeGetSimilarState()
  }

  /**
   * Observe get detail movie state
   */
  private fun observeDetailMovieState() {
    viewModel.getDetailMovieState.observe(this) { result ->
      result.loading { onGetMovieLoading() }

      result.success { onGetMovieSuccess(it) }

      result.error { onError(it) }
    }
  }

  private fun onGetMovieLoading() {
    binding.vaContent.displayedChild = 1
  }

  private fun onGetMovieSuccess(movie: MovieModel?) {
    binding.vaContent.displayedChild = 0

    binding.contentDetail.ivPoster.load(movie?.posterPath.toImageUrl(ImageQuality.W500)) {
      transformations(RoundedCornersTransformation(32f))
    }

    binding.contentDetail.tvTitle.text = movie?.title
    binding.contentDetail.tvReleaseDate.text = movie?.releaseDate?.toFormattedDate("dd MMMM yyyy")
    binding.contentDetail.tvRating.text = "${movie?.voteAverage}"
    binding.contentDetail.tvPopularity.text = getString(R.string.popularity_movie, movie?.popularity.toString())

    movie?.genres?.let { genreAdapter.setItems(it) }

    binding.contentDetail.tvOverview.text = movie?.overview
  }

  /**
   * Observe get trailer movie state
   */
  private fun observeMovieTrailerState() {
    viewModel.getMovieTrailerState.observe(this) { result ->
      result.success { onGetTrailerSuccess(it) }

      result.error { onError(it) }
    }
  }

  private fun onGetTrailerSuccess(items: BaseListModel<VideoModel>) {
    if (items.items.isNullOrEmpty()) return

    for (video in items.items as List<VideoModel>) {
      if (video.type == "Trailer") {
        lifecycle.addObserver(binding.contentDetail.youtubePlayer)
        binding.contentDetail.youtubePlayer.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
          override fun onReady(youTubePlayer: YouTubePlayer) {
            video.key?.let { youTubePlayer.cueVideo(it, 0f) }
          }
        })
        break
      }
    }
  }

  /**
   * Observe get credit movie state
   */
  private fun observeOnGetCreditState() {
    viewModel.getCreditState.observe(this) { result ->
      result.success { onGetCreditSuccess(it) }

      result.error { onError(it) }
    }
  }

  private fun onGetCreditSuccess(credits: CreditResponseModel) {
    if (credits.cast.isNullOrEmpty()) return

    creditAdapter.setItems(credits.cast as List<CreditModel>)
  }

  /**
   * Observe get review movie state
   */
  private fun observeGetReviewState() {
    viewModel.getReviewState.observe(this) { result ->
      result.success { onGetReviewSuccess(it) }

      result.error { onError(it) }
    }
  }

  private fun onGetReviewSuccess(reviews: BaseListModel<ReviewModel>) {
    if (reviews.items.isNullOrEmpty()) {
      binding.contentDetail.vaReview.displayedChild = 1
      return
    }
    binding.contentDetail.vaReview.displayedChild = 0
    reviewAdapter.setItems(reviews.items?.take(5) as List<ReviewModel>)
  }

  /**
   * Observe get similar movie state
   */
  private fun observeGetSimilarState() {
    viewModel.getSimilarState.observe(this) { result ->
      result.success { onGetSimilarSuccess(it.items) }

      result.error { onError(it) }
    }
  }

  private fun onGetSimilarSuccess(items: List<MovieModel>?) {
    if (items.isNullOrEmpty()) {
      binding.contentDetail.vaSimilar.displayedChild = 1
      return
    }
    binding.contentDetail.vaSimilar.displayedChild = 0
    similarAdapter.setItems(items)
  }

  private fun onError(error: NetworkError) {
    errorHandler.showError(error)
  }

  /**
   * Navigation
   */
  private fun navigateToDetail(movieId: Int, movieTitle: String) {
    startActivity(newIntent(this, movieId, movieTitle))
  }


  companion object {
    private const val EXTRA_MOVIE_ID = "EXTRA_MOVIE_ID"
    private const val EXTRA_MOVIE_TITLE = "EXTRA_MOVIE_TITLE"

    fun newIntent(context: Context?, movieId: Int, movieTitle: String): Intent {
      return Intent(context, DetailMovieActivity::class.java).apply {
        putExtra(EXTRA_MOVIE_ID, movieId)
        putExtra(EXTRA_MOVIE_TITLE, movieTitle)
      }
    }
  }
}