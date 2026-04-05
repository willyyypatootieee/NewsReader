package com.example.mandirisubmission.ui

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.material3.carousel.HorizontalMultiBrowseCarousel
import androidx.compose.material3.carousel.rememberCarouselState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.mandirisubmission.network.model.Article


private val ExpressiveLargeShape = RoundedCornerShape(
    topStart = 48.dp,
    topEnd = 16.dp,
    bottomEnd = 48.dp,
    bottomStart = 16.dp
)

private val ExpressiveMediumShape = RoundedCornerShape(24.dp)

private val ExpressiveEasing = CubicBezierEasing(0.4f, 0.0f, 0.2f, 1.0f)

@Composable
fun NewsApp(articles: List<Article>) {
    var selectedArticle by remember { mutableStateOf<Article?>(null) }

    AnimatedContent(
        targetState = selectedArticle,
        transitionSpec = {
            if (targetState != null) {
                (fadeIn(animationSpec = tween(400, easing = ExpressiveEasing)) +
                        slideInVertically(
                            initialOffsetY = { it / 3 },
                            animationSpec = tween(600, easing = ExpressiveEasing)
                        ) +
                        expandVertically(animationSpec = tween(600, easing = ExpressiveEasing))
                ).togetherWith(
                    fadeOut(animationSpec = tween(200))
                )
            } else {
                (fadeIn(animationSpec = tween(200, delayMillis = 100))
                ).togetherWith(
                    fadeOut(animationSpec = tween(400, easing = ExpressiveEasing)) +
                            slideOutVertically(
                                targetOffsetY = { it / 3 },
                                animationSpec = tween(500, easing = ExpressiveEasing)
                            ) +
                            shrinkVertically(animationSpec = tween(500, easing = ExpressiveEasing))
                )
            }
        },
        label = "ExpressiveMotion"
    ) { targetArticle ->
        if (targetArticle == null) {
            NewsFeedScreen(articles = articles, onArticleClick = { selectedArticle = it })
        } else {
            NewsReaderScreen(article = targetArticle, onBack = { selectedArticle = null })
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsFeedScreen(articles: List<Article>, onArticleClick: (Article) -> Unit) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            LargeTopAppBar(
                title = {
                    Text(
                        "The Daily",
                        style = MaterialTheme.typography.displayMedium.copy(
                            fontWeight = FontWeight.Black,
                            fontFamily = FontFamily.Serif,
                            letterSpacing = (-1).sp
                        )
                    )
                },
                scrollBehavior = scrollBehavior,
                colors = TopAppBarDefaults.largeTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    scrolledContainerColor = MaterialTheme.colorScheme.surfaceContainer
                )
            )
        }
    ) { innerPadding ->
        LazyColumn(
            contentPadding = innerPadding,
            modifier = Modifier.fillMaxSize()
        ) {
            if (articles.isNotEmpty()) {
                item {
                    Text(
                        "TOP STORIES",
                        modifier = Modifier.padding(start = 24.dp, top = 24.dp, bottom = 12.dp),
                        style = MaterialTheme.typography.labelLarge.copy(
                            letterSpacing = 3.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    )
                    
                    val featuredArticles = articles.take(5)
                    val carouselState = rememberCarouselState { featuredArticles.count() }
                    
                    HorizontalMultiBrowseCarousel(
                        state = carouselState,
                        preferredItemWidth = 340.dp,
                        itemSpacing = 16.dp,
                        contentPadding = PaddingValues(horizontal = 24.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp)
                    ) { index ->
                        val article = featuredArticles[index]
                        FeaturedNewsCard(article, onArticleClick)
                    }
                    
                    Spacer(modifier = Modifier.height(32.dp))
                    HorizontalDivider(
                        modifier = Modifier.padding(horizontal = 24.dp),
                        thickness = 1.dp,
                        color = MaterialTheme.colorScheme.outline.copy(alpha = 0.1f)
                    )
                }

                itemsIndexed(articles.drop(5)) { index, article ->
                    ExpressiveNewsCard(article, onArticleClick)
                    if (index < articles.size - 6) {
                        HorizontalDivider(
                            modifier = Modifier.padding(horizontal = 24.dp),
                            thickness = 0.5.dp,
                            color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)
                        )
                    }
                }
            }
        }
    }
}

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsReaderScreen(article: Article, onBack: () -> Unit) {
    val scrollState = rememberScrollState()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    val readingProgress by animateFloatAsState(
        targetValue = if (scrollState.maxValue > 0) scrollState.value.toFloat() / scrollState.maxValue else 0f,
        animationSpec = tween(200, easing = LinearOutSlowInEasing),
        label = "ProgressAnimation"
    )

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            Column {
                TopAppBar(
                    title = {
                        Text(
                            article.source.name,
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.sp
                            )
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = onBack) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                        }
                    },
                    actions = {
                        IconButton(onClick = { /* Share */ }) {
                            Icon(Icons.Default.Share, contentDescription = "Share")
                        }
                    },
                    scrollBehavior = scrollBehavior
                )
                
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                        .background(MaterialTheme.colorScheme.surfaceContainerLow)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(readingProgress)
                            .fillMaxHeight()
                            .clip(RoundedCornerShape(topEnd = 8.dp, bottomEnd = 8.dp))
                            .background(
                                Brush.horizontalGradient(
                                    colors = listOf(
                                        MaterialTheme.colorScheme.primary,
                                        MaterialTheme.colorScheme.tertiary,
                                        MaterialTheme.colorScheme.secondary
                                    )
                                )
                            )
                    )
                }
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(padding)
        ) {
            // Title & Meta Section
            Column(modifier = Modifier.padding(horizontal = 28.dp, vertical = 32.dp)) {
                Text(
                    text = article.title,
                    style = MaterialTheme.typography.displaySmall.copy(
                        fontWeight = FontWeight.Black,
                        fontFamily = FontFamily.Serif,
                        lineHeight = 46.sp,
                        letterSpacing = (-0.5).sp
                    )
                )
                
                Spacer(modifier = Modifier.height(28.dp))
                
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(ExpressiveMediumShape)
                            .background(MaterialTheme.colorScheme.primaryContainer)
                    ) {
                        Text(
                            text = (article.author ?: article.source.name).take(1).uppercase(),
                            modifier = Modifier.align(Alignment.Center),
                            style = MaterialTheme.typography.headlineSmall,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text(
                            text = article.author ?: "Staff Reporter",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.ExtraBold)
                        )
                        Text(
                            text = article.publishedAt.take(10),
                            style = MaterialTheme.typography.bodySmall.copy(letterSpacing = 1.sp),
                            color = MaterialTheme.colorScheme.outline
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(32.dp))
                HorizontalDivider(thickness = 2.dp, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.05f))
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp)
                    .graphicsLayer {
                        translationY = scrollState.value * 0.15f
                    }
                    .clip(RoundedCornerShape(bottomStart = 56.dp, bottomEnd = 56.dp))
            ) {
                if (!article.urlToImage.isNullOrBlank()) {
                    AsyncImage(
                        model = article.urlToImage,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
            
            Text(
                text = "FEATURED IMAGE • ${article.source.name.uppercase()}",
                modifier = Modifier.padding(horizontal = 28.dp, vertical = 16.dp),
                style = MaterialTheme.typography.labelSmall.copy(letterSpacing = 1.5.sp),
                color = MaterialTheme.colorScheme.outline
            )

            Column(modifier = Modifier.padding(horizontal = 28.dp, vertical = 24.dp)) {
                val cleanContent = (article.content ?: article.description ?: "Full coverage available at source.")
                    .substringBeforeLast("[")

                Text(
                    text = cleanContent,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontSize = 21.sp,
                        lineHeight = 38.sp,
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.Normal
                    ),
                    textAlign = TextAlign.Start
                )

                Spacer(modifier = Modifier.height(32.dp))
                
                Text(
                    text = article.description ?: "",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontSize = 21.sp,
                        lineHeight = 38.sp,
                        fontFamily = FontFamily.Serif,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )
                )
                
                Spacer(modifier = Modifier.height(80.dp))
            }
        }
    }
}
