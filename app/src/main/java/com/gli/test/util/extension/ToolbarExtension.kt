package com.gli.test.util.extension

import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.gli.test.R

object ToolbarExtensions {

  fun AppCompatActivity.setAsActionBar(
    toolbar: Toolbar,
    @DrawableRes indicator: Int = R.drawable.ic_arrow_back_white,
    onClick: () -> Unit = { onBackPressedDispatcher.onBackPressed() }
  ) {
    setSupportActionBar(toolbar)
    supportActionBar?.setDisplayShowTitleEnabled(false)
    supportActionBar?.setDisplayHomeAsUpEnabled(true)
    supportActionBar?.setHomeAsUpIndicator(indicator)
    toolbar.setNavigationOnClickListener { onClick() }
  }
}