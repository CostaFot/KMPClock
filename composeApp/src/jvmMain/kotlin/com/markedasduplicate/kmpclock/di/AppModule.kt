package com.markedasduplicate.kmpclock.di

import com.markedasduplicate.kmpclock.data.PositionRepository
import com.markedasduplicate.kmpclock.platform.NoopWindowStyleHelper
import com.markedasduplicate.kmpclock.platform.WindowStyleHelper
import com.markedasduplicate.kmpclock.platform.WindowsWindowStyleHelper
import com.markedasduplicate.kmpclock.ui.ClockViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    singleOf(::PositionRepository)
    single<WindowStyleHelper> {
        if (System.getProperty("os.name").startsWith("Windows")) {
            WindowsWindowStyleHelper()
        } else {
            NoopWindowStyleHelper
        }
    }
    viewModelOf(::ClockViewModel)
}
