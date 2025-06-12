package com.dbajaj.expensetracker.models

import java.time.LocalDateTime

data class Transaction(
    val id : String,
    val userId : String,
    val amount : Double,
    val type : String,
    val date : LocalDateTime,
    val description : String=""
)