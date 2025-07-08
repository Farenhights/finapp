package com.finapp.data.mapper

import com.finapp.data.model.AccountDto
import com.finapp.domain.model.Account

fun AccountDto.toDomain(): Account {
    return Account(id = id, name = name, balance = balance)
}