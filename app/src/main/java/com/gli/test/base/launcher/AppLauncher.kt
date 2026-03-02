package com.gli.test.base.launcher

import android.content.Intent
import android.net.Uri
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia.VisualMediaType
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.fragment.app.Fragment
import com.gli.test.base.launcher.kind.ActivityAppLauncher
import com.gli.test.base.launcher.kind.FragmentAppLauncher

@Suppress("MemberVisibilityCanBePrivate", "unused")
class AppLauncher private constructor() {

  private lateinit var impl: AppLauncherContract

  constructor(
    activity: AppCompatActivity,
    launcherType: List<AppLauncherType>
  ) : this() {
    impl = ActivityAppLauncher(activity = activity, launcherType)
    activity.lifecycle.addObserver(impl)
  }

  constructor(
    fragment: Fragment,
    launcherType: List<AppLauncherType>
  ) : this() {
    impl = FragmentAppLauncher(fragment = fragment, launcherType)
    fragment.lifecycle.addObserver(impl)
  }

  /**
   * Activity Result
   */
  fun startActivityForResult(intent: Intent, callback: OnReceiveActivityResult) {
    startActivityForResult(intent, null, callback)
  }

  fun startActivityForResult(
    intent: Intent,
    options: ActivityOptionsCompat?,
    callback: OnReceiveActivityResult
  ) = impl.startActivityForResult(intent, options, callback)

  /**
   * Intent Sender
   */
  fun startIntentSenderForResult(intent: IntentSenderRequest, callback: OnReceiveActivityResult) {
    startIntentSenderForResult(intent, null, callback)
  }

  fun startIntentSenderForResult(
    intent: IntentSenderRequest,
    options: ActivityOptionsCompat?,
    callback: OnReceiveActivityResult
  ) = impl.startIntentSenderForResult(intent, options, callback)

  /**
   * Request Permissions
   */
  fun requestPermissions(permissions: Array<String>, callback: OnReceivePermissionsResult) {
    requestPermissions(permissions, null, callback)
  }

  fun requestPermissions(
    permissions: Array<String>,
    options: ActivityOptionsCompat?,
    callback: OnReceivePermissionsResult
  ) = impl.requestPermissions(permissions, options, callback)

  /**
   * Take Picture Preview
   */
  fun takePicturePreview(callback: OnReceiveBitmapResult) {
    takePicturePreview(null, callback)
  }

  fun takePicturePreview(
    options: ActivityOptionsCompat?, callback:
    OnReceiveBitmapResult
  ) = impl.takePicturePreview(options, callback)

  /**
   * Take Picture
   */
  fun takePicture(
    uri: Uri,
    callback: OnReceiveStateUriResult
  ) = takePicture(uri, null, callback)

  fun takePicture(
    uri: Uri,
    options: ActivityOptionsCompat?,
    callback: OnReceiveStateUriResult
  ) = impl.takePicture(uri, options) { callback.invoke(it, uri) }

  /**
   * Capture Video
   */
  fun captureVideo(
    uri: Uri,
    callback: OnReceiveStateUriResult
  ) = captureVideo(uri, null, callback)

  fun captureVideo(
    uri: Uri,
    options: ActivityOptionsCompat?,
    callback: OnReceiveStateUriResult
  ) = impl.captureVideo(uri, options) { callback.invoke(it, uri) }

  /**
   * Pick Contact
   */
  fun pickContact(callback: OnReceiveUriResult) {
    pickContact(null, callback)
  }

  fun pickContact(
    options: ActivityOptionsCompat?,
    callback: OnReceiveUriResult
  ) = impl.pickContact(options) { callback.invoke(it) }

  /**
   * Pick Visual Media
   */
  fun pickVisualMedia(
    mediaType: VisualMediaType,
    callback: OnReceiveUriResult,
  ) = pickVisualMedia(mediaType, null, callback)


  fun pickVisualMedia(
    mediaType: VisualMediaType,
    options: ActivityOptionsCompat?,
    callback: OnReceiveUriResult,
  ) = impl.pickVisualMedia(mediaType, options, callback)

  /**
   * Pick Multiple Visual Media
   */
  fun pickMultipleVisualMedia(
    mediaType: VisualMediaType,
    callback: OnReceiveListUriResult,
  ) = pickMultipleVisualMedia(mediaType, null, callback)


  fun pickMultipleVisualMedia(
    mediaType: VisualMediaType,
    options: ActivityOptionsCompat?,
    callback: OnReceiveListUriResult,
  ) = impl.pickMultipleVisualMedia(mediaType, options, callback)

  /**
   * Get Content
   */
  fun getContent(
    mimeType: ContentMimeType,
    callback: OnReceiveUriResult
  ) = getContent(mimeType, null, callback)

  fun getContent(
    mimeType: ContentMimeType,
    options: ActivityOptionsCompat?,
    callback: OnReceiveUriResult
  ) = impl.getContent(mimeType, options, callback)

  /**
   * Get Multiple Contents
   */
  fun getMultipleContents(
    mimeType: ContentMimeType,
    callback: OnReceiveListUriResult
  ) = getMultipleContents(mimeType, null, callback)

  fun getMultipleContents(
    mimeType: ContentMimeType,
    options: ActivityOptionsCompat?,
    callback: OnReceiveListUriResult
  ) {
    impl.getMultipleContents(mimeType, options, callback)
  }

  /**
   * Open Document
   */
  fun openDocument(
    mimeType: Array<ContentMimeType>,
    callback: OnReceiveUriResult
  ) {
    openDocument(mimeType, null, callback)
  }

  fun openDocument(
    mimeType: Array<ContentMimeType>,
    options: ActivityOptionsCompat?,
    callback: OnReceiveUriResult
  ) {
    impl.openDocument(mimeType, options, callback)
  }

  /**
   * Open Document
   */
  fun openMultipleDocuments(
    mimeType: Array<ContentMimeType>,
    callback: OnReceiveListUriResult
  ) {
    openMultipleDocuments(mimeType, null, callback)
  }

  fun openMultipleDocuments(
    mimeType: Array<ContentMimeType>,
    options: ActivityOptionsCompat?,
    callback: OnReceiveListUriResult
  ) {
    impl.openMultipleDocument(mimeType, options, callback)
  }

  /**
   * Open Document Tree
   */
  fun openDocumentTree(
    uri: Uri,
    callback: OnReceiveUriResult
  ) {
    openDocumentTree(uri, null, callback)
  }

  fun openDocumentTree(
    uri: Uri,
    options: ActivityOptionsCompat?,
    callback: OnReceiveUriResult
  ) {
    impl.openDocumentTree(uri, options, callback)
  }
}