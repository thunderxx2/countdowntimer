package com.example

import android.app.Application
import com.example.assignmentacura.TimerViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class MainApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MainApplication)
            modules(viewModelModule)
        }
    }

    private val viewModelModule = module {
        viewModel{ TimerViewModel() }
    }
}