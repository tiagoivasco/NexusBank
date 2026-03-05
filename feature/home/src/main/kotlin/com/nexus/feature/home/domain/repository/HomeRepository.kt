package com.nexus.feature.home.domain.repository

import com.nexus.core.domain.Result
import com.nexus.feature.home.domain.model.Account
import com.nexus.feature.home.domain.model.Transaction

/**
 * Contract for the home data layer. The domain and presentation layers
 * depend only on this interface — never on the concrete implementation.
 */
interface HomeRepository {
    suspend fun getAccount(): Result<Account>
    suspend fun getRecentTransactions(): Result<List<Transaction>>
}
