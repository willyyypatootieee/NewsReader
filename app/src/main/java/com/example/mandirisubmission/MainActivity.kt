package com.example.mandirisubmission

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mandirisubmission.ui.screen.NewsScreen
import com.example.mandirisubmission.viewmodel.NewsViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface {
                    val newsViewModel: NewsViewModel = viewModel()
                    NewsScreen(viewModel = newsViewModel)
                }
            }
        }
    }
}