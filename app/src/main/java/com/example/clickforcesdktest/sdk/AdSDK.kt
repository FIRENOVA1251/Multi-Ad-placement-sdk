package com.example.clickforcesdktest.sdk

import android.content.Context
import com.example.clickforcesdktest.data.model.AdRequest
import com.example.clickforcesdktest.view.manager.AdManager
import com.example.clickforcesdktest.viewmodel.AdViewModel

// Public API (initialize, showAd, etc...)

// initialize(context)
// loadAd(adUnitId)
// isAdLoaded(adUnitId)
// showAd (adUnitId)

object AdSdk {
    private var adManager: AdManager? = null
    private var adViewModel: AdViewModel? = null

    // 初始化 SDK
    fun init(context: Context, viewModel: AdViewModel) {
        if (adManager == null) {
            synchronized(this) {
                if (adManager == null) {
                    adManager = AdManager(viewModel)
                    adViewModel = viewModel
                }
            }
        }

    }

    // 載入廣告 (載入完成後會直接顯示)
    //TODO: parameters not done .
    fun loadAds(adRequest: AdRequest) {

        adManager?.loadAds(adRequest)
    }

    // 檢查廣告是否已載入
    fun isAdLoaded(): Boolean {
        return adViewModel?.adResponseState?.value != null
    }

    // 釋放廣告資源
    fun release() {
        adManager?.releaseAd()
        adManager = null
        adViewModel = null
    }
}
