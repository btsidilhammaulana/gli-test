package com.gli.test.screen.detail.adapter.credit

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gli.model.response.credit.CreditModel
import com.gli.test.databinding.ItemCastBinding

class CreditAdapter : RecyclerView.Adapter<CreditViewHolder>() {
  private val items: ArrayList<CreditModel> = arrayListOf()

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CreditViewHolder {
    val inflater = LayoutInflater.from(parent.context)
    val binding = ItemCastBinding.inflate(inflater, parent, false)
    return CreditViewHolder(binding)
  }

  override fun getItemCount(): Int = items.size

  override fun onBindViewHolder(holder: CreditViewHolder, position: Int) {
    holder.bind(items[position])
  }

  fun setItems(filteredList: List<CreditModel>) {
    val oldSize = items.size
    items.clear()
    notifyItemRangeRemoved(0, oldSize)
    items.addAll(filteredList)
    notifyItemRangeInserted(oldSize, items.size)
  }
}