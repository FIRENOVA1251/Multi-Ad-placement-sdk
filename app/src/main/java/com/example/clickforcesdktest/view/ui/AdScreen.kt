package com.example.clickforcesdktest.view.ui

import com.example.clickforcesdktest.viewmodel.AdViewModel
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState

// 整合顯示廣告的螢幕 （optional）
@Composable
fun AdScreen(viewModel: AdViewModel, adSizeType: String) {

    val adResponse = viewModel.adResponseState.collectAsState().value

    // 若 adResponse 仍是 null，顯示 No response
    if (adResponse?.item == null) {
        Text(text = "No response ")
    } else {
        // 依照 ad bannerType 分流至不同廣告 Composable
        when (adResponse.item.bannerType) {
            "1" -> {
                BannerAdComposable(viewModel, adResponse, adSizeType)

            }

            "2" -> {
                //AnimatedFlipBanner(viewModel, adResponse, adSizeType)

            }

            "5" -> {
                //Full Ad banner
                FullBanner(viewModel,  adResponse)
            }

            "6" -> {
                // 可能是一個全螢幕插播廣告 Composable
                // InterstitialAdComposable(adResponse)
            }

            "11" -> {
                // 摩天影音+圖
                // Video 16:9, Image 300 x 80 共 300 x 250
                MultiMediaTowerAd(adResponse = adResponse)
            }

            "38" -> {
                AdWebView(viewModel, adResponse)

            }

            else -> {
                Text(text = "Unknown Ad Type")
            }
        }
    }
}