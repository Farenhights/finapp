package com.finapp.domain.repository

import com.finapp.domain.model.Account
import com.finapp.domain.model.Transaction

interface TransactionRepository {
    suspend fun fetchBalance(): Account
    suspend fun fetchTransactions(): List<Transaction>
    suspend fun fetchTransfer(to: String, amount: Double): Result<Unit>
}