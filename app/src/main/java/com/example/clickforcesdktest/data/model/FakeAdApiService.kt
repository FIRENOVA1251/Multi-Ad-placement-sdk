//package com.example.clickforcesdktest.data.model
//
//import com.example.clickforcesdktest.data.network.AdApiService
//
//class FakeAdApiService : AdApiService {
//
//    override suspend fun fetchAd(
//        zoneId: String,
//        latitude: String,
//        longitude: String,
//        appId: String,
//        orientation: String,
//        deviceType: String,
//        idfa: String,
//        mfidfa: String,
//        deviceNumber: String,
//        network: String,
//        width: String,
//        height: String,
//        dpi: String,
//        inventoryId: String,
//        sdkVersion: String,
//        osName: String,
//        osVersion: String,
//        osTimestamp: String,
//        osModel: String,
//        country: String,
//        deviceStorage: String
//    ): AdResponse {
//        // 模擬後端回傳的 JSON 結構 (外層 + item + zone)
//        return AdResponse(
//            // 最外層
//            result = 1,
//            errorMsg = "",
//            price = "0",
//            currency = "usd",
//
//            // item 物件
//            item = AdItem(
//                p = "21958:166503:260349:b095a7e90c68...",
//                sizeId = "1",
//                impTrack = "https://ad.holmesmind.com/adserver/i?ut=1736144144...",
//                // 假裝後端給的是字串 "1"
//                bannerType = "1",
//                bannerUrlType = 2,
//                bannerUrl = "https://images.pexels.com/photos/209037/pexels-photo-209037.jpeg",
//                mediaType = "Image",
//                bannerContent = "https://cdn.holmesmind.com/image/30680/6f9f7ec4...",
//                icon = "",
//                iconDisplay = 1,
//                iconUrl = "https://fake-cdn.com/fake-icon.png",
//                hasTrack = 0,
//                clickMarco = "0",
//                appID = "",
//                trackUrl = "https://ad.holmesmind.com/adserver/d?tid=1&p=21958:166503:260349...",
//                thirdPartyTrack = "0",
//                thirdPartyTrackUrl = "https://ad.holmesmind.com/adserver/t?p=21958:166503:260349...",
//
//                // zone 物件
//                zone = AdZone(
//                    interstitialAd = 0
//                )
//            )
//        )
//    }
//
//    override suspend fun trackImpression(ut: String, p: String): TrackResponse {
//        TODO("Not yet implemented")
//    }
//
//    override suspend fun trackClick(p: String, dest: String): TrackResponse {
//        TODO("Not yet implemented")
//    }
//}
//
//
//
