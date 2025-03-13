package com.example.clickforcesdktest.view.ui

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.clickforcesdktest.R
import com.example.clickforcesdktest.data.model.AdResponse
import com.example.clickforcesdktest.viewmodel.AdViewModel

@Composable
fun FullBanner(viewModel: AdViewModel, adResponse: AdResponse) {

    val context = LocalContext.current

    // 狀態控制是否顯示廣告
    var isAdVisible by remember { mutableStateOf(true) }

    if (isAdVisible) {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.8f)),
            contentAlignment = Alignment.Center
        ) {
            // Ad content centered in the screen
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(320f / 480f)
                    .background(Color.White)
                    .clickable {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(adResponse.item?.bannerUrl))
                        context.startActivity(intent)
                    }
            ) {
                Image(
                    painter = rememberAsyncImagePainter(adResponse.item?.bannerContent),
                    contentDescription = "Banner Image",
                    modifier = Modifier
                        .matchParentSize(),
                    contentScale = ContentScale.Crop
                )
            }

            // Close button at the top-right corner
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.TopEnd
            ) {
                Image(
                    painter = rememberAsyncImagePainter(R.drawable.close_btn),
                    contentDescription = "Close button",
                    modifier = Modifier
                        .width(50.dp)
                        .height(50.dp)
                        .clip(CircleShape)

                        .clickable { isAdVisible = false }
                )
            }


            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.BottomEnd
            ) {
                Image(
                    painter = rememberAsyncImagePainter("https://cdn.holmesmind.com/cf.png"),
                    contentDescription = "CF Logo",
                    modifier = Modifier
                        .width(23.dp)
                        .height(20.dp)
                        .align(Alignment.BottomEnd)
                        .clickable {
                            val intent =
                                Intent(Intent.ACTION_VIEW, Uri.parse(adResponse.item?.iconUrl))
                            context.startActivity(intent)
                        }
                )
            }
        }

        LaunchedEffect(key1 = adResponse.item?.p) {
            adResponse.item?.p?.let { adId ->
                viewModel.sendImpressionWithTimestamp(adId)
            } ?: run {
                println("Ad ID (p) is null, impression not sent")
            }
        }
    }
}


// 21951



