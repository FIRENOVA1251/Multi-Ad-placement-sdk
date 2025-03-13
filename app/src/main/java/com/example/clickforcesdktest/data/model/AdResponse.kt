package com.example.clickforcesdktest.data.model

import com.example.clickforcesdktest.data.network.BannerContentDeserializer
import com.google.gson.annotations.JsonAdapter
import com.google.gson.annotations.SerializedName

data class AdResponse(
    @SerializedName("result")
    val result: Int? = null,

    @SerializedName("error_msg")
    val errorMsg: String? = null,

    @SerializedName("price")
    val price: String? = null,

    @SerializedName("cur")
    val currency: String? = null,

    // 新增: 將 "item" map 到 AdItem 這個資料類
    @SerializedName("item")
    val item: AdItem? = null
)

data class AdItem(
    @SerializedName("P")
    val p: String? = null,

    @SerializedName("sizeId")
    val sizeId: String? = null,

    @SerializedName("impTarck")
    val impTrack: String? = null,

    // 注意: 後端回傳的是字串 "1"；您可用 String? 來接
    @SerializedName("bannerType")
    val bannerType: String? = null,

    @SerializedName("bannerUrlType")
    val bannerUrlType: Int? = null,

    @SerializedName("bannerUrl")
    val bannerUrl: String? = null,

    @SerializedName("mediaType")
    val mediaType: String? = null,

    // bannerContent 可能是 `String` 或 `Object`，用 @JsonAdapter 來處理
    @SerializedName("bannerContent")
    @JsonAdapter(BannerContentDeserializer::class)
    val bannerContent: BannerContent? = null,

    @SerializedName("icon")
    val icon: String? = null,

    @SerializedName("iconDisplay")
    val iconDisplay: Int? = null,

    @SerializedName("iconUrl")
    val iconUrl: String? = null,

    @SerializedName("hasTrack")
    val hasTrack: Int? = null,

    @SerializedName("clickMarco")
    val clickMarco: String? = null,

    @SerializedName("appID")
    val appID: String? = null,

    @SerializedName("trackUrl")
    val trackUrl: String? = null,

    @SerializedName("thirdPartyTrack")
    val thirdPartyTrack: String? = null,

    @SerializedName("thirdPartyTrackUrl")
    val thirdPartyTrackUrl: String? = null,

    // 新增: zone 物件
    @SerializedName("zone")
    val zone: AdZone? = null
)

data class AdZone(
    @SerializedName("interstitial_ad")
    val interstitialAd: Int? = null
)

data class BannerContent(
    @SerializedName("video")
    val video: String? = null,
    @SerializedName("image")
    val image: String? = null,

    val htmlContent: String? = null
) {
    constructor(singleUrl: String) : this(image = singleUrl)
}

