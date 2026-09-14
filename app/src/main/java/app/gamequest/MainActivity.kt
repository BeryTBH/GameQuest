package app.gamequest

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.gamequest.components.LoadingScreen
import app.gamequest.components.LoginScreen
import app.gamequest.components.QrCodeImage
import app.gamequest.components.QrLoginScreen
import app.gamequest.ui.theme.PluviaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            var isSteamConnected by remember { mutableStateOf(SteamService.isRunning) }
            onConnectedCallback = {
                isSteamConnected = true
            }
            SteamService.addOnConnectedListener(onConnectedCallback!!)
            startSteamService()

            PluviaTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    if (isSteamConnected)
                        LoginScreen(innerPadding = innerPadding)
                    else
                        LoadingScreen(innerPadding)
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        SteamService.removeOnConnectedListener(onConnectedCallback!!)
    }

    private fun startSteamService() {
        val intent = Intent(this, SteamService::class.java)
        startService(intent)
    }
}
