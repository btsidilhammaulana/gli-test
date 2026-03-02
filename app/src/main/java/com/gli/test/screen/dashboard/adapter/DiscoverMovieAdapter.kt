package com.gli.test.screen.dashboard.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import com.gli.model.response.movie.MovieModel
import com.gli.test.databinding.ItemMovieBinding

class DiscoverMovieAdapter : PagingDataAdapter<MovieModel, DiscoverMovieViewHolder>(MovieDiffCallback()) {
  private var listener: ItemClickListener? = null

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiscoverMovieViewHolder {
    val inflater = LayoutInflater.from(parent.context)
    val binding = ItemMovieBinding.inflate(inflater, parent, false)
    return DiscoverMovieViewHolder(binding)
  }

  override fun onBindViewHolder(holder: DiscoverMovieViewHolder, position: Int) {
    val movie = getItem(position)
    movie?.let { mov ->
      holder.bind(mov)
      holder.itemView.setOnClickListener {
        listener?.onItemClicked(mov)
      }
    }
  }

  fun addOnItemClickListener(listener: ItemClickListener) {
    this.listener = listener
  }

  interface ItemClickListener {
    fun onItemClicked(movie: MovieModel)
  }
}
