package com.gli.test.screen.detail.adapter.similar

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gli.model.response.movie.MovieModel
import com.gli.test.databinding.ItemMovieBinding

class SimilarAdapter : RecyclerView.Adapter<SimilarViewHolder>() {
  private val items: ArrayList<MovieModel> = arrayListOf()
  private var listener: ItemClickListener? = null

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimilarViewHolder {
    val inflater = LayoutInflater.from(parent.context)
    val binding = ItemMovieBinding.inflate(inflater, parent, false)
    return SimilarViewHolder(binding)
  }

  override fun getItemCount(): Int = items.size

  override fun onBindViewHolder(holder: SimilarViewHolder, position: Int) {
    holder.bind(items[position])
    holder.itemView.setOnClickListener {
      listener?.onItemClicked(items[position])
    }
  }

  fun addOnItemClickListener(listener: ItemClickListener) {
    this.listener = listener
  }

  fun setItems(filteredList: List<MovieModel>) {
    val oldSize = items.size
    items.clear()
    notifyItemRangeRemoved(0, oldSize)
    items.addAll(filteredList)
    notifyItemRangeInserted(oldSize, items.size)
  }

  interface ItemClickListener {
    fun onItemClicked(movie: MovieModel)
  }
}
