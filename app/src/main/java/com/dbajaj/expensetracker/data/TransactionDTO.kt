package com.dbajaj.expensetracker.data

import java.time.LocalDateTime

data class TransactionDTO(
    val amount : Double,
    val type : String,
    val description : String=""
)