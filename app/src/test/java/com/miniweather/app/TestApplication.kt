package com.miniweather.app

import com.miniweather.di.AppComponent
import com.miniweather.di.DaggerTestAppComponent

class TestApplication : BaseApplication() {

    private lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerTestAppComponent
            .factory()
            .create(this)
    }

    override fun getAppComponent(): AppComponent {
        return appComponent
    }

    override fun getBaseUrlProvider(): BaseUrlProvider {
        return object : BaseUrlProvider {

            override fun getBaseWeatherUrl(): String {
                return "http://localhost/"
            }

            override fun getBaseImageUrl(): String {
                return "http://localhost/"
            }
        }
    }

}
