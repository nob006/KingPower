package com.interview.kingpower.module

import com.interview.kingpower.network.RetrofitClient
import com.interview.kingpower.network.PhotoApiService
import com.interview.kingpower.repository.PhotoListRepository
import com.interview.kingpower.viewmodel.PhotoViewModel
import com.interview.kingpower.viewmodel.ToolbarViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val repoModules = module {
    single { PhotoListRepository(get()) }
}

val serviceModules = module {
    single { RetrofitClient().create(PhotoApiService::class.java) }
}

val viewModelModules = module {
    viewModel { ToolbarViewModel() }
    viewModel { PhotoViewModel(get()) }
}