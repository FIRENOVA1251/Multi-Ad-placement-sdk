package com.example.clickforcesdktest.view.manager

import android.content.Context
import com.example.clickforcesdktest.data.model.AdRequest
import com.example.clickforcesdktest.data.model.AdResponse
import com.example.clickforcesdktest.data.network.NetworkModule
import com.example.clickforcesdktest.viewmodel.AdViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

// SDK  APP LifeCycle 控制中樞   在app啟動時初始化
// 管理廣告的初始化  加載展示 與 釋放
// 負責監聽應用程式的生命週期  確保廣告在合適的時機加載或暫停
// 控制廣告快取和重試機制
class AdManager(
    private val adViewModel: AdViewModel
) {

    private var adResponse: AdResponse? = null

    //載入廣告
    fun loadAds(adRequest: AdRequest) {

        println("Start loading Ad.")

        adViewModel.loadAd(adRequest)

        println("Ad loaded successfully.")

    }

    // 檢查廣告是否已載入
    fun isAdLoaded(): Boolean {

        return adResponse != null
    }

    // 釋放廣告資源
    fun releaseAd() {
        adResponse = null
        println("Ad resources released.")
    }

}