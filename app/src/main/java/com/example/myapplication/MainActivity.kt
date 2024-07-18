package com.example.myapplication

import android.os.Bundle
import android.os.Handler
import android.os.Looper
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
import cloud.eppo.ufc.dto.EppoValue
import cloud.eppo.ufc.dto.SubjectAttributes
import com.example.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    // replace the values below with your own experiment and subject keys
    val subjectAttributes = SubjectAttributes(mapOf("companyId" to EppoValue.valueOf("1")))
    val eppoClient = EppoClient.getInstance()
    Handler(Looper.getMainLooper()).postDelayed({
      val assignedVariation =
        eppoClient.getBooleanAssignment("test-flag", "test-subject-key",
          subjectAttributes, false)
      setContent {
        MyApplicationTheme {
          Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            Greeting(
              name = assignedVariation.toString(),
              modifier = Modifier.padding(innerPadding)
            )
          }
        }
      }
    }, 1000)
  }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
  Text(
    text = "Assigned variation: '$name'",
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