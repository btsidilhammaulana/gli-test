package com.gli.custom.circleimageview

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapShader
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.ColorFilter
import android.graphics.Matrix
import android.graphics.Outline
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.RectF
import android.graphics.Shader
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewOutlineProvider
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.appcompat.widget.AppCompatImageView
import com.gli.test.custom.R
import kotlin.math.min

class CircleImageView : AppCompatImageView {

  private var mBorderWidth: Float = 0F

  private var mBorderColor: Int = Color.BLACK

  private var mBorderOverlay: Boolean = false

  private var mCircleBackgroundColor: Int = Color.TRANSPARENT

  private var mInitialized: Boolean = false

  private var mColorFilter: ColorFilter? = null

  private var mDisableCircularTransformation: Boolean = false

  private var mDrawableDirty: Boolean = false

  private var mRebuildShader = false

  private var mImageAlpha: Int = 255

  private var mDrawableRadius: Float = 0f

  private var mBorderRadius = 0f

  private var mBitmap: Bitmap? = null

  private var mBitmapCanvas: Canvas? = null

  private val mScaleType: ScaleType = ScaleType.CENTER_CROP

  private val mColorDrawableDimension: Int = 2

  private val mBitmapConfig: Bitmap.Config = Bitmap.Config.ARGB_8888

  private val mBitmapPaint: Paint by lazy { Paint() }

  private val mBorderPaint: Paint by lazy { Paint() }

  private val mCircleBackgroundPaint by lazy { Paint() }

  private val mDrawableRect by lazy { RectF() }

  private val mBorderRect by lazy { RectF() }

  private val mShaderMatrix by lazy { Matrix() }

  constructor(context: Context) : this(context, null)

  constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

  constructor(
    context: Context,
    attrs: AttributeSet?,
    defStyle: Int
  ) : super(context, attrs, defStyle) {
    context.theme.obtainStyledAttributes(attrs, R.styleable.CircleImageView, defStyle, 0).run {
      try {
        mBorderWidth = getDimension(
          R.styleable.CircleImageView_border_width,
          0F
        )
        mBorderColor = getColor(
          R.styleable.CircleImageView_border_color,
          Color.BLACK
        )
        mBorderOverlay = getBoolean(
          R.styleable.CircleImageView_border_overlay,
          false
        )
        mCircleBackgroundColor = getColor(
          R.styleable.CircleImageView_circle_background_color,
          Color.TRANSPARENT
        )

        init()
      } finally {
        recycle()
      }
    }
  }

  private fun init() {
    mInitialized = true

    super.setScaleType(mScaleType)

    mBitmapPaint.run {
      mBitmapPaint.isAntiAlias = true
      mBitmapPaint.isDither = true
      mBitmapPaint.isFilterBitmap = true
      mBitmapPaint.setAlpha(mImageAlpha)
      mBitmapPaint.setColorFilter(mColorFilter)
    }

    mBorderPaint.run {
      style = Paint.Style.STROKE
      isAntiAlias = true
      setColor(mBorderColor)
      strokeWidth = mBorderWidth
    }

    mCircleBackgroundPaint.run {
      style = Paint.Style.FILL
      isAntiAlias = true
      setColor(mCircleBackgroundColor)
    }

    outlineProvider = OutlineProvider()
  }

  override fun setScaleType(scaleType: ScaleType) {
    require(scaleType == mScaleType) { String.format("ScaleType %s not supported.", scaleType) }
  }

  override fun setAdjustViewBounds(adjustViewBounds: Boolean) {
    require(!adjustViewBounds) { "adjustViewBounds not supported." }
  }

  @SuppressLint("CanvasSize")
  override fun onDraw(canvas: Canvas) {
    if (mDisableCircularTransformation) {
      super.onDraw(canvas)
      return
    }

    if (mCircleBackgroundColor != Color.TRANSPARENT) {
      canvas.drawCircle(
        mDrawableRect.centerX(),
        mDrawableRect.centerY(),
        mDrawableRadius,
        mCircleBackgroundPaint
      )
    }

    mBitmap?.let { bitmap ->
      if (mDrawableDirty) {
        mDrawableDirty = false
        mBitmapCanvas?.let { bitmapCanvas ->
          drawable.setBounds(0, 0, bitmapCanvas.width, bitmapCanvas.height)
          drawable.draw(bitmapCanvas)
        }
      }

      if (mRebuildShader) {
        mRebuildShader = false
        val bitmapShader = BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
        bitmapShader.setLocalMatrix(mShaderMatrix)
        mBitmapPaint.shader = bitmapShader
      }

      canvas.drawCircle(
        mDrawableRect.centerX(),
        mDrawableRect.centerY(),
        mDrawableRadius,
        mBitmapPaint
      )
    }

    if (mBorderWidth > 0) {
      canvas.drawCircle(
        mBorderRect.centerX(),
        mBorderRect.centerY(),
        mBorderRadius,
        mBorderPaint
      )
    }
  }

  override fun invalidateDrawable(dr: Drawable) {
    mDrawableDirty = true
    invalidate()
  }

  override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
    super.onSizeChanged(w, h, oldw, oldh)
    updateDimensions()
    invalidate()
  }

  override fun setPadding(left: Int, top: Int, right: Int, bottom: Int) {
    super.setPadding(left, top, right, bottom)
    updateDimensions()
    invalidate()
  }

  override fun setPaddingRelative(start: Int, top: Int, end: Int, bottom: Int) {
    super.setPaddingRelative(start, top, end, bottom)
    updateDimensions()
    invalidate()
  }

  fun getBorderColor() = mBorderColor

  fun setBorderColor(@ColorInt borderColor: Int) {
    if (borderColor == mBorderColor) return
    mBorderColor = borderColor
    mBorderPaint.color = borderColor
    invalidate()
  }

  fun getCircleBackgroundColor() = mCircleBackgroundColor

  fun setCircleBackgroundColor(@ColorInt circleBackgroundColor: Int) {
    if (circleBackgroundColor == mCircleBackgroundColor) {
      return
    }

    mCircleBackgroundColor = circleBackgroundColor
    mCircleBackgroundPaint.color = circleBackgroundColor
    invalidate()
  }

  fun getBorderWidth() = mBorderWidth.toInt()

  fun setBorderWidth(borderWidth: Int) {
    if (borderWidth.toFloat() == mBorderWidth) {
      return
    }

    mBorderWidth = borderWidth.toFloat()
    mBorderPaint.strokeWidth = borderWidth.toFloat()
    updateDimensions()
    invalidate()
  }

  fun isBorderOverlay() = mBorderOverlay

  fun setBorderOverlay(borderOverlay: Boolean) {
    if (borderOverlay == mBorderOverlay) {
      return
    }

    mBorderOverlay = borderOverlay
    updateDimensions()
    invalidate()
  }

  fun isDisableCircularTransformation() = mDisableCircularTransformation

  fun setDisableCircularTransformation(disableCircularTransformation: Boolean) {
    if (disableCircularTransformation == mDisableCircularTransformation) return

    mDisableCircularTransformation = disableCircularTransformation

    if (disableCircularTransformation) {
      mBitmap = null
      mBitmapCanvas = null
      mBitmapPaint.shader = null
    } else {
      initializeBitmap()
    }

    invalidate()
  }

  override fun setImageBitmap(bm: Bitmap?) {
    super.setImageBitmap(bm)
    initializeBitmap()
    invalidate()
  }

  override fun setImageDrawable(drawable: Drawable?) {
    super.setImageDrawable(drawable)
    initializeBitmap()
    invalidate()
  }

  override fun setImageResource(@DrawableRes resId: Int) {
    super.setImageResource(resId)
    initializeBitmap()
    invalidate()
  }

  override fun setImageURI(uri: Uri?) {
    super.setImageURI(uri)
    initializeBitmap()
    invalidate()
  }

  override fun setImageAlpha(alpha: Int) {
    val clampedAlpha = alpha.coerceIn(0, 255)
    if (clampedAlpha == mImageAlpha) return

    mImageAlpha = clampedAlpha

    if (mInitialized) {
      mBitmapPaint.alpha = clampedAlpha
      invalidate()
    }
  }

  override fun getImageAlpha() = mImageAlpha

  override fun setColorFilter(cf: ColorFilter?) {
    if (cf == mColorFilter) return

    mColorFilter = cf

    if (mInitialized) {
      mBitmapPaint.colorFilter = cf
      invalidate()
    }
  }

  override fun getColorFilter() = mColorFilter

  private fun getBitmapFromDrawable(drawable: Drawable?): Bitmap? {
    if (drawable == null) return null

    return when (drawable) {
      is BitmapDrawable -> drawable.bitmap
      is ColorDrawable -> {
        Bitmap.createBitmap(
          mColorDrawableDimension,
          mColorDrawableDimension,
          mBitmapConfig
        ).apply {
          val canvas = Canvas(this)
          drawable.setBounds(0, 0, width, height)
          drawable.draw(canvas)
        }
      }
      else -> {
        try {
          val bitmap = Bitmap.createBitmap(
            drawable.intrinsicWidth,
            drawable.intrinsicHeight,
            mBitmapConfig
          )
          val canvas = Canvas(bitmap)
          drawable.setBounds(0, 0, canvas.width, canvas.height)
          drawable.draw(canvas)
          bitmap
        } catch (e: Exception) {
          e.printStackTrace()
          null
        }
      }
    }
  }

  private fun initializeBitmap() {
    mBitmap = getBitmapFromDrawable(drawable)
    mBitmapCanvas = mBitmap?.takeIf { it.isMutable }?.let { Canvas(it) }

    if (!mInitialized) return

    if (mBitmap != null) {
      updateShaderMatrix()
    } else {
      mBitmapPaint.shader = null
    }
  }

  private fun updateDimensions() {
    mBorderRect.set(calculateBounds())

    mBorderRadius = min(
      (mBorderRect.height() - mBorderWidth) / 2f,
      (mBorderRect.width() - mBorderWidth) / 2f
    )

    mDrawableRect.set(mBorderRect)

    if (!mBorderOverlay && mBorderWidth > 0) {
      mDrawableRect.inset(mBorderWidth - 1f, mBorderWidth - 1f)
    }

    mDrawableRadius = min(
      mDrawableRect.height() / 2f,
      mDrawableRect.width() / 2f
    )

    updateShaderMatrix()
  }

  private fun calculateBounds(): RectF {
    val availableWidth = (width - paddingLeft - paddingRight).toFloat()
    val availableHeight = (height - paddingTop - paddingBottom).toFloat()
    val sideLength = min(availableWidth, availableHeight)
    val left = paddingLeft + (availableWidth - sideLength) / 2
    val top = paddingTop + (availableHeight - sideLength) / 2
    return RectF(left, top, left + sideLength, top + sideLength)
  }

  private fun updateShaderMatrix() {
    val bitmap = mBitmap ?: return

    val bitmapHeight = bitmap.height
    val bitmapWidth = bitmap.width

    val scale: Float
    var dx = 0f
    var dy = 0f

    mShaderMatrix.reset()

    if (bitmapWidth * mDrawableRect.height() > mDrawableRect.width() * bitmapHeight) {
      scale = mDrawableRect.height() / bitmapHeight.toFloat()
      dx = (mDrawableRect.width() - bitmapWidth * scale) * 0.5f
    } else {
      scale = mDrawableRect.width() / bitmapWidth.toFloat()
      dy = (mDrawableRect.height() - bitmapHeight * scale) * 0.5f
    }

    mShaderMatrix.setScale(scale, scale)
    mShaderMatrix.postTranslate(
      (dx + 0.5f).toInt() + mDrawableRect.left,
      (dy + 0.5f).toInt() + mDrawableRect.top
    )

    mRebuildShader = true
  }

  @SuppressLint("ClickableViewAccessibility")
  override fun onTouchEvent(event: MotionEvent): Boolean {
    if (mDisableCircularTransformation) return super.onTouchEvent(event)

    return if (inTouchableArea(event.x, event.y)) {
      super.onTouchEvent(event)
    } else {
      false
    }
  }

  private fun inTouchableArea(x: Float, y: Float): Boolean {
    if (mBorderRect.isEmpty) {
      return true
    }

    val dx = x - mBorderRect.centerX()
    val dy = y - mBorderRect.centerY()

    return dx * dx + dy * dy <= mBorderRadius * mBorderRadius
  }

  inner class OutlineProvider : ViewOutlineProvider() {
    override fun getOutline(view: View?, outline: Outline) {
      if (mDisableCircularTransformation) {
        BACKGROUND.getOutline(view, outline)
      } else {
        val bounds = Rect()
        mBorderRect.roundOut(bounds)
        outline.setRoundRect(bounds, bounds.width() / 2.0f)
      }
    }
  }
}