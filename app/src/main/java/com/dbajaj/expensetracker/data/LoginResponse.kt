package com.dbajaj.expensetracker.data

data class LoginResponse(
    val jwtToken:String,
    val userId:String,
    val username:String
)