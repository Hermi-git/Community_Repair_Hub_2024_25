
package com.example.community_repair_hub.Utills

import android.content.Context
import android.content.SharedPreferences

object TokenManager {
    private lateinit var preferences: SharedPreferences

    private const val PREF_NAME = "user_prefs"
    private const val KEY_TOKEN = "auth_token"
    private const val KEY_ROLE = "user_role"

    fun init(context: Context) {
        preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun saveToken(token: String) {
        preferences.edit().putString(KEY_TOKEN, token).apply()
    }

    fun getToken(): String? {
        return preferences.getString(KEY_TOKEN, null)
    }

    fun saveRole(role: String) {
        preferences.edit().putString(KEY_ROLE, role).apply()
    }

    fun getRole(): String? {
        return preferences.getString(KEY_ROLE, null)
    }

    fun clear() {
        preferences.edit().clear().apply()
    }
}