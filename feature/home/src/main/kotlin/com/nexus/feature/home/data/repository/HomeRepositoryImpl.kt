package com.nexus.feature.home.data.repository

import com.nexus.core.domain.Result
import com.nexus.feature.home.data.remote.HomeApi
import com.nexus.feature.home.data.mapper.toDomain
import com.nexus.feature.home.domain.model.Account
import com.nexus.feature.home.domain.model.AccountDisplayType
import com.nexus.feature.home.domain.model.Transaction
import com.nexus.feature.home.domain.model.TransactionCategory
import com.nexus.feature.home.domain.model.TransactionType
import com.nexus.feature.home.domain.repository.HomeRepository
import java.math.BigDecimal
import java.time.LocalDateTime

class HomeRepositoryImpl(
    @Suppress("UNUSED_PARAMETER") private val api: HomeApi
) : HomeRepository {

    // ─── TODO: Replace with real API calls once backend is ready ──────────────
    // Real implementation:
    //   override suspend fun getAccount() = runCatching { api.getAccount().toDomain() }
    //       .fold({ Result.Success(it) }, { Result.Error(it) })

    override suspend fun getAccount(): Result<Account> = Result.Success(
        Account(
            id = "acc-001",
            ownerName = "João Silva",
            accountNumber = "12345-6",
            agency = "0001",
            balance = BigDecimal("12450.87"),
            accountType = AccountDisplayType.CHECKING
        )
    )

    override suspend fun getRecentTransactions(): Result<List<Transaction>> = Result.Success(
        listOf(
            Transaction(
                id = "txn-001",
                description = "Netflix",
                amount = BigDecimal("55.90"),
                type = TransactionType.DEBIT,
                category = TransactionCategory.ENTERTAINMENT,
                dateTime = LocalDateTime.now().minusHours(2)
            ),
            Transaction(
                id = "txn-002",
                description = "Salário",
                amount = BigDecimal("8500.00"),
                type = TransactionType.CREDIT,
                category = TransactionCategory.SALARY,
                dateTime = LocalDateTime.now().minusDays(1)
            ),
            Transaction(
                id = "txn-003",
                description = "Supermercado Extra",
                amount = BigDecimal("230.45"),
                type = TransactionType.DEBIT,
                category = TransactionCategory.SHOPPING,
                dateTime = LocalDateTime.now().minusDays(2)
            ),
            Transaction(
                id = "txn-004",
                description = "Pix — Ana Lima",
                amount = BigDecimal("150.00"),
                type = TransactionType.DEBIT,
                category = TransactionCategory.PIX,
                dateTime = LocalDateTime.now().minusDays(3)
            ),
            Transaction(
                id = "txn-005",
                description = "iFood",
                amount = BigDecimal("67.80"),
                type = TransactionType.DEBIT,
                category = TransactionCategory.FOOD,
                dateTime = LocalDateTime.now().minusDays(3)
            ),
            Transaction(
                id = "txn-006",
                description = "Uber",
                amount = BigDecimal("23.50"),
                type = TransactionType.DEBIT,
                category = TransactionCategory.TRANSPORT,
                dateTime = LocalDateTime.now().minusDays(4)
            ),
        )
    )
}
