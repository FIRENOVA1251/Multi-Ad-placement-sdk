package com.example.clickforcesdktest.data.network

import com.example.clickforcesdktest.data.model.BannerContent
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

class BannerContentDeserializer : JsonDeserializer<BannerContent> {
    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): BannerContent {
        return when {
            json == null || json.isJsonNull -> BannerContent()

            json.isJsonPrimitive -> {
                val content = json.asString.trim()
                if (content.startsWith("<script") || content.startsWith("<div")
                    || content.contains("</") || content.contains("%%")) {
                    // 偵測到 HTML/JS 代碼，當作 `htmlContent`
                    BannerContent(htmlContent = content)
                } else {
                    // 否則當作一般的圖片 URL
                    BannerContent(content)
                }
            }
            json.isJsonObject -> { // 新版：包含 video & image
                val jsonObject = json.asJsonObject
                val video = jsonObject.get("video")?.asString
                val image = jsonObject.get("image")?.asString
                BannerContent(video = video, image = image)
            }
            else -> BannerContent() // 預防例外情況
        }
    }

}