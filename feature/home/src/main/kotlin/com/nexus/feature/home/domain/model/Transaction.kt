package com.nexus.feature.home.domain.model

import java.math.BigDecimal
import java.time.LocalDateTime

data class Transaction(
    val id: String,
    val description: String,
    val amount: BigDecimal,
    val type: TransactionType,
    val category: TransactionCategory,
    val dateTime: LocalDateTime
)

enum class TransactionType {
    CREDIT, DEBIT
}

enum class TransactionCategory {
    FOOD, SHOPPING, TRANSPORT, ENTERTAINMENT,
    SALARY, TRANSFER, PIX, BILL, OTHER
}
