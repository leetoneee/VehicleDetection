package com.bteamcoding.vehicledetection

import android.app.Application
import com.bteamcoding.vehicledetection.core.utils.ContextProvider
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // Khởi tạo ContextProvider với application context
        ContextProvider.init(this)
    }
}