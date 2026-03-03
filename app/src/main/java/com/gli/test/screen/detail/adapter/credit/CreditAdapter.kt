package com.gli.test.screen.detail.adapter.credit

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gli.model.response.credit.CreditItem
import com.gli.test.databinding.ItemCastBinding
import com.gli.test.databinding.ItemCastMoreBinding

class CreditAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

  private val items: ArrayList<CreditItem> = arrayListOf()

  companion object {
    const val VIEW_TYPE_DATA = 1
    const val VIEW_TYPE_MORE = 2
  }

  override fun getItemViewType(position: Int): Int {
    return when (items[position]) {
      is CreditItem.DataItem -> VIEW_TYPE_DATA
      is CreditItem.MoreItem -> VIEW_TYPE_MORE
    }
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
    val inflater = LayoutInflater.from(parent.context)

    return when (viewType) {
      VIEW_TYPE_MORE -> {
        val binding = ItemCastMoreBinding.inflate(inflater, parent, false)
        CreditMoreViewHolder(binding)
      }

      else -> {
        val binding = ItemCastBinding.inflate(inflater, parent, false)
        CreditViewHolder(binding)
      }
    }
  }

  override fun getItemCount(): Int = items.size

  override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
    when (val item = items[position]) {
      is CreditItem.DataItem -> (holder as CreditViewHolder).bind(item.data)
      is CreditItem.MoreItem -> (holder as CreditMoreViewHolder).bind(item.remainingCount)
    }
  }

  fun setItems(newList: List<CreditItem>) {
    items.clear()
    items.addAll(newList)
    notifyItemRangeChanged(0, newList.size)
  }
}