package com.gli.test.base.launcher.kind

import android.content.Intent
import android.net.Uri
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia.VisualMediaType
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.gli.test.base.launcher.AppLauncherContract
import com.gli.test.base.launcher.AppLauncherType
import com.gli.test.base.launcher.ContentMimeType
import com.gli.test.base.launcher.OnReceiveActivityResult
import com.gli.test.base.launcher.OnReceiveBitmapResult
import com.gli.test.base.launcher.OnReceiveListUriResult
import com.gli.test.base.launcher.OnReceivePermissionsResult
import com.gli.test.base.launcher.OnReceiveStateResult
import com.gli.test.base.launcher.OnReceiveUriResult
import kotlinx.coroutines.launch
import kotlin.collections.component1
import kotlin.collections.component2

class ActivityAppLauncher(
  private val activity: AppCompatActivity,
  private val launcherType: List<AppLauncherType>
) : AppLauncherContract {

  override fun onCreate(owner: LifecycleOwner) {
    super.onCreate(owner)
    owner.lifecycleScope.launch {
      owner.repeatOnLifecycle(Lifecycle.State.CREATED) {
        launcherType.forEach { type ->
          when (type) {
            AppLauncherType.ACTIVITY_RESULT -> registerActivityResultLauncher()
            AppLauncherType.INTENT_SENDER -> registerIntentSenderLauncher()
            AppLauncherType.REQUEST_PERMISSIONS -> registerRequestPermissionsLauncher()
            AppLauncherType.TAKE_PICTURE_PREVIEW -> registerTakePicturePreviewLauncher()
            AppLauncherType.TAKE_PICTURE -> registerTakePictureLauncher()
            AppLauncherType.CAPTURE_VIDEO -> registerCaptureVideoLauncher()
            AppLauncherType.PICK_CONTACT -> registerPickContactLauncher()
            AppLauncherType.PICK_VISUAL_MEDIA -> registerPickVisualMediaLauncher()
            AppLauncherType.PICK_MULTIPLE_VISUAL_MEDIA -> registerPickMultipleVisualMediaLauncher()
            AppLauncherType.GET_CONTENT -> registerGetContentLauncher()
            AppLauncherType.GET_MULTIPLE_CONTENTS -> registerMultipleGetContentsLauncher()
            AppLauncherType.OPEN_DOCUMENT -> registerOpenDocumentLauncher()
            AppLauncherType.OPEN_MULTIPLE_DOCUMENTS -> registerOpenMultipleDocumentsLauncher()
            AppLauncherType.OPEN_DOCUMENT_TREE -> registerOpenDocumentTreeLauncher()
          }
        }
      }
    }
  }

  override fun onDestroy(owner: LifecycleOwner) {
    unregister()
    clear()
    super.onDestroy(owner)
  }

  private fun unregister() {
    activityResultLauncher?.unregister()
    intentSenderLauncher?.unregister()
    requestPermissionsLauncher?.unregister()
    takePicturePreviewLauncher?.unregister()
    takePictureLauncher?.unregister()
    captureVideoLauncher?.unregister()
    pickContactLauncher?.unregister()
    pickVisualMediaLauncher?.unregister()
    pickMultipleVisualMediaLauncher?.unregister()
    getContentLauncher?.unregister()
    getMultipleContentsLauncher?.unregister()
    openDocumentLauncher?.unregister()
    openMultipleDocumentsLauncher?.unregister()
    openDocumentTreeLauncher?.unregister()
  }

  fun clear() {
    activityResultLauncher = null
    intentSenderLauncher = null
    requestPermissionsLauncher = null
    takePicturePreviewLauncher = null
    takePictureLauncher = null
    captureVideoLauncher = null
    pickContactLauncher = null
    pickVisualMediaLauncher = null
    pickMultipleVisualMediaLauncher = null
    getContentLauncher = null
    getMultipleContentsLauncher = null
    openDocumentLauncher = null
    openMultipleDocumentsLauncher = null
    openDocumentTreeLauncher = null
  }

  /**
   * Start Activity For Result
   */
  private var activityResultLauncher: ActivityResultLauncher<Intent>? = null

  private var onActivityResult: OnReceiveActivityResult? = null

  private fun registerActivityResultLauncher() {
    val contract = ActivityResultContracts.StartActivityForResult()
    activityResultLauncher = activity.registerForActivityResult(contract) {
      onActivityResult?.invoke(it.resultCode, it.data)
    }
  }

  override fun startActivityForResult(
    intent: Intent,
    options: ActivityOptionsCompat?,
    callback: OnReceiveActivityResult
  ) {
    this.onActivityResult = callback
    activityResultLauncher?.launch(intent, options)
  }

  /**
   * Start Intent Sender For Result
   */
  private var intentSenderLauncher: ActivityResultLauncher<IntentSenderRequest>? = null

  private var onIntentSenderResult: OnReceiveActivityResult? = null

  private fun registerIntentSenderLauncher() {
    val contract = ActivityResultContracts.StartIntentSenderForResult()
    intentSenderLauncher = activity.registerForActivityResult(contract) {
      onIntentSenderResult?.invoke(it.resultCode, it.data)
    }
  }

  override fun startIntentSenderForResult(
    intent: IntentSenderRequest,
    options: ActivityOptionsCompat?,
    callback: OnReceiveActivityResult
  ) {
    this.onIntentSenderResult = callback
    intentSenderLauncher?.launch(intent, options)
  }

  /**
   * Request Permissions
   */
  private var requestPermissionsLauncher: ActivityResultLauncher<Array<String>>? = null

  private var onRequestPermissionsResult: OnReceivePermissionsResult? = null

  private fun registerRequestPermissionsLauncher() {
    val contract = ActivityResultContracts.RequestMultiplePermissions()
    requestPermissionsLauncher = activity.registerForActivityResult(contract) {
      val hashMap: HashMap<String, Boolean> = hashMapOf()
      it.forEach { (t, u) -> hashMap[t] = u }
      onRequestPermissionsResult?.invoke(hashMap)
    }
  }

  override fun requestPermissions(
    permissions: Array<String>,
    options: ActivityOptionsCompat?,
    callback: OnReceivePermissionsResult
  ) {
    this.onRequestPermissionsResult = callback
    requestPermissionsLauncher?.launch(permissions, options)
  }

  /**
   * Take Picture Preview
   */
  private var takePicturePreviewLauncher: ActivityResultLauncher<Void?>? = null

  private var onTakePicturePreviewResult: OnReceiveBitmapResult? = null

  private fun registerTakePicturePreviewLauncher() {
    val contract = ActivityResultContracts.TakePicturePreview()
    takePicturePreviewLauncher = activity.registerForActivityResult(contract) {
      onTakePicturePreviewResult?.invoke(it)
    }
  }

  override fun takePicturePreview(
    options: ActivityOptionsCompat?,
    callback: OnReceiveBitmapResult
  ) {
    this.onTakePicturePreviewResult = callback
    takePicturePreviewLauncher?.launch(null, options)
  }

  /**
   * Take Picture
   */
  private var takePictureLauncher: ActivityResultLauncher<Uri>? = null

  private var onTakePictureResult: OnReceiveStateResult? = null

  private fun registerTakePictureLauncher() {
    val contract = ActivityResultContracts.TakePicture()
    takePictureLauncher = activity.registerForActivityResult(contract) {
      onTakePictureResult?.invoke(it)
    }
  }

  override fun takePicture(
    uri: Uri,
    options: ActivityOptionsCompat?,
    callback: OnReceiveStateResult
  ) {
    this.onTakePictureResult = callback
    takePictureLauncher?.launch(uri, options)
  }

  /**
   * Capture Video
   */
  private var captureVideoLauncher: ActivityResultLauncher<Uri>? = null

  private var onCaptureVideoResult: OnReceiveStateResult? = null

  private fun registerCaptureVideoLauncher() {
    val contract = ActivityResultContracts.CaptureVideo()
    captureVideoLauncher = activity.registerForActivityResult(contract) {
      onCaptureVideoResult?.invoke(it)
    }
  }

  override fun captureVideo(
    uri: Uri,
    options: ActivityOptionsCompat?,
    callback: OnReceiveStateResult
  ) {
    this.onCaptureVideoResult = callback
    captureVideoLauncher?.launch(uri, options)
  }

  /**
   * Pick Contact
   */
  private var pickContactLauncher: ActivityResultLauncher<Void?>? = null

  private var onPickContactResult: OnReceiveUriResult? = null

  private fun registerPickContactLauncher() {
    val contract = ActivityResultContracts.PickContact()
    pickContactLauncher = activity.registerForActivityResult(contract) {
      onPickContactResult?.invoke(it)
    }
  }

  override fun pickContact(
    options: ActivityOptionsCompat?,
    callback: OnReceiveUriResult
  ) {
    this.onPickContactResult = callback
    pickContactLauncher?.launch(null, options)
  }

  /**
   * Pick Visual Media
   */
  private var pickVisualMediaLauncher: ActivityResultLauncher<PickVisualMediaRequest>? = null

  private var onPickVisualMediaResult: OnReceiveUriResult? = null

  private fun registerPickVisualMediaLauncher() {
    val contract = ActivityResultContracts.PickVisualMedia()
    pickVisualMediaLauncher = activity.registerForActivityResult(contract) {
      onPickVisualMediaResult?.invoke(it)
    }
  }

  override fun pickVisualMedia(
    type: VisualMediaType,
    options: ActivityOptionsCompat?,
    callback: OnReceiveUriResult
  ) {
    this.onPickVisualMediaResult = callback
    pickVisualMediaLauncher?.launch(PickVisualMediaRequest(type), options)
  }

  /**
   * Pick Multiple Visual Media
   */
  private var pickMultipleVisualMediaLauncher: ActivityResultLauncher<PickVisualMediaRequest>? =
    null

  private var onPickMultipleVisualMediaResult: OnReceiveListUriResult? = null

  private fun registerPickMultipleVisualMediaLauncher() {
    val contract = ActivityResultContracts.PickMultipleVisualMedia()
    pickMultipleVisualMediaLauncher = activity.registerForActivityResult(contract) {
      onPickMultipleVisualMediaResult?.invoke(it)
    }
  }

  override fun pickMultipleVisualMedia(
    type: VisualMediaType,
    options: ActivityOptionsCompat?,
    callback: OnReceiveListUriResult
  ) {
    this.onPickMultipleVisualMediaResult = callback
    pickMultipleVisualMediaLauncher?.launch(PickVisualMediaRequest(type), options)
  }


  /**
   * Get Content
   */
  private var getContentLauncher: ActivityResultLauncher<String>? = null

  private var onGetContentResult: OnReceiveUriResult? = null

  private fun registerGetContentLauncher() {
    val contract = ActivityResultContracts.GetContent()
    getContentLauncher = activity.registerForActivityResult(contract) {
      onGetContentResult?.invoke(it)
    }
  }

  override fun getContent(
    mimeType: ContentMimeType,
    options: ActivityOptionsCompat?,
    callback: OnReceiveUriResult
  ) {
    this.onGetContentResult = callback
    getContentLauncher?.launch(mimeType.value, options)
  }

  /**
   * Get Multiple Contents
   */
  private var getMultipleContentsLauncher: ActivityResultLauncher<String>? = null

  private var onGetMultipleContentsResult: OnReceiveListUriResult? = null

  private fun registerMultipleGetContentsLauncher() {
    val contract = ActivityResultContracts.GetMultipleContents()
    getMultipleContentsLauncher = activity.registerForActivityResult(contract) {
      onGetMultipleContentsResult?.invoke(it)
    }
  }

  override fun getMultipleContents(
    mimeType: ContentMimeType,
    options: ActivityOptionsCompat?,
    callback: OnReceiveListUriResult
  ) {
    this.onGetMultipleContentsResult = callback
    getMultipleContentsLauncher?.launch(mimeType.value, options)
  }

  /**
   * Open Document
   */
  private var openDocumentLauncher: ActivityResultLauncher<Array<String>>? = null

  private var onOpenDocumentResult: OnReceiveUriResult? = null

  private fun registerOpenDocumentLauncher() {
    val contract = ActivityResultContracts.OpenDocument()
    openDocumentLauncher = activity.registerForActivityResult(contract) {
      onOpenDocumentResult?.invoke(it)
    }
  }

  override fun openDocument(
    mimeType: Array<ContentMimeType>,
    options: ActivityOptionsCompat?,
    callback: OnReceiveUriResult
  ) {
    this.onOpenDocumentResult = callback
    val mimes = mimeType.map { it.value }.toTypedArray()
    openDocumentLauncher?.launch(mimes, options)
  }

  /**
   * Open Multiple Documents
   */
  private var openMultipleDocumentsLauncher: ActivityResultLauncher<Array<String>>? = null

  private var onOpenMultipleDocumentsResult: OnReceiveListUriResult? = null

  private fun registerOpenMultipleDocumentsLauncher() {
    val contract = ActivityResultContracts.OpenMultipleDocuments()
    openMultipleDocumentsLauncher = activity.registerForActivityResult(contract) {
      onOpenMultipleDocumentsResult?.invoke(it)
    }
  }

  override fun openMultipleDocument(
    mimeType: Array<ContentMimeType>,
    options: ActivityOptionsCompat?,
    callback: OnReceiveListUriResult
  ) {
    this.onOpenMultipleDocumentsResult = callback
    val mimes = mimeType.map { it.value }.toTypedArray()
    openMultipleDocumentsLauncher?.launch(mimes, options)
  }

  /**
   * Open Document Tree
   */
  private var openDocumentTreeLauncher: ActivityResultLauncher<Uri?>? = null

  private var onOpenDocumentTreeResult: OnReceiveUriResult? = null

  private fun registerOpenDocumentTreeLauncher() {
    val contract = ActivityResultContracts.OpenDocumentTree()
    openDocumentTreeLauncher = activity.registerForActivityResult(contract) {
      onOpenDocumentTreeResult?.invoke(it)
    }
  }

  override fun openDocumentTree(
    uri: Uri,
    options: ActivityOptionsCompat?,
    callback: OnReceiveUriResult
  ) {
    this.onOpenDocumentTreeResult = callback
    openDocumentTreeLauncher?.launch(uri, options)
  }
}