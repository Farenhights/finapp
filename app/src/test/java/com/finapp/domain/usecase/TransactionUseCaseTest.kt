package com.finapp.domain.usecase

import com.finapp.domain.model.Account
import com.finapp.domain.model.Transaction
import com.finapp.domain.model.TransactionType
import com.finapp.domain.repository.TransactionRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class TransactionUseCaseTest {
    private lateinit var repository: TransactionRepository
    private lateinit var useCase: TransactionUseCase

    @Before
    fun setup() {
        repository = mockk()
        useCase = TransactionUseCase(repository)
    }

    @Test
    fun fetchBalanceShouldReturnAccount() = runTest {
        val account = Account("001", "Eric", 1000.0)
        coEvery { repository.fetchBalance() } returns account

        val result = useCase.fetchBalance()

        assertEquals(account, result)
    }

    @Test
    fun fetchTransactionsShouldReturnTransactionList() = runTest {
        val transactions =
            listOf(Transaction(
                "t1", "2024-07-01",
                TransactionType.CREDIT,
                100.0,
                "depósito")
            )
        coEvery { repository.fetchTransactions() } returns transactions

        val result = useCase.fetchTransactions()

        assertEquals(transactions, result)
    }

    @Test
    fun fetchTransferShouldReturnSuccess() = runTest {
        coEvery { repository.fetchTransfer("002", 50.0) } returns Result.success(Unit)

        val result = useCase.fetchTransfer("002", 50.0)

        assert(result.isSuccess)
    }
}