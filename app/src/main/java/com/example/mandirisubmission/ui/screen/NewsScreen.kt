package com.example.mandirisubmission.ui.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.mandirisubmission.ui.components.NewsItem
import com.example.mandirisubmission.viewmodel.NewsViewModel

@Composable
fun NewsScreen(viewModel: NewsViewModel) {
    val articles = viewModel.newsList
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(articles) { article ->
            NewsItem(article = article)
        }
    }
}