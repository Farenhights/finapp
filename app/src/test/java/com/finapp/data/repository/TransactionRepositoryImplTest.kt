package com.finapp.data.repository

import com.finapp.data.mapper.toDomain
import com.finapp.data.model.AccountDto
import com.finapp.data.model.TransactionDto
import com.finapp.data.model.TransferRequestDto
import com.finapp.data.remote.TransactionApi
import com.finapp.util.TestJsonReader
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import io.mockk.coEvery
import io.mockk.mockk
import okhttp3.ResponseBody
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import retrofit2.Response

@ExperimentalCoroutinesApi
class TransactionRepositoryImplTest {

    private lateinit var api: TransactionApi
    private lateinit var repository: TransactionRepositoryImpl

    @Before
    fun setUp() {
        api = mockk()
        repository = TransactionRepositoryImpl(api)
    }

    @Test
    fun fetchTransferShouldReturnSuccessWhenAPIReturnsIsSuccessful() = runTest {
        coEvery {
            api.transfer(
                TransferRequestDto(
                    "destino",
                    100.0
                )
            )
        } returns Response.success(Unit)

        val result = repository.fetchTransfer("destino", 100.0)

        assertTrue(result.isSuccess)
    }

    @Test
    fun fetchTransferShouldReturnFailureWhenAPIReturnsUnsuccessful() = runTest {
        val errorResponse = Response.error<Unit>(
            400,
            ResponseBody.create(
                null,
                "erro"
            )
        )
        coEvery {
            api.transfer(
                TransferRequestDto(
                    "destino",
                    100.0
                )
            )
        } returns errorResponse

        val result = repository.fetchTransfer("destino", 100.0)

        assertTrue(result.isFailure)
        assertEquals("Erro: 400", result.exceptionOrNull()?.message)
    }

    @Test
    fun fetchTransferShouldReturnFailureWhenExceptionIsThrown() = runTest {
        coEvery { api.transfer(any()) } throws RuntimeException("Falha de rede")

        val result = repository.fetchTransfer("destino", 100.0)

        assertTrue(result.isFailure)
        assertEquals("Falha de rede", result.exceptionOrNull()?.message)
    }

    @Test
    fun fetchBalanceShouldReturnParsedAccountFromJSON() = runTest {
        val json = TestJsonReader.readJson("balance_response.json")
        val dto = Gson().fromJson(json, AccountDto::class.java)

        coEvery { api.getBalance() } returns dto

        val result = repository.fetchBalance()

        assertEquals(dto.toDomain(), result)
    }

    @Test
    fun `fetchTransactions should return parsed list from JSON`() = runTest {
        val json = TestJsonReader.readJson("transactions_response.json")
        val type = object : TypeToken<List<TransactionDto>>() {}.type
        val dtos = Gson().fromJson<List<TransactionDto>>(json, type)

        coEvery { api.getTransactions() } returns dtos

        val result = repository.fetchTransactions()

        assertEquals(dtos.map { it.toDomain() }, result)
    }

}
