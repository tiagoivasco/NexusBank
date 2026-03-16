package com.nexus.feature.home.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AccountDto(
    @SerialName("id") val id: String,
    @SerialName("owner_name") val ownerName: String,
    @SerialName("account_number") val accountNumber: String,
    @SerialName("agency") val agency: String,
    @SerialName("balance") val balance: Double,
    @SerialName("account_type") val accountType: String
)

@Serializable
data class TransactionDto(
    @SerialName("id") val id: String,
    @SerialName("description") val description: String,
    @SerialName("amount") val amount: Double,
    @SerialName("type") val type: String,
    @SerialName("category") val category: String,
    @SerialName("date_time") val dateTime: String
)
