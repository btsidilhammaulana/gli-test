package com.gli.test.base.launcher

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri

typealias OnReceiveActivityResult = (resultCode: Int, data: Intent?) -> Unit
typealias OnReceivePermissionsResult = (result: Map<String, Boolean>) -> Unit
typealias OnReceiveBitmapResult = (result: Bitmap?) -> Unit
typealias OnReceiveStateResult = (state: Boolean) -> Unit
typealias OnReceiveStateUriResult = (state: Boolean, result: Uri?) -> Unit
typealias OnReceiveUriResult = (result: Uri?) -> Unit
typealias OnReceiveListUriResult = (result: List<Uri>?) -> Unit

enum class AppLauncherType {
  ACTIVITY_RESULT,
  INTENT_SENDER,
  REQUEST_PERMISSIONS,
  TAKE_PICTURE_PREVIEW,
  TAKE_PICTURE,
  CAPTURE_VIDEO,
  PICK_CONTACT,
  PICK_VISUAL_MEDIA,
  PICK_MULTIPLE_VISUAL_MEDIA,
  GET_CONTENT,
  GET_MULTIPLE_CONTENTS,
  OPEN_DOCUMENT,
  OPEN_MULTIPLE_DOCUMENTS,
  OPEN_DOCUMENT_TREE
}

enum class ContentMimeType(val value: String) {
  APPLICATION_ALL("application/*"),
  APPLICATION_PDF("application/pdf"),
  APPLICATION_ZIP("application/zip"),
  APPLICATION_JSON("application/json"),
  APPLICATION_XML("application/xml"),
  APPLICATION_OCTET_STREAM("application/octet-stream"),
  APPLICATION_MS_EXCEL("application/vnd.ms-excel"),
  APPLICATION_MS_POWERPOINT("application/vnd.ms-powerpoint"),
  APPLICATION_MS_WORD("application/msword"),
  APPLICATION_ANDROID_PACKAGE("application/vnd.android.package-archive"),
  AUDIO_ALL("audio/*"),
  AUDIO_MPEG("audio/mpeg"),
  AUDIO_WAV("audio/x-wav"),
  AUDIO_OGG("audio/ogg"),
  AUDIO_AAC("audio/aac"),
  AUDIO_FLAC("audio/flac"),
  IMAGE_ALL("image/*"),
  IMAGE_JPEG("image/jpeg"),
  IMAGE_PNG("image/png"),
  IMAGE_GIF("image/gif"),
  IMAGE_BMP("image/bmp"),
  IMAGE_WEBP("image/webp"),
  IMAGE_SVG_XML("image/svg+xml"),
  TEXT_ALL("text/*"),
  TEXT_PLAIN("text/plain"),
  TEXT_HTML("text/html"),
  TEXT_CSS("text/css"),
  TEXT_XML("text/xml"),
  TEXT_JAVASCRIPT("text/javascript"),
  VIDEO_ALL("video/*"),
  VIDEO_MP4("video/mp4"),
  VIDEO_3GPP("video/3gpp"),
  VIDEO_WEBM("video/webm"),
  VIDEO_MPEG("video/mpeg"),
  VIDEO_AVI("video/x-msvideo"),
}