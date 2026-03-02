package com.gli.test.screen.detail.adapter.genre

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gli.model.response.genre.GenreModel
import com.gli.test.databinding.ItemGenreBinding

class GenreAdapter : RecyclerView.Adapter<GenreViewHolder>() {
  private val items: ArrayList<GenreModel> = arrayListOf()

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder {
    val inflater = LayoutInflater.from(parent.context)
    val binding = ItemGenreBinding.inflate(inflater, parent, false)
    return GenreViewHolder(binding)
  }

  override fun getItemCount(): Int = items.size

  override fun onBindViewHolder(holder: GenreViewHolder, position: Int) {
    holder.bind(items[position])
  }

  fun setItems(filteredList: List<GenreModel>) {
    val oldSize = items.size
    items.clear()
    notifyItemRangeRemoved(0, oldSize)
    items.addAll(filteredList)
    notifyItemRangeInserted(oldSize, items.size)
  }
}
