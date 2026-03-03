package com.gli.test.screen.detail.adapter.review

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gli.model.response.review.ReviewModel
import com.gli.test.databinding.ItemReviewBinding

class ReviewAdapter : RecyclerView.Adapter<ReviewViewHolder>() {
  private val items: ArrayList<ReviewModel> = arrayListOf()

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
    val inflater = LayoutInflater.from(parent.context)
    val binding = ItemReviewBinding.inflate(inflater, parent, false)
    return ReviewViewHolder(binding)
  }

  override fun getItemCount(): Int = items.size

  override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
    holder.bind(items[position]) {
      notifyItemChanged(position)
    }
  }

  fun setItems(filteredList: List<ReviewModel>) {
    val oldSize = items.size
    items.clear()
    notifyItemRangeRemoved(0, oldSize)
    items.addAll(filteredList)
    notifyItemRangeInserted(oldSize, items.size)
  }
}