package com.gli.model.ui

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class UiState<T>(private val initial: T) {

  private val _data = MutableStateFlow(initial)

  private val data: StateFlow<T> = _data.asStateFlow()

  fun postValue(value: T) {
    _data.value = value
  }

  fun observe(
    owner: LifecycleOwner,
    repeat: Lifecycle.State? = null,
    action: (T) -> Unit
  ) {
    if (repeat == null) {
      observeOnce(owner, action)
      return
    }

    observeRepeated(owner, repeat, action)
  }

  private fun observeOnce(owner: LifecycleOwner, action: (T) -> Unit) {
    owner.lifecycleScope.launch {
      data.collectLatest { action.invoke(it) }
    }
  }

  private fun observeRepeated(owner: LifecycleOwner, repeat: Lifecycle.State, action: (T) -> Unit) {
    owner.lifecycleScope.launch {
      owner.repeatOnLifecycle(repeat) {
        data.collectLatest { action.invoke(it) }
      }
    }
  }
}