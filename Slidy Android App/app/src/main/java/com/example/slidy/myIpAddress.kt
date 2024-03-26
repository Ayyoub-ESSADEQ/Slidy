package com.example.slidy

import android.content.Context
import android.net.wifi.WifiManager
import android.text.format.Formatter

fun getIpAddress(applicationContext: Context): String {
    val wifiManager = applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
    return Formatter.formatIpAddress(wifiManager.connectionInfo.ipAddress)
}
