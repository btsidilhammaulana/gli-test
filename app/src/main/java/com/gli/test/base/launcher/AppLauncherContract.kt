package com.gli.test.base.launcher

import android.content.Intent
import android.net.Uri
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia.VisualMediaType
import androidx.core.app.ActivityOptionsCompat
import androidx.lifecycle.DefaultLifecycleObserver

interface AppLauncherContract : DefaultLifecycleObserver {
  fun startActivityForResult(
    intent: Intent,
    options: ActivityOptionsCompat?,
    callback: OnReceiveActivityResult
  )

  fun startIntentSenderForResult(
    intent: IntentSenderRequest,
    options: ActivityOptionsCompat?,
    callback: OnReceiveActivityResult
  )

  fun requestPermissions(
    permissions: Array<String>,
    options: ActivityOptionsCompat?,
    callback: OnReceivePermissionsResult
  )

  fun takePicturePreview(
    options: ActivityOptionsCompat?,
    callback: OnReceiveBitmapResult
  )

  fun takePicture(
    uri: Uri,
    options: ActivityOptionsCompat?,
    callback: OnReceiveStateResult
  )

  fun captureVideo(
    uri: Uri,
    options: ActivityOptionsCompat?,
    callback: OnReceiveStateResult
  )

  fun pickContact(
    options: ActivityOptionsCompat?,
    callback: OnReceiveUriResult
  )

  fun pickVisualMedia(
    type: VisualMediaType,
    options: ActivityOptionsCompat?,
    callback: OnReceiveUriResult
  )

  fun pickMultipleVisualMedia(
    type: VisualMediaType,
    options: ActivityOptionsCompat?,
    callback: OnReceiveListUriResult
  )

  fun getContent(
    mimeType: ContentMimeType,
    options: ActivityOptionsCompat?,
    callback: OnReceiveUriResult
  )

  fun getMultipleContents(
    mimeType: ContentMimeType,
    options: ActivityOptionsCompat?,
    callback: OnReceiveListUriResult
  )

  fun openDocument(
    mimeType: Array<ContentMimeType>,
    options: ActivityOptionsCompat?,
    callback: OnReceiveUriResult
  )

  fun openMultipleDocument(
    mimeType: Array<ContentMimeType>,
    options: ActivityOptionsCompat?,
    callback: OnReceiveListUriResult
  )

  fun openDocumentTree(
    uri: Uri,
    options: ActivityOptionsCompat?,
    callback: OnReceiveUriResult
  )
}