package com.finapp.data.repository

import com.finapp.data.mapper.toDomain
import com.finapp.data.model.TransferRequestDto
import com.finapp.data.remote.TransactionApi
import com.finapp.domain.model.Account
import com.finapp.domain.model.Transaction
import com.finapp.domain.repository.TransactionRepository

class TransactionRepositoryImpl(
    private val api: TransactionApi
) : TransactionRepository {

    override suspend fun fetchBalance(): Account {
        return api.getBalance().toDomain()
    }

    override suspend fun fetchTransactions(): List<Transaction> {
        return api.getTransactions().map { it.toDomain() }
    }

    override suspend fun fetchTransfer(to: String, amount: Double): Result<Unit> {
        return try {
            val response = api.transfer(TransferRequestDto(to, amount))
            if (response.isSuccessful) Result.success(Unit)
            else Result.failure(Exception("Erro: ${response.code()}"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

