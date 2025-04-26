package com.example.myaigenhelper

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.myaigenhelper.features.search.AISearchingScreenView
import com.example.myaigenhelper.ui.theme.MyAIGenHelperTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyAIGenHelperTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier
                        .statusBarsPadding()
                        .fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    AISearchingScreenView()
                }
            }
        }
    }
}