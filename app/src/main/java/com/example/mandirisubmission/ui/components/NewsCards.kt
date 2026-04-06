package com.example.mandirisubmission.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.mandirisubmission.network.model.Article
import com.example.mandirisubmission.ui.theme.ExpressiveLargeShape
import com.example.mandirisubmission.ui.theme.ExpressiveMediumShape

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeaturedNewsCard(article: Article, onClick: (Article) -> Unit) {
    Card(
        onClick = { onClick(article) },
        shape = ExpressiveLargeShape,
        modifier = Modifier.fillMaxSize(),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerHigh)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            AsyncImage(
                model = article.urlToImage,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.85f)),
                            startY = 400f
                        )
                    )
            )
            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(24.dp)
            ) {
                Text(
                    text = article.title,
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        fontFamily = FontFamily.Serif,
                        lineHeight = 28.sp
                    ),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = article.source.name.uppercase(),
                    style = MaterialTheme.typography.labelMedium.copy(
                        color = MaterialTheme.colorScheme.primaryContainer,
                        letterSpacing = 2.sp,
                        fontWeight = FontWeight.Black
                    )
                )
            }
        }
    }
}

@Composable
fun ExpressiveNewsCard(article: Article, onClick: (Article) -> Unit) {
    val subheadline = remember(article.description) {
        val desc = article.description ?: ""
        val sentences = desc.split(Regex("(?<=[.!?])\\s+"))
        sentences.take(2).joinToString(" ").trim()
    }

    Surface(
        onClick = { onClick(article) },
        color = Color.Transparent,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 24.dp, horizontal = 24.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = article.source.name.uppercase(),
                    style = MaterialTheme.typography.labelSmall.copy(
                        letterSpacing = 1.5.sp,
                        fontWeight = FontWeight.Black,
                        color = MaterialTheme.colorScheme.secondary
                    )
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = article.title,
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.ExtraBold,
                        lineHeight = 28.sp,
                        fontFamily = FontFamily.Serif
                    ),
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
                if (subheadline.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = subheadline,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            lineHeight = 22.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        ),
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

            if (!article.urlToImage.isNullOrBlank()) {
                AsyncImage(
                    model = article.urlToImage,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(110.dp)
                        .clip(ExpressiveMediumShape)
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                )
            }
        }
    }
}
