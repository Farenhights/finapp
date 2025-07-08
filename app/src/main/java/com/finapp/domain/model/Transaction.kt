package com.finapp.domain.model

data class Transaction(
    val id: String,
    val date: String,
    val type: TransactionType,
    val amount: Double,
    val description: String
)

enum class TransactionType {
    CREDIT, DEBIT
}