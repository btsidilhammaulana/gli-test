package com.gli.custom.shimmer

import android.content.Context
import android.util.AttributeSet
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.AbstractComposeView
import androidx.compose.ui.unit.dp
import com.gli.test.custom.R

class ShimmerView @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0
) : AbstractComposeView(context, attrs, defStyleAttr) {

  var radius: Float = 0f // Default radius in dp

  init {
    context.theme.obtainStyledAttributes(
      attrs,
      R.styleable.ShimmerView,
      defStyleAttr,
      0
    ).apply {
      try {
        radius = getDimension(R.styleable.ShimmerView_corner_radius, 0f) // Default to 0dp
      } finally {
        recycle()
      }
    }
  }

  @Composable
  override fun Content() {
    ShimmerEffectBox()
  }

  @Composable
  fun ShimmerEffectBox() {
    val shimmerColors = listOf(
      Color.LightGray.copy(alpha = 0.6f),
      Color.LightGray.copy(alpha = 0.2f),
      Color.LightGray.copy(alpha = 0.6f),
    )

    val infiniteTransition = rememberInfiniteTransition(label = "shimmerTransition")
    val shimmerTranslateAnim = infiniteTransition.animateFloat(
      initialValue = 0f,
      targetValue = 1000F,
      animationSpec = infiniteRepeatable(
        animation = tween(durationMillis = 1200),
        repeatMode = RepeatMode.Restart
      ), label = "shimmerTranslate"
    )

    val shimmerBrush = Brush.linearGradient(
      colors = shimmerColors,
      start = Offset.Zero,
      end = Offset(shimmerTranslateAnim.value, shimmerTranslateAnim.value)
    )

    val shimmerShape = RoundedCornerShape(radius.dp)

    val shimmerModifier = Modifier
      .fillMaxSize()
      .clip(shimmerShape)
      .background(shimmerBrush, shimmerShape)

    Box(
      modifier = Modifier
        .fillMaxSize()
        .clip(shimmerShape)
        .background(Color.White, shimmerShape)
    ) {
      Box(modifier = shimmerModifier)
    }
  }
}