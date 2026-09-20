package com.example.home

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

/**
 * Gestor del minitutorial de bienvenida para dar a conocer el selector del Modo PRO.
 * Guarda en SharedPreferences para mostrarse automáticamente solo la primera vez,
 * pero permite reactivarse manualmente si el usuario lo desea.
 */
object ProModeTutorialManager {
    private const val PREFS_NAME = "kabasket_pro_tutorial"
    private const val KEY_HAS_SEEN = "has_seen_pro_toggle_tutorial_v1"

    var isTutorialActive by mutableStateOf(false)
        private set

    fun shouldShowTutorial(context: Context): Boolean {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return !prefs.getBoolean(KEY_HAS_SEEN, false)
    }

    fun markTutorialSeen(context: Context) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit().putBoolean(KEY_HAS_SEEN, true).apply()
        isTutorialActive = false
    }

    fun triggerTutorial() {
        isTutorialActive = true
    }

    fun dismissTutorial(context: Context? = null) {
        if (context != null) {
            markTutorialSeen(context)
        } else {
            isTutorialActive = false
        }
    }

    fun resetTutorial(context: Context) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit().putBoolean(KEY_HAS_SEEN, false).apply()
        isTutorialActive = true
    }
}
