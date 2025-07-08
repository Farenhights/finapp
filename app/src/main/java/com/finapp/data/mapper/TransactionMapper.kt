package com.finapp.data.mapper

import com.finapp.data.model.TransactionDto
import com.finapp.domain.model.Transaction
import com.finapp.domain.model.TransactionType

fun TransactionDto.toDomain(): Transaction {
    return Transaction(
        id = id,
        date = date,
        type = TransactionType.valueOf(type),
        amount = amount,
        description = description
    )
}
