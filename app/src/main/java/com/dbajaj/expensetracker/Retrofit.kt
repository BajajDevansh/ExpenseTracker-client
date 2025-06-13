package com.dbajaj.expensetracker

import com.dbajaj.expensetracker.data.LoginRequest
import com.dbajaj.expensetracker.data.LoginResponse
import com.dbajaj.expensetracker.data.Transaction
import com.dbajaj.expensetracker.data.TransactionDTO
import com.dbajaj.expensetracker.data.UserDTO
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthApi{
    //Login Functions
    @POST("/auth/login")
    suspend fun login(loginRequest: LoginRequest): LoginResponse
    @POST("/auth/register")
    suspend fun register(registerRequest: UserDTO)

}

interface TransactionApi{
    //Transaction Functions
    @POST("/transaction/add")
    suspend fun addTransaction(transaction: TransactionDTO)
    @GET("/transaction")
    suspend fun getTransactions(): List<Transaction>
}

object RetrofitClient {
    private const val baseUrl="http://localhost:8080/api"

    val authApi: AuthApi by lazy{
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AuthApi::class.java)
    }
    fun createTransaction(token: String): TransactionApi{
        val client= OkHttpClient.Builder()
            .addInterceptor {
                val request=it.request().newBuilder()
                    .addHeader("Authoriztion","Bearer $token")
                    .build()
                it.proceed(request)
            }
            .build()

        val retrofit= Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(TransactionApi::class.java)
    }

}