package com.example.clickforcesdktest.data.model

data class AdRequest(
    val zoneId: String,
    val latitude: String = "0",
    val longitude: String = "0",
    val appId: String,
    val osType: String,
    val deviceType: String,
    val idfa: String = "00000000-0000-0000-0000-000000000000",
    val mfidfa: String,
    val deviceNumber: String = "1",
    val network: String,
    val width: String,
    val height: String,
    val dpi: String,
    val ipsId: String = "0",
    val sdkVersion: String = "3.12.0",
    val osName: String,
    val osVersion: String,
    val osTimestamp: String,
    val osModel: String,
    val country: String,
    val deviceStorage: String
)
