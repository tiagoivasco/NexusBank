package com.nexus.feature.home.domain.model

import java.math.BigDecimal

data class Account(
    val id: String,
    val ownerName: String,
    val accountNumber: String,
    val agency: String,
    val balance: BigDecimal,
    val accountType: AccountDisplayType
)

enum class AccountDisplayType {
    CHECKING,
    DIGITAL
}
