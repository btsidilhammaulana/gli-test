package com.gli.test.screen.detail.adapter.review

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil3.load
import com.gli.model.constant.ImageQuality
import com.gli.model.extension.StringExtensions.toImageUrl
import com.gli.model.response.review.ReviewModel
import com.gli.test.databinding.ItemReviewBinding

class ReviewViewHolder(
  private val binding: ItemReviewBinding
) : ViewHolder(binding.root) {
  fun bind(review: ReviewModel) = with(review) {
    binding.run {
      ivAuthor.load(authorDetails?.avatarPath.toImageUrl(ImageQuality.W500))
      tvAuthor.text = author
      tvDate.text = createdAt
      tvContent.text = content
    }
  }
}