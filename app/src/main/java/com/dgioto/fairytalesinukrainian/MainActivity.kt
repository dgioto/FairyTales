package com.dgioto.fairytalesinukrainian

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.dgioto.fairytalesinukrainian.ui.theme.FairyTalesInUkrainianTheme
import com.dgioto.fairytalesinukrainian.ui.MainScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FairyTalesInUkrainianTheme {
                MainScreen()
            }
        }
    }
}
