package com.nexus.feature.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nexus.core.domain.Result
import com.nexus.feature.home.domain.usecase.GetAccountUseCase
import com.nexus.feature.home.domain.usecase.GetTransactionsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getAccountUseCase: GetAccountUseCase,
    private val getTransactionsUseCase: GetTransactionsUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadHomeData()
    }

    fun loadHomeData() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }

            val accountResult = getAccountUseCase()
            val transactionsResult = getTransactionsUseCase()

            _uiState.update { state ->
                state.copy(
                    isLoading = false,
                    account = (accountResult as? Result.Success)?.data,
                    transactions = (transactionsResult as? Result.Success)?.data ?: emptyList(),
                    error = (accountResult as? Result.Error)?.message
                )
            }
        }
    }

    fun toggleBalanceVisibility() {
        _uiState.update { it.copy(isBalanceVisible = !it.isBalanceVisible) }
    }
}
