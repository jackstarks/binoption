package com.newoption.binatraderapps

import android.app.Application
import com.onesignal.OneSignal

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        OneSignal.initWithContext(this)
        OneSignal.setAppId(getString(R.string.onesignal_id))
    }
}


