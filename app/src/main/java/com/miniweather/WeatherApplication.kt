package com.miniweather

import com.miniweather.di.AppComponent
import com.miniweather.di.AppModule
import com.miniweather.di.DaggerAppComponent

class WeatherApplication : BaseDaggerApplication() {

    private lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
    }

    override fun getAppComponent(): AppComponent {
        return appComponent
    }
}
