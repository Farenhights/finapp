package com.finapp.data.model

data class TransactionDto(
    val id: String,
    val date: String,
    val type: String,
    val amount: Double,
    val description: String
)
