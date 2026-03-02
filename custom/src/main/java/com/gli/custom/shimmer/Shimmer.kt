package com.gli.custom.shimmer

import android.graphics.Color
import androidx.annotation.ColorInt
import androidx.annotation.FloatRange
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds


data class Shimmer(
  val style: Style = Style.Alpha,
  val direction: Direction = Direction.LeftToRight,
  @param:FloatRange(from = 0.0, to = 1.0) val baseAlpha: Float = 0.3f,
  @param:FloatRange(from = 0.0, to = 1.0) val highlightAlpha: Float = 1f,
  val shape: Shape = Shape.Linear,
  val intensity: Float = 0f,
  val dropOff: Float = 0.5f,
  val tilt: Float = 20f,
  val clipToChildren: Boolean = true,
  val autoStart: Boolean = true,
  val repeatCount: Int = 1000,
  val repeatMode: RepeatMode = RepeatMode.Restart,
  val animationDuration: Duration = 1.seconds,
  val repeatDelay: Duration = 0.seconds,
  val startDelay: Duration = 0.seconds,
) {

  init {
    require(intensity >= 0f) { "Given invalid intensity value: $intensity" }
    require(dropOff >= 0f) { "Given invalid dropoff value: $dropOff" }
  }

  sealed class Style {

    data object Alpha : Style()

    data class Colored(
      @param:ColorInt val baseColor: Int = Color.WHITE,
      @param:ColorInt val highlightColor: Int = Color.WHITE,
    ) : Style()
  }

  /** The shape of the shimmer's highlight. By default LINEAR is used.  */
  enum class Shape(internal val attrValue: Int) {
    /** Linear gives a ray reflection effect.  */
    Linear(0),

    /** Radial gives a spotlight effect.  */
    Radial(1);

    internal companion object {
      fun fromAttr(value: Int): Shape = entries.first { it.attrValue == value }
    }
  }

  /** Direction of the shimmer's sweep.  */
  enum class Direction(internal val attrValue: Int) {
    LeftToRight(0),
    TopToBottom(1),
    RightToLeft(2),
    BottomToTop(3);

    internal companion object {
      fun fromAttr(value: Int) = entries.first { it.attrValue == value }
    }
  }

  enum class RepeatMode(internal val attrValue: Int) {
    Restart(0),
    Reverse(1);

    internal companion object {
      fun fromAttr(value: Int) = entries.first { it.attrValue == value }
    }
  }
}