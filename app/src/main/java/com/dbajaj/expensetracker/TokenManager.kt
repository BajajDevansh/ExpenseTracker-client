package com.dbajaj.expensetracker

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first

private val Context.dataStore by preferencesDataStore(name = "auth_prefs")

object TokenManager {
    private val TOKEN_KEY = stringPreferencesKey("jwt_token")
    private val USERNAME_KEY = stringPreferencesKey("username")
    private val USER_ID_KEY = stringPreferencesKey("user_id")

    suspend fun saveSession(context: Context, token: String, username: String, userId: String) {
        context.dataStore.edit { prefs ->
            prefs[TOKEN_KEY] = token
            prefs[USERNAME_KEY] = username
            prefs[USER_ID_KEY] = userId
        }
    }

    suspend fun getToken(context: Context): String? {
        val prefs = context.dataStore.data.first()
        return prefs[TOKEN_KEY]
    }

    suspend fun getUsername(context: Context): String? {
        val prefs = context.dataStore.data.first()
        return prefs[USERNAME_KEY]
    }

    suspend fun getUserId(context: Context): String? {
        val prefs = context.dataStore.data.first()
        return prefs[USER_ID_KEY]
    }

    suspend fun clearSession(context: Context) {
        context.dataStore.edit { prefs ->
            prefs.remove(TOKEN_KEY)
            prefs.remove(USERNAME_KEY)
            prefs.remove(USER_ID_KEY)
        }
    }
}