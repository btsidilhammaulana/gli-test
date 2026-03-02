package com.gli.custom.shimmer.shimmer

import android.content.res.TypedArray
import android.graphics.Color
import com.gli.test.custom.R.styleable
import kotlin.time.Duration.Companion.milliseconds

internal fun TypedArray.buildShimmer(): Shimmer {
  val direction = getInt(
    /* index = */ styleable.ShimmerLayout_shimmer_direction,
    /* defValue = */ Shimmer.Direction.LeftToRight.attrValue
  )
  val baseAlpha = getFloat(
    /* index = */ styleable.ShimmerLayout_shimmer_base_alpha,
    /* defValue = */ 0.3f
  )
  val highlightAlpha = getFloat(
    /* index = */ styleable.ShimmerLayout_shimmer_highlight_alpha,
    /* defValue = */ 1f
  )
  val shape = getInt(
    /* index = */ styleable.ShimmerLayout_shimmer_shape,
    /* defValue = */ Shimmer.Shape.Linear.attrValue
  )
  val intensity = getFloat(
    /* index = */ styleable.ShimmerLayout_shimmer_intensity,
    /* defValue = */ 0f
  )
  val dropOff = getFloat(
    /* index = */ styleable.ShimmerLayout_shimmer_dropOff,
    /* defValue = */ 0.5f
  )
  val tilt = getFloat(
    /* index = */ styleable.ShimmerLayout_shimmer_tilt,
    /* defValue = */ 20f
  )
  val clipToChildren = getBoolean(
    /* index = */ styleable.ShimmerLayout_shimmer_clip_to_children,
    /* defValue = */ true
  )
  val autoStart = getBoolean(
    /* index = */ styleable.ShimmerLayout_shimmer_auto_start,
    /* defValue = */ true
  )
  val repeatCount = getInt(
    /* index = */ styleable.ShimmerLayout_shimmer_repeat_count,
    /* defValue = */ 1000
  )
  val repeatMode = getInt(
    /* index = */ styleable.ShimmerLayout_shimmer_repeat_mode,
    /* defValue = */ Shimmer.RepeatMode.Restart.attrValue
  )
  val animationDuration = getInt(
    /* index = */ styleable.ShimmerLayout_shimmer_duration,
    /* defValue = */ 1000
  ).toLong()
  val repeatDelay = getInt(
    /* index = */ styleable.ShimmerLayout_shimmer_repeat_delay,
    /* defValue = */ 0
  ).toLong()
  val startDelay = getInt(
    /* index = */ styleable.ShimmerLayout_shimmer_start_delay,
    /* defValue = */ 0
  ).toLong()
  val colored = getBoolean(
    /* index = */ styleable.ShimmerLayout_shimmer_colored,
    /* defValue = */ false
  )
  val style = if (colored) {
    val baseColor = getColor(
      /* index = */ styleable.ShimmerLayout_shimmer_base_color,
      /* defValue = */ Color.WHITE
    )
    val highlightColor = getColor(
      /* index = */ styleable.ShimmerLayout_shimmer_highlight_color,
      /* defValue = */ Color.WHITE
    )

    Shimmer.Style.Colored(
      baseColor = baseColor,
      highlightColor = highlightColor,
    )
  } else {
    Shimmer.Style.Alpha
  }

  return Shimmer(
    style = style,
    direction = Shimmer.Direction.fromAttr(direction),
    baseAlpha = baseAlpha,
    highlightAlpha = highlightAlpha,
    shape = Shimmer.Shape.fromAttr(shape),
    intensity = intensity,
    dropOff = dropOff,
    tilt = tilt,
    clipToChildren = clipToChildren,
    autoStart = autoStart,
    repeatCount = repeatCount,
    repeatMode = Shimmer.RepeatMode.fromAttr(repeatMode),
    animationDuration = animationDuration.milliseconds,
    repeatDelay = repeatDelay.milliseconds,
    startDelay = startDelay.milliseconds,
  )
}