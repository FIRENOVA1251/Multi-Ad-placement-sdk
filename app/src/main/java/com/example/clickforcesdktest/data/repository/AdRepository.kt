package com.example.clickforcesdktest.data.repository

import com.example.clickforcesdktest.data.network.AdApiService
import com.example.clickforcesdktest.data.model.AdResponse
import com.example.clickforcesdktest.data.model.TrackResponse
import com.google.gson.JsonSyntaxException
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

// 整合多個資料來源
/**
 * AdRepository: 負責對外提供取得廣告資料的介面。
 *  - 從後端獲取廣告內容 (透過 AdApiService)。
 *  - 若日後需要快取、資料庫整合，可在此擴充。
 */
class AdRepository(
    private val adApiService: AdApiService
) {

    /**
     * 非同步呼叫 API 取得廣告資料
     */
    suspend fun fetchAd(
        zoneId: String,
        latitude: String,
        longitude: String,
        appId: String,
        osType: String,
        deviceType: String,
        idfa: String,
        mfidfa: String,
        deviceNumber: String,
        network: String,
        width: String,
        height: String,
        dpi: String,
        ipsId: String,
        sdkVersion: String,
        osName: String,
        osVersion: String,
        osTimestamp: String,
        osModel: String,
        country: String,
        deviceStorage: String,
        retryCount: Int = 2 // 最大重試次數
    ): AdResponse {
        // 為什麼不是return response?

        repeat(retryCount) { attempt ->
            try {
                val response = adApiService.fetchAd(
                    zoneId,
                    latitude,
                    longitude,
                    appId,
                    osType,
                    deviceType,
                    idfa,
                    mfidfa,
                    deviceNumber,
                    network,
                    width,
                    height,
                    dpi,
                    ipsId,
                    sdkVersion,
                    osName,
                    osVersion,
                    osTimestamp,
                    osModel,
                    country,
                    deviceStorage
                )

                // 2. 在這裡做資料檢查或轉換（如有需要）
                //    例如檢查 result 狀態，或對某些欄位做 parsing/過濾
                if (response.result != 1) {
                    // 可根據情境決定是否要 throw Exception、
                    // 回傳自訂錯誤資料模型、或給預設值。
                    throw IllegalStateException("API error: No Ad. ${response.errorMsg}")
                }

                // 回傳的數據
                return response

            } catch (e: SocketTimeoutException) {
                // 處理連線或讀取超時
                println(" Attempt ${attempt + 1} Network timeout, please try again later.")
            } catch (e: UnknownHostException) {
                // 無法解析域名
                throw IOException(
                    "Unable to resolve host. Please check your network connection.",
                    e
                )
            } catch (e: HttpException) {
                // 處理 HTTP 錯誤碼 (如 404, 500)
                throw IOException("Server error: ${e.code()} - ${e.message()}", e)
            } catch (e: JsonSyntaxException) {
                // 解析 JSON 失敗
                throw IOException("Data parsing error. Please contact support.", e)
            } catch (e: IOException) {
                // 網路中斷或其他 I/O 錯誤
                println(" Attempt ${attempt + 1} Network error. Please check your connection.")
            } catch (e: Exception) {
                // 捕捉未知錯誤
                throw IOException("An unexpected error occurred. Please try again.", e)
            }
        }
        throw IOException("Failed to fetch ad after $retryCount attempts")
    }


    suspend fun trackImpression(ut: String, p: String): TrackResponse {
        return try {
            adApiService.trackImpression(ut, p)
        } catch (e: Exception) {
            // 錯誤處理 (或回傳一個失敗的 TrackResponse)
            TrackResponse(false, e.localizedMessage)
        }
    }

//    suspend fun trackClick(p: String, dest: String): TrackResponse {
//        return try {
//            adApiService.trackClick(p, dest)
//        } catch (e: Exception) {
//            TrackResponse(false, e.localizedMessage)
//        }
//    }


}
