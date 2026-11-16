package com.zubi.client

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.zubi.client.ui.MainViewModel
import com.zubi.client.ui.theme.ZubiTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ZubiTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    val vm: MainViewModel = viewModel()
                    val gear by vm.gearText.collectAsStateWithLifecycle()
                    MainScreen(gear = gear)
                }
            }
        }
    }
}

@Composable
fun MainScreen(gear: String) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(text = "Zubi", style = MaterialTheme.typography.displayLarge)
        Text(text = "기어: $gear", style = MaterialTheme.typography.headlineMedium)
        Text(text = "API: ${BuildConfig.API_BASE_URL}", style = MaterialTheme.typography.bodyLarge)
    }
}

@Preview(widthDp = 1280, heightDp = 720)
@Composable
private fun MainScreenPreview() {
    ZubiTheme { MainScreen(gear = "GEAR_PARK") }
}
