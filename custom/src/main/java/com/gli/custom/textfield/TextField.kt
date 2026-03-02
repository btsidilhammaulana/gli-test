package com.gli.custom.textfield

import android.content.Context
import android.text.Editable
import android.text.InputType
import android.text.Spanned
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.gli.model.extension.NumberExtension.orZero
import com.gli.test.custom.R
import com.gli.test.custom.databinding.ViewTextFormFieldComponentBinding

class TextField @JvmOverloads constructor(
  context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

  private val binding: ViewTextFormFieldComponentBinding
  private var isPasswordField = false

  init {
    val inflater = LayoutInflater.from(context)
    binding = ViewTextFormFieldComponentBinding.inflate(inflater, this, true)

    context.theme.obtainStyledAttributes(
      attrs, R.styleable.TextField, defStyleAttr, 0
    ).run {
      try {
        val label = getString(R.styleable.TextField_label)
        val hint = getString(R.styleable.TextField_hint)
        val prefixIcon = getResourceId(R.styleable.TextField_prefixIcon, 0)
        val suffixIcon = getResourceId(R.styleable.TextField_suffixIcon, 0)
        val prefixText = getString(R.styleable.TextField_prefixText)
        val suffixText = getString(R.styleable.TextField_suffixText)
        val text = getString(R.styleable.TextField_text)
        val isPasswordField = getBoolean(R.styleable.TextField_isPasswordField, false)
        val isEnabled = getBoolean(R.styleable.TextField_enabledState, true)
        val inputType = getInt(R.styleable.TextField_inputType, InputType.TYPE_CLASS_TEXT)

        label?.let { setLabel(it) }
        text?.let { setText(it) }
        prefixText?.let { setPrefixText(it) }
        suffixText?.let { setSuffixText(it) }
        hint?.let { setHint(it) }
        setInputType(inputType)
        if (prefixIcon != 0) setPrefixImage(prefixIcon)
        if (suffixIcon != 0) setSuffixImage(suffixIcon)
        if (isPasswordField) setPasswordField(true)
        if (isEnabled) setEnabledState(true) else setEnabledState(false)
      } finally {
        recycle()
      }
    }

    setupFocusChangeListener()
  }

  private fun setupFocusChangeListener() {
    binding.textField.setOnFocusChangeListener { _, hasFocus ->
      val backgroundRes = when {
        binding.tvError.text.isNotEmpty() -> {
          R.drawable.bg_rounded_bordered_error
        }

        hasFocus -> {
          R.drawable.bg_rounded_bordered_focus
        }

        else -> {
          R.drawable.bg_rounded_bordered
        }
      }
      binding.rootEditText.setBackgroundResource(backgroundRes)
    }
  }

  /**
   * Configure the field as a password field with toggleable visibility.
   */
  fun setPasswordField(isPassword: Boolean) {
    isPasswordField = isPassword
    if (isPassword) {
      setSuffixImage(R.drawable.ic_visibility_off)
      binding.textField.run {
        inputType = android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD
        transformationMethod = android.text.method.PasswordTransformationMethod.getInstance()
      }
      setupPasswordToggle()
    }
  }

  private fun setupPasswordToggle() {
    binding.ivSuffix.setOnClickListener {
      binding.textField.run {
        val isPassword =
          transformationMethod == android.text.method.PasswordTransformationMethod.getInstance()
        // Remove the text watcher first
        if (binding.tvError.text.isEmpty()) removeTextChangedListener()
        transformationMethod =
          if (isPassword) null else android.text.method.PasswordTransformationMethod.getInstance()
        // Reassign the text watcher when the transformation method is changed
        addTextChangedListener(textWatcher)
        setSuffixImage(if (isPassword) R.drawable.ic_visibility else R.drawable.ic_visibility_off)
        // Retain cursor position
        setSelection(text?.length.orZero())
      }
    }
  }

  /**
   * Set text size of Label
   */
  fun setLabelSize(size: Float) {
    binding.tvLabel.textSize = size
  }

  /**
   * Set min lines of EditText
   */
  fun setMinLines(line: Int) {
    binding.textField.minLines = line
  }

  /**
   * Set gravity of text inside EditText
   */
  fun setTextFieldGravity(gravity: Int) {
    binding.textField.gravity = gravity
  }


  /**
   * Set the MultiLine EditText
   */
  fun setTextFieldMultiLine(){
    binding.textField.run {
      inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_MULTI_LINE
      isSingleLine = false
      setHorizontallyScrolling(false)
    }
  }

  /**
   * Get the current text from the EditText.
   */
  fun getText(): String {
    return binding.textField.text.toString()
  }

  /**
   * Set text to the EditText programmatically.
   */
  fun setText(text: String?) {
    binding.textField.run {
      // Remove the text watcher first
      removeTextChangedListener()
      setText(text)
      // Reassign the text watcher when the transformation method is changed
      textWatcher?.let { addTextChangedListener(it) }
      if(text.isNullOrBlank()) this@TextField.setError(null)
    }
  }

  /**
   * Set the label text.
   */
  fun setLabel(label: String) {
    binding.labelContainer.isVisible = true
    binding.tvLabel.text = label
  }

  fun setLabel(label: Spanned) {
    binding.labelContainer.isVisible = true
    binding.tvLabel.text = label
  }

  /**
   * Set the label text.
   */
  fun setHint(hint: String) {
    binding.textField.hint = hint
  }

  fun setHint(hint: Spanned) {
    binding.textField.hint = hint
  }

  /**
   * Set prefix image
   */
  fun setPrefixImage(@DrawableRes id: Int) {
    binding.ivPrefix.run {
      setImageResource(id)
      isVisible = true
    }
  }

  /**
   * Set suffix text
   */
  fun setSuffixImage(@DrawableRes id: Int) {
    binding.ivSuffix.run {
      setImageResource(id)
      isVisible = true
    }
  }

  fun setColorSuffixImage(@ColorRes color: Int) {
    binding.ivSuffix.run {
      binding.ivSuffix.setColorFilter(ContextCompat.getColor(context, color))
    }
  }

  /**
   * Set prefix text
   */
  fun setPrefixText(text: String) {
    binding.tvPrefix.run {
      this.text = text
      isVisible = true
    }
  }

  /**
   * Set suffix image
   */
  fun setSuffixText(text: String) {
    binding.tvSuffix.run {
      this.text = text
      isVisible = true
    }
  }

  fun setInputType(type: Int) {
    binding.textField.inputType = type
  }

  /**
   * Set an error message to display below the input field.
   */
  fun setError(errorMessage: String?) {
    val backgroundRes = if (errorMessage != null) {
      R.drawable.bg_rounded_bordered_error
    } else {
      if (binding.textField.hasFocus()) {
        R.drawable.bg_rounded_bordered_focus
      } else {
        R.drawable.bg_rounded_bordered
      }
    }
    binding.rootEditText.setBackgroundResource(backgroundRes)

    val colorRes = if (errorMessage != null) {
      R.color.error_500
    } else {
      R.color.primary_500
    }
    binding.ivPrefix.setColorFilter(ContextCompat.getColor(context, colorRes))
    binding.ivSuffix.setColorFilter(ContextCompat.getColor(context, colorRes))
    binding.tvError.run {
      text = errorMessage
      isVisible = errorMessage != null
    }
  }

  fun hasError(): Boolean {
    return binding.tvError.isVisible
  }

  /**
   * Add a TextWatcher for the EditText.
   */
  private var textWatcher: TextWatcher? = null

  fun addTextChangedListener(
    onTextChanged: ((text: CharSequence?) -> Unit)? = null,
  ) {
    textWatcher = object : TextWatcher {
      override fun beforeTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
        /** No Implementation */
      }

      override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
        /** No Implementation */
      }

      override fun afterTextChanged(s: Editable?) {
        onTextChanged?.invoke(s)
      }
    }
    binding.textField.addTextChangedListener(textWatcher)
  }

  /**
   * Remove all TextWatchers for the EditText.
   */
  fun removeTextChangedListener() {
    binding.textField.removeTextChangedListener(textWatcher)
  }

  /**
   * Set the text field enabled or disabled.
   */
  fun setEnabledState(isEnabled: Boolean) {
    setChildrenEnabled(binding.rootEditText, isEnabled)

    binding.rootEditText.setBackgroundResource(
      if (!isEnabled) R.drawable.bg_rounded_disabled else R.drawable.bg_rounded_bordered
    )
  }

  private fun setChildrenEnabled(parent: ViewGroup, isEnabled: Boolean) {
    parent.isEnabled = isEnabled
    for (i in 0 until parent.childCount) {
      val child = parent.getChildAt(i)
      child.isEnabled = isEnabled
      if (child is ViewGroup) {
        // Recursively handle nested child views
        setChildrenEnabled(child, isEnabled)
      }
    }
  }

  fun setRequired(){
    binding.tvRequired.isVisible = true
  }

  fun isRequired(): Boolean{
    return binding.tvRequired.isVisible
  }

  fun setReadOnly() {
    binding.rootEditText.run {
      isClickable = true
      isFocusable = false
      isFocusableInTouchMode = false
    }

    binding.textField.run {
      inputType = InputType.TYPE_NULL
      isFocusable = false
      isClickable = true
      isFocusableInTouchMode = false
    }
  }

  fun setOnClickChild(callback: () -> Unit) {
    binding.rootEditText.setOnClickListener {
      callback.invoke()
    }

    binding.textField.setOnClickListener {
      callback.invoke()
    }
  }

  fun setBackgroundFocus() {
    if(binding.textField.text.isNotEmpty()){
      binding.rootEditText.setBackgroundResource(R.drawable.bg_rounded_bordered_focus)
    }
  }

  fun setEmpty(){
    binding.textField.setText("")
  }

}