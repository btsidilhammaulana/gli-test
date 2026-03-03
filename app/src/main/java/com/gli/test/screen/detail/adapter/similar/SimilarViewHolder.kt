package com.gli.test.screen.detail.adapter.similar

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil3.load
import coil3.request.error
import coil3.request.fallback
import coil3.request.placeholder
import coil3.request.transformations
import coil3.transform.RoundedCornersTransformation
import com.gli.model.constant.ImageQuality
import com.gli.model.extension.StringExtensions.toImageUrl
import com.gli.model.response.movie.MovieModel
import com.gli.test.R
import com.gli.test.databinding.ItemMovieBinding

class SimilarViewHolder(
  private val binding: ItemMovieBinding
) : ViewHolder(binding.root) {
  fun bind(movie: MovieModel) = with(movie) {
    binding.run {
      ivPoster.load(posterPath.toImageUrl(ImageQuality.W500)) {
        placeholder(R.drawable.img_placeholder)
        error(R.drawable.img_placeholder)
        fallback(R.drawable.img_placeholder)
        transformations(RoundedCornersTransformation(32f))
      }
    }
  }
}