package com.gli.test.screen.dashboard.adapter

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil3.load
import com.gli.model.constant.ImageQuality
import com.gli.model.extension.StringExtensions.toImageUrl
import com.gli.model.response.movie.MovieModel
import com.gli.test.databinding.ItemPopularMovieBinding

class PopularMovieViewHolder(
  private val binding: ItemPopularMovieBinding
) : ViewHolder(binding.root) {
  fun bind(movie: MovieModel) = with(movie) {
    binding.run {
      ivPoster.load(backdropPath.toImageUrl(ImageQuality.W780))
    }
  }
}