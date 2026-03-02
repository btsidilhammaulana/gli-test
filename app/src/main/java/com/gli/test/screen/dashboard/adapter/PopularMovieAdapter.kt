package com.gli.test.screen.dashboard.adapter

import android.widget.ImageView
import coil3.load
import com.gli.model.constant.ImageQuality
import com.gli.model.extension.StringExtensions.toImageUrl
import com.gli.model.response.movie.MovieModel
import com.gli.test.R
import com.zhpan.bannerview.BaseBannerAdapter
import com.zhpan.bannerview.BaseViewHolder

class PopularMovieAdapter : BaseBannerAdapter<MovieModel>() {

  override fun bindData(
    holder: BaseViewHolder<MovieModel>?,
    data: MovieModel?,
    position: Int,
    pageSize: Int
  ) {
    val imageView = holder?.findViewById<ImageView>(R.id.iv_poster)
    imageView?.load(data?.backdropPath?.toImageUrl(imageQuality = ImageQuality.W780))
  }

  override fun getLayoutId(viewType: Int): Int {
    return R.layout.item_popular_movie
  }
}