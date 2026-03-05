package com.nexus.feature.home.domain.usecase

import com.nexus.core.domain.NoParamsUseCase
import com.nexus.core.domain.Result
import com.nexus.feature.home.domain.model.Account
import com.nexus.feature.home.domain.model.Transaction
import com.nexus.feature.home.domain.repository.HomeRepository

class GetAccountUseCase(
    private val repository: HomeRepository
) : NoParamsUseCase<Account>() {
    override suspend fun execute(): Result<Account> = repository.getAccount()
}

class GetTransactionsUseCase(
    private val repository: HomeRepository
) : NoParamsUseCase<List<Transaction>>() {
    override suspend fun execute(): Result<List<Transaction>> = repository.getRecentTransactions()
}
