package com.gli.test.screen.detail.adapter.credit

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil3.load
import coil3.request.error
import coil3.request.fallback
import coil3.request.placeholder
import coil3.request.transformations
import coil3.transform.RoundedCornersTransformation
import com.gli.model.constant.ImageQuality
import com.gli.model.extension.StringExtensions.toImageUrl
import com.gli.model.response.credit.CreditModel
import com.gli.test.R
import com.gli.test.databinding.ItemCastBinding

class CreditViewHolder(
  private val binding: ItemCastBinding
) : ViewHolder(binding.root) {
  fun bind(cast: CreditModel) = with(cast) {
    binding.run {
      ivCast.load(profilePath.toImageUrl(ImageQuality.W500)) {
        placeholder(R.drawable.img_placeholder)
        error(R.drawable.img_placeholder)
        fallback(R.drawable.img_placeholder)
      }
      tvCast.text = name
    }
  }
}