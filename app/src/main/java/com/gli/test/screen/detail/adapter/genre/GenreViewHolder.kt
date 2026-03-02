package com.gli.test.screen.detail.adapter.genre

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.gli.model.response.genre.GenreModel
import com.gli.test.databinding.ItemGenreBinding

class GenreViewHolder(
  private val binding: ItemGenreBinding
) : ViewHolder(binding.root) {
  fun bind(genre: GenreModel) = with(genre) {
    binding.run {
      tvGenre.text = name
    }
  }
}