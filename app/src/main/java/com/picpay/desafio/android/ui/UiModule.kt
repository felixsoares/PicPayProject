package com.picpay.desafio.android.ui

import com.picpay.desafio.android.data.repository.UserRepository
import com.picpay.desafio.android.ui.home.HomeViewModel
import com.picpay.desafio.android.widget.UserListAdapter
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val uiModule = module {
    viewModel { HomeViewModel(get<UserRepository>()) }
    single { UserListAdapter() }
}