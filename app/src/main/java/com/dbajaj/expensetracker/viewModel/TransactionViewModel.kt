package com.dbajaj.expensetracker.viewModel

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dbajaj.expensetracker.RetrofitClient
import com.dbajaj.expensetracker.TokenManager
import com.dbajaj.expensetracker.data.Transaction
import kotlinx.coroutines.launch

class TransactionViewModel(application: Application): AndroidViewModel(application) {
    private val _transactions= mutableStateOf<List<Transaction>>(emptyList())
    val transactions: List<Transaction>
        get() = _transactions.value

    private val context = getApplication<Application>().applicationContext

    fun getTransactions(){
        viewModelScope.launch {
            try{
                val token = TokenManager.getToken(context) ?: return@launch
                val api = RetrofitClient.createTransaction(token)
                val response = api.getTransactions()
                _transactions.value = response
            }catch (e: Exception){
                println(e.message)
                _transactions.value = emptyList()
            }
        }
    }
}