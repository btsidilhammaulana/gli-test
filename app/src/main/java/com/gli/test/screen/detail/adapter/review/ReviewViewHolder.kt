package com.gli.test.screen.detail.adapter.review

import android.view.View
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil3.load
import coil3.request.error
import coil3.request.fallback
import coil3.request.placeholder
import coil3.request.transformations
import coil3.transform.RoundedCornersTransformation
import com.gli.model.constant.ImageQuality
import com.gli.model.extension.DateExtension.toFormattedDate
import com.gli.model.extension.StringExtensions.toImageUrl
import com.gli.model.response.review.ReviewModel
import com.gli.test.R
import com.gli.test.databinding.ItemReviewBinding
import com.gli.test.util.extension.ContextExtensions.getStringResource

class ReviewViewHolder(
  private val binding: ItemReviewBinding
) : ViewHolder(binding.root) {
  fun bind(review: ReviewModel, onReadMoreClick: () -> Unit?) = with(review) {
    val context = binding.root.context

    binding.run {
      ivAuthor.load(authorDetails?.avatarPath.toImageUrl(ImageQuality.W500)) {
        placeholder(R.drawable.img_placeholder)
        error(R.drawable.img_placeholder)
        fallback(R.drawable.img_placeholder)
        transformations(RoundedCornersTransformation(32f))
      }
      tvAuthor.text = author
      tvDate.text = createdAt?.toFormattedDate("dd MMM yyyy, HH:mm")
      tvContent.text = content

      if (review.isExpanded) {
        tvContent.maxLines = Integer.MAX_VALUE
        tvReadMore.text = context.getStringResource(R.string.read_less)
      } else {
        tvContent.maxLines = 3
        tvReadMore.text = context.getStringResource(R.string.read_more)
      }

      tvContent.post {
        val lineCount = tvContent.lineCount
        if (lineCount >= 3) {
          tvReadMore.visibility = View.VISIBLE
        } else {
          tvReadMore.visibility = View.GONE
        }
      }
    }

    binding.tvReadMore.setOnClickListener {
      review.isExpanded = !review.isExpanded
      onReadMoreClick.invoke()
    }
  }
}