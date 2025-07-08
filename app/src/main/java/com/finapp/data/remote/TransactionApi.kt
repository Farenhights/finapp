package com.finapp.data.remote

import com.finapp.data.model.AccountDto
import com.finapp.data.model.TransactionDto
import com.finapp.data.model.TransferRequestDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface TransactionApi {
    @GET("balance")
    suspend fun getBalance(): AccountDto

    @GET("transactions")
    suspend fun getTransactions(): List<TransactionDto>

    @POST("transfer")
    suspend fun transfer(@Body request: TransferRequestDto): Response<Unit>
}
