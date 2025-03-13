package com.example.clickforcesdktest.data.network

// 發送廣告請求的API介面  定義api  透過retrofit 自動生成網路呼叫
import com.example.clickforcesdktest.data.model.AdResponse
import com.example.clickforcesdktest.data.model.TrackResponse
import retrofit2.http.GET
import retrofit2.http.Query
interface AdApiService {
    @GET("adserver/app.json")
    suspend fun fetchAd(
        @Query("z") zoneId: String,
        @Query("lat") latitude: String,
        @Query("lng") longitude: String,
        @Query("appid") appId: String,
        @Query("o") orientation: String,
        @Query("d") deviceType: String,
        @Query("idfa") idfa: String,
        @Query("mfidfa") mfidfa: String,
        @Query("dn") deviceNumber: String,
        @Query("n") network: String,
        @Query("w") width: String,
        @Query("h") height: String,
        @Query("dpi") dpi: String,
        @Query("i") inventoryId: String,
        @Query("V") sdkVersion: String,
        @Query("osn") osName: String,
        @Query("osv") osVersion: String,
        @Query("osot") osTimestamp: String,
        @Query("osm") osModel: String,
        @Query("osc") country: String,
        @Query("osds") deviceStorage: String
    ): AdResponse


    @GET("adserver/i")
    suspend fun trackImpression(
        @Query("ut") ut: String,
        @Query("p") p: String

    ): TrackResponse

    @GET("adserver/c")
    suspend fun trackClick(
        @Query("p") p: String,
        @Query("dest") dest: String

    ): TrackResponse
}