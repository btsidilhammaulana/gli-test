package com.gli.test.screen.detail.adapter.credit

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil3.load
import com.gli.model.constant.ImageQuality
import com.gli.model.extension.StringExtensions.toImageUrl
import com.gli.model.response.credit.CreditModel
import com.gli.test.databinding.ItemCastBinding

class CreditViewHolder(
  private val binding: ItemCastBinding
) : ViewHolder(binding.root) {
  fun bind(cast: CreditModel) = with(cast) {
    binding.run {
      ivCast.load(profilePath.toImageUrl(ImageQuality.W500))
      tvCast.text = name
    }
  }
}