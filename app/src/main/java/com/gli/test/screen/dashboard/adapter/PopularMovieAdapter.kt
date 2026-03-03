package com.gli.test.screen.dashboard.adapter

import android.widget.ImageView
import coil3.load
import coil3.request.error
import coil3.request.fallback
import coil3.request.placeholder
import com.gli.model.constant.ImageQuality
import com.gli.model.extension.StringExtensions.toImageUrl
import com.gli.model.response.movie.MovieModel
import com.gli.test.R
import com.gli.test.screen.dashboard.adapter.DiscoverMovieAdapter.ItemClickListener
import com.zhpan.bannerview.BaseBannerAdapter
import com.zhpan.bannerview.BaseViewHolder

class PopularMovieAdapter : BaseBannerAdapter<MovieModel>() {

  private var listener: ItemClickListener? = null

  override fun bindData(
    holder: BaseViewHolder<MovieModel>?,
    data: MovieModel?,
    position: Int,
    pageSize: Int
  ) {
    val imageView = holder?.findViewById<ImageView>(R.id.iv_poster)
    imageView?.load(data?.backdropPath?.toImageUrl(imageQuality = ImageQuality.W780)) {
      placeholder(R.drawable.img_placeholder)
      error(R.drawable.img_placeholder)
      fallback(R.drawable.img_placeholder)
    }
    holder?.itemView?.setOnClickListener {
      listener?.onItemClicked(data)
    }
  }

  override fun getLayoutId(viewType: Int): Int {
    return R.layout.item_popular_movie
  }

  fun addOnItemClickListener(listener: ItemClickListener) {
    this.listener = listener
  }

  interface ItemClickListener {
    fun onItemClicked(movie: MovieModel?)
  }
}