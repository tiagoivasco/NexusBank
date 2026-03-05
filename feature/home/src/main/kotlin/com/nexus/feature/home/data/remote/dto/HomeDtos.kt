package com.nexus.feature.home.data.remote.dto

import com.google.gson.annotations.SerializedName

data class AccountDto(
    @SerializedName("id") val id: String,
    @SerializedName("owner_name") val ownerName: String,
    @SerializedName("account_number") val accountNumber: String,
    @SerializedName("agency") val agency: String,
    @SerializedName("balance") val balance: Double,
    @SerializedName("account_type") val accountType: String
)

data class TransactionDto(
    @SerializedName("id") val id: String,
    @SerializedName("description") val description: String,
    @SerializedName("amount") val amount: Double,
    @SerializedName("type") val type: String,
    @SerializedName("category") val category: String,
    @SerializedName("date_time") val dateTime: String
)
