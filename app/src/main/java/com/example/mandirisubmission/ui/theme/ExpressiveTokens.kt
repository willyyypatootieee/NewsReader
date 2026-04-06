package com.example.mandirisubmission.ui.theme

import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.unit.dp

val ExpressiveLargeShape = RoundedCornerShape(
    topStart = 48.dp,
    topEnd = 16.dp,
    bottomEnd = 48.dp,
    bottomStart = 16.dp
)

val ExpressiveMediumShape = RoundedCornerShape(24.dp)

val ExpressiveEasing = CubicBezierEasing(0.4f, 0.0f, 0.2f, 1.0f)
