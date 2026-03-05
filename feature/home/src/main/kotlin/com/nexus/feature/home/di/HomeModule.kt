package com.nexus.feature.home.di

import com.nexus.feature.home.data.remote.HomeApi
import com.nexus.feature.home.data.repository.HomeRepositoryImpl
import com.nexus.feature.home.domain.repository.HomeRepository
import com.nexus.feature.home.domain.usecase.GetAccountUseCase
import com.nexus.feature.home.domain.usecase.GetTransactionsUseCase
import com.nexus.feature.home.presentation.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import retrofit2.Retrofit

val homeModule = module {
    // ── Data ─────────────────────────────────────────────────────────────────
    single { get<Retrofit>().create(HomeApi::class.java) }
    singleOf(::HomeRepositoryImpl) { bind<HomeRepository>() }

    // ── Domain ────────────────────────────────────────────────────────────────
    singleOf(::GetAccountUseCase)
    singleOf(::GetTransactionsUseCase)

    // ── Presentation ──────────────────────────────────────────────────────────
    viewModelOf(::HomeViewModel)
}
