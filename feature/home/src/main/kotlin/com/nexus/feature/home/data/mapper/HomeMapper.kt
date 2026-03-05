package com.nexus.feature.home.data.mapper

import com.nexus.feature.home.data.remote.dto.AccountDto
import com.nexus.feature.home.data.remote.dto.TransactionDto
import com.nexus.feature.home.domain.model.Account
import com.nexus.feature.home.domain.model.AccountDisplayType
import com.nexus.feature.home.domain.model.Transaction
import com.nexus.feature.home.domain.model.TransactionCategory
import com.nexus.feature.home.domain.model.TransactionType
import java.math.BigDecimal
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

private val dateFormatter = DateTimeFormatter.ISO_DATE_TIME

fun AccountDto.toDomain() = Account(
    id = id,
    ownerName = ownerName,
    accountNumber = accountNumber,
    agency = agency,
    balance = BigDecimal.valueOf(balance),
    accountType = when (accountType.lowercase()) {
        "checking" -> AccountDisplayType.CHECKING
        else -> AccountDisplayType.DIGITAL
    }
)

fun TransactionDto.toDomain() = Transaction(
    id = id,
    description = description,
    amount = BigDecimal.valueOf(amount),
    type = when (type.uppercase()) {
        "CREDIT" -> TransactionType.CREDIT
        else -> TransactionType.DEBIT
    },
    category = runCatching { TransactionCategory.valueOf(category.uppercase()) }
        .getOrDefault(TransactionCategory.OTHER),
    dateTime = runCatching { LocalDateTime.parse(dateTime, dateFormatter) }
        .getOrDefault(LocalDateTime.now())
)
