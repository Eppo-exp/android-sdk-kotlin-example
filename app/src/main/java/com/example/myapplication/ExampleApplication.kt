package com.example.myapplication

import android.app.Application
import android.util.Log
import cloud.eppo.android.EppoClient
import cloud.eppo.android.InitializationCallback

class ExampleApplication : Application() {
  override fun onCreate() {
    super.onCreate()
    EppoClient.Builder()
      .application(this)
      .apiKey(API_KEY)
      .isGracefulMode(false)
      .assignmentLogger { assignment ->
        Log.d(
          TAG,
          "${assignment.experiment}-> subject: ${assignment.subject} assigned to ${assignment.experiment}"
        )
      }
      .callback(object : InitializationCallback {
        override fun onCompleted() {
          Log.d(TAG, "Eppo SDK initialized")
        }

        override fun onError(errorMessage: String?) {
          throw RuntimeException("Unable to initialize. Ensure you API key is set correctly in your application")
        }
      })
      .buildAndInit()
  }

  companion object {
    private const val API_KEY = "REPLACE_THI_WITH_YOUR_EPPO_SDK_KEY"
    private const val TAG = "ExampleApplication"
  }
}