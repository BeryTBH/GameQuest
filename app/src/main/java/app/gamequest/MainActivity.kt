package app.gamequest

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import app.gamequest.components.LoggedInScreen
import app.gamequest.components.LoginScreen
import app.gamequest.events.SteamEvent
import app.gamequest.ui.theme.PluviaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            var isLoggedIn by remember { mutableStateOf(false) }
            LaunchedEffect("") {
                SteamService.events.on<SteamEvent.LogonEnded> { isLoggedIn = it.success }
            }

            PluviaTheme {
                if (isLoggedIn)
                    LoggedInScreen()
                else
                    LoginScreen()
            }
        }
    }
}
