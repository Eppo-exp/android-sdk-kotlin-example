package com.example.myapplication

import android.app.Application
import android.util.Log
import cloud.eppo.android.EppoClient

class ExampleApplication : Application() {
  override fun onCreate() {
    super.onCreate()
    EppoClient.Builder(API_KEY, this)
      .isGracefulMode(false)
      .assignmentLogger { assignment ->
        Log.d(
          TAG,
          "${assignment.experiment}-> subject: ${assignment.subject} assigned to ${assignment.experiment}"
        )
      }
      .buildAndInitAsync()
      .thenAccept{ Log.d(TAG, "Eppo SDK initialized") }

  }

  companion object {
    private const val API_KEY = "REPLACE_THIS_WITH_YOUR_EPPO_SDK_KEY"
    private const val TAG = "ExampleApplication"
  }
}