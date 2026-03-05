package com.nexus.feature.home.presentation

import com.nexus.feature.home.domain.model.Account
import com.nexus.feature.home.domain.model.Transaction

data class HomeUiState(
    val isLoading: Boolean = true,
    val account: Account? = null,
    val transactions: List<Transaction> = emptyList(),
    val isBalanceVisible: Boolean = true,
    val error: String? = null
)
