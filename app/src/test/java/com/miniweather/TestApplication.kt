package com.miniweather

import com.miniweather.di.AppComponent
import com.miniweather.di.DaggerTestAppComponent
import com.miniweather.di.TestAppModule

class TestApplication : BaseDaggerApplication() {

    private lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerTestAppComponent.builder()
            .testAppModule(TestAppModule(this))
            .build()
    }

    override fun getAppComponent(): AppComponent {
        return appComponent
    }
}
