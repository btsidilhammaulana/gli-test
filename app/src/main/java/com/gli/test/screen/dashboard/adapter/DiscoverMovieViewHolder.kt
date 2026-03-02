package com.gli.test.screen.dashboard.adapter

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil3.load
import coil3.request.transformations
import coil3.transform.RoundedCornersTransformation
import com.gli.model.constant.ImageQuality
import com.gli.model.extension.StringExtensions.toImageUrl
import com.gli.model.response.movie.MovieModel
import com.gli.test.databinding.ItemMovieBinding

class DiscoverMovieViewHolder(
  private val binding: ItemMovieBinding
) : ViewHolder(binding.root) {
  fun bind(movie: MovieModel) = with(movie) {
    binding.run {
      ivPoster.load(posterPath.toImageUrl(ImageQuality.W500)) {
        transformations(RoundedCornersTransformation(32f))
      }
    }
  }
}