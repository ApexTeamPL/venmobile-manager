package io.apexteam.vmanager.ui.activity

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.app.ActivityCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import io.apexteam.vmanager.ui.screen.home.HomeScreen
import io.apexteam.vmanager.ui.screen.installer.InstallerScreen
import io.apexteam.vmanager.ui.theme.VencoreManagerTheme
import io.apexteam.vmanager.utils.DiscordVersion
import io.apexteam.vmanager.utils.Intents
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        val version = intent.getStringExtra(Intents.Extras.VERSION)

        if (checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf("android.permission.POST_NOTIFICATIONS"),
                0
            )
        }

        val screen = if (intent.action == Intents.Actions.INSTALL && version != null) {
            InstallerScreen(DiscordVersion.fromVersionCode(version)!!)
        } else {
            HomeScreen()
        }

        setContent {
            VencoreManagerTheme {
                Navigator(screen) {
                    SlideTransition(it)
                }
            }
        }
    }
}
