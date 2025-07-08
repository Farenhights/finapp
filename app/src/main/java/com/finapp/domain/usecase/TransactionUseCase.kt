package com.finapp.domain.usecase

import com.finapp.domain.model.Account
import com.finapp.domain.model.Transaction
import com.finapp.domain.repository.TransactionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TransactionUseCase(
    private val repository: TransactionRepository
) {

    suspend fun fetchBalance(): Account = withContext(Dispatchers.IO) {
        repository.fetchBalance()
    }

    suspend fun fetchTransactions(): List<Transaction> = withContext(Dispatchers.IO) {
        repository.fetchTransactions()
    }

    suspend fun fetchTransfer(to: String, amount: Double): Result<Unit> = withContext(Dispatchers.IO) {
        repository.fetchTransfer(to, amount)
    }
}
