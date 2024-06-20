package com.example.myapplication

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import cloud.eppo.android.EppoClient
import cloud.eppo.android.InitializationCallback
import com.example.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      MyApplicationTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
          Greeting(
            name = "Android",
            modifier = Modifier.padding(innerPadding)
          )
        }
      }
    }

    EppoClient.Builder()
      .application(application)
      .apiKey(API_KEY)
      .isGracefulMode(true)
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
    private const val TAG = "MainActivity"
  }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
  Text(
    text = "Hello $name!",
    modifier = modifier
  )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
  MyApplicationTheme {
    Greeting("Android")
  }
}