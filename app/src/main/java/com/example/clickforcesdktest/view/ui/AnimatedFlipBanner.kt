package com.example.clickforcesdktest.view.ui

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.example.clickforcesdktest.data.model.AdResponse
import com.example.clickforcesdktest.viewmodel.AdViewModel
import kotlinx.coroutines.delay

@Composable
fun AnimatedFlipBanner( viewModel: AdViewModel, adResponse: AdResponse, bannerSizeType: String) {
    // Control animation state.
    var isBannerVisible by remember { mutableStateOf(false) }

    var height: Dp = 100.dp

    if (bannerSizeType == "1") {
        height = 250.dp
    } else if (bannerSizeType == "2") {
        height = 100.dp
    }
    // Y axis relocation
    val translateY by animateDpAsState(
        targetValue = if (isBannerVisible) 0.dp else height, // Initial position
        animationSpec = tween(durationMillis = 1000, easing = LinearOutSlowInEasing), label = ""
    )

    // Rotation angle
    val rotation by animateFloatAsState(
        targetValue = if (isBannerVisible) 0f else -360f, // Complete a rotation.
        animationSpec = tween(durationMillis = 1000, easing = LinearOutSlowInEasing), label = ""
    )

    // 触发动画
    LaunchedEffect(key1 = adResponse.item?.p) {
        // delay(250)
        if (adResponse.item?.bannerContent != null) {
            isBannerVisible = true
        }

        adResponse.item?.p?.let { adId ->
            viewModel.sendImpressionWithTimestamp(adId)
        } ?: run {
            println("Ad ID (p) is null, impression not sent")
        }
    }

    // Banner Component
    if (adResponse.item?.bannerContent != null) {
        Box(
            modifier = Modifier
                .wrapContentWidth()
                .wrapContentHeight()
                // Use Y axis relocation to control animation location
                .offset { IntOffset(0, translateY.roundToPx()) }
                .graphicsLayer(
                    rotationX = rotation // Use X axis rotation to simulate rotation effect.
                )
                .background(Color.Black),
            contentAlignment = Alignment.Center
        ) {
            when (bannerSizeType) {

                "1" -> BannerBox(
                    width = 300.dp,
                    height = 250.dp,
                    adResponse = adResponse
                )

                "2" -> BannerBox(
                    width = 320.dp,
                    height = 50.dp,
                    adResponse = adResponse
                )
            }
        }
    }

}
