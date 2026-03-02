package com.gli.test.util.extension

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

object ContextExtensions {

  /**
   * String Resource
   */
  fun Context?.getStringResource(@StringRes resId: Int): String? {
    this ?: return null
    return ContextCompat.getString(this, resId)
  }

  fun AppCompatActivity.getStringResource(@StringRes resId: Int): String {
    return ContextCompat.getString(this, resId)
  }

  /**
   * Color Resource
   */
  fun Context?.getColorResource(@ColorRes resId: Int): Int? {
    val context = this ?: return null
    return ContextCompat.getColor(context, resId)
  }

  fun AppCompatActivity.getColorResource(@ColorRes resId: Int): Int {
    return ContextCompat.getColor(this, resId)
  }

  /**
   * Drawable Resource
   */
  fun Context?.getDrawableResource(@DrawableRes resId: Int): Drawable? {
    val context = this ?: return null
    return ContextCompat.getDrawable(context, resId)
  }

  fun AppCompatActivity.getDrawableResource(@DrawableRes resId: Int): Drawable? {
    return ContextCompat.getDrawable(this, resId)
  }

  /**
   * Color State List Resource
   */
  fun Context?.getColorStateListResource(@ColorRes resId: Int): ColorStateList? {
    val context = this ?: return null
    return ContextCompat.getColorStateList(context, resId)
  }

  fun AppCompatActivity.getColorStateListResource(@ColorRes resId: Int): ColorStateList? {
    return ContextCompat.getColorStateList(this, resId)
  }

  /**
   * Dimension Resource (font-size, if not sure)
   */
  fun Context?.getDimenResource(@DimenRes resId: Int): Float? {
    val context = this ?: return null
    return context.resources?.getDimension(resId)
  }


  fun AppCompatActivity.getDimenResource(@DimenRes resId: Int): Float {
    return resources.getDimension(resId)
  }

  /**
   * Dimension Pixel Offset Resource (padding / margin / translate)
   */
  fun Context?.getDimenOffsetResource(@DimenRes resId: Int): Int? {
    val context = this ?: return null
    return context.resources?.getDimensionPixelOffset(resId)
  }

  fun AppCompatActivity.getDimenOffsetResource(@DimenRes resId: Int): Int {
    return resources.getDimensionPixelOffset(resId)
  }

  /**
   * Dimension Pixel Size Resource (width / height)
   */
  fun Context?.getDimenSizeResource(@DimenRes resId: Int): Int? {
    val context = this ?: return null
    return context.resources?.getDimensionPixelSize(resId)
  }


  fun AppCompatActivity.getDimenSizeResource(@DimenRes resId: Int): Int {
    return resources.getDimensionPixelSize(resId)
  }

}