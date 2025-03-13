package com.example.clickforcesdktest.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.clickforcesdktest.data.model.AdRequest
import com.example.clickforcesdktest.data.model.AdResponse
import com.example.clickforcesdktest.data.network.NetworkModule
import com.example.clickforcesdktest.data.repository.AdRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AdViewModel : ViewModel() {

    private val repository = AdRepository(NetworkModule.adApiService)
    //private val repository = AdRepository(FakeAdApiService())

    // StateFlow：用來暴露給 UI 層觀察
    private val _adResponseState = MutableStateFlow<AdResponse?>(null)
    val adResponseState: StateFlow<AdResponse?> = _adResponseState


    /**
     * 帶入需要的參數給 repository.fetchAd(...)
     * 這樣 ViewModel 不需要硬編在 Repository 內，也不必在 Repository 製造假資料
     */
    fun loadAd(adRequest: AdRequest) {
        viewModelScope.launch {
            try {
                val response = repository.fetchAd(
                    zoneId = adRequest.zoneId,
                    latitude = adRequest.latitude,
                    longitude = adRequest.longitude,
                    appId = adRequest.appId,
                    osType = adRequest.osType,
                    deviceType = adRequest.deviceType,
                    idfa = adRequest.idfa,
                    mfidfa = adRequest.mfidfa,
                    deviceNumber = adRequest.deviceNumber,
                    network = adRequest.network,
                    width = adRequest.width,
                    height = adRequest.height,
                    dpi = adRequest.dpi,
                    ipsId = adRequest.ipsId,
                    sdkVersion = adRequest.sdkVersion,
                    osName = adRequest.osName,
                    osVersion = adRequest.osVersion,
                    osTimestamp = adRequest.osTimestamp,
                    osModel = adRequest.osModel,
                    country = adRequest.country,
                    deviceStorage = adRequest.deviceStorage
                )

                _adResponseState.value = response

                // 依據 adType 或其他東西  做數據的預處理
                when (response.item?.bannerType) {
                    "1" -> {
                        // 更新UI狀態: 顯示 Banner
                        // response.title, response.imageUrl...
                    }

                    "6" -> {
                        // 顯示全屏廣告 interstitial ad
                        // 可能需要 videoUrl、title等
                    }

                    "9" -> {
                        // 原生Native...
                    }

                    else -> {
                        // 未知的 adType
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Log.ERROR
            }
        }
    }

    // Track Impression
    fun sendImpressionWithTimestamp(p: String) {
        val timestamp = System.currentTimeMillis() / 1000 //  Unix time stamp
        viewModelScope.launch {
            repository.trackImpression(timestamp.toString(), p)
        }
    }


    // 追蹤廣告點擊  api已內建 因此此功能暫時棄用
//    fun sendClick(p: String, dest: String) {
//        viewModelScope.launch {
//            val result = repository.trackClick(p, dest)
//            if (result.success == false) {
//                // 處理失敗
//            }
//        }
//    }

}
