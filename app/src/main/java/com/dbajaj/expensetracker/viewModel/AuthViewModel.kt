package com.dbajaj.expensetracker.viewModel

import android.util.Base64
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import org.json.JSONObject
import kotlin.io.encoding.ExperimentalEncodingApi

class AuthViewModel: ViewModel() {
    var isLoggedIn by mutableStateOf(false)
        private set

    fun login(token: String) {
        if(isJwtValid(token)){
            isLoggedIn = true
        }
    }

    fun logout() {
        isLoggedIn = false
    }
}
@OptIn(ExperimentalEncodingApi::class)
fun isJwtValid(token: String): Boolean {
    return try {
        val parts = token.split(".")
        if (parts.size != 3) return false


        val payloadJson = String(Base64.decode(parts[1], Base64.URL_SAFE), Charsets.UTF_8)
        val payload = JSONObject(payloadJson)

        val exp = payload.getLong("exp") * 1000 // convert seconds to milliseconds
        val now = System.currentTimeMillis()

        now < exp // true if token is still valid
    } catch (e: Exception) {
        false // if any error occurs, token is invalid
    }
}
