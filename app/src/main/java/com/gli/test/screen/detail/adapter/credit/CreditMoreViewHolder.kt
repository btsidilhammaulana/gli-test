package com.gli.test.screen.detail.adapter.credit

import androidx.recyclerview.widget.RecyclerView
import com.gli.test.R
import com.gli.test.databinding.ItemCastMoreBinding

class CreditMoreViewHolder(
  private val binding: ItemCastMoreBinding
) : RecyclerView.ViewHolder(binding.root) {
  fun bind(count: Int) {
    val context = binding.root.context
    binding.tvMoreCast.text = context.getString(R.string.more_cast_value, count)
  }
}