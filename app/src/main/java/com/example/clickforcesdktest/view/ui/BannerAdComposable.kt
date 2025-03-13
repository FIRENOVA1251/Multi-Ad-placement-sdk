package com.example.clickforcesdktest.view.ui

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.clickforcesdktest.data.model.AdResponse
import com.example.clickforcesdktest.viewmodel.AdViewModel

@Composable
fun BannerAdComposable(
    viewModel: AdViewModel,
    adResponse: AdResponse,
    bannerSizeType: String,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(20.dp))
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

            "3" -> BannerBox(
                width = 336.dp,
                height = 280.dp,
                adResponse = adResponse
            )

            "4" -> BannerBox(
                width = 320.dp,
                height = 100.dp,
                adResponse = adResponse
            )

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


// Banner box component
@Composable
fun BannerBox(
    width: Dp,
    height: Dp,
    adResponse: AdResponse
) {
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .width(width)
            .height(height)
            .clickable {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(adResponse.item?.bannerUrl))
                context.startActivity(intent)

                // 點擊統計回傳 應該是要分開處理？ 改api內建了  此func暫時先棄用
                // viewModel.sendClick(adResponse.item?.p!!, "dest")

            }
    ) {
        Image(
            painter = rememberAsyncImagePainter(adResponse.item?.bannerContent?.image),
            contentDescription = "Banner Image",
            modifier = Modifier
                .matchParentSize(),
            contentScale = ContentScale.Crop
        )

        Image(
            painter = rememberAsyncImagePainter("https://cdn.holmesmind.com/cf.png"),
            contentDescription = "CF Logo",
            modifier = Modifier
                .width(23.dp)
                .height(20.dp)
                .align(Alignment.BottomEnd)
                .clickable {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(adResponse.item?.iconUrl))
                    context.startActivity(intent)
                }
        )
    }
}
