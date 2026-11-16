package com.zubi.client

import android.app.Application
import com.zubi.client.vehicle.VehicleManager

class ZubiApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        VehicleManager.init(this)
    }

    override fun onTerminate() {
        VehicleManager.release()
        super.onTerminate()
    }
}
