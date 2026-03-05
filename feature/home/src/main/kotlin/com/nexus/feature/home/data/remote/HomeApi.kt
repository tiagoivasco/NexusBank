package com.nexus.feature.home.data.remote

import com.nexus.feature.home.data.remote.dto.AccountDto
import com.nexus.feature.home.data.remote.dto.TransactionDto
import retrofit2.http.GET

interface HomeApi {
    @GET("account/me")
    suspend fun getAccount(): AccountDto

    @GET("transactions/recent")
    suspend fun getRecentTransactions(): List<TransactionDto>
}
