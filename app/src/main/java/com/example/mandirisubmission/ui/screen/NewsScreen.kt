package com.example.mandirisubmission.ui.screen

import androidx.compose.runtime.Composable
import com.example.mandirisubmission.ui.NewsApp
import com.example.mandirisubmission.viewmodel.NewsViewModel

@Composable
fun NewsScreen(viewModel: NewsViewModel) {
    val articles = viewModel.newsList
    NewsApp(articles = articles)
}