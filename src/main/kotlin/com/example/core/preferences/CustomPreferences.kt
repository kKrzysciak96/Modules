package com.example.core.preferences

interface CustomPreferences {

    fun saveLastCard(page: Int)
    fun loadLastCard(): Int

    companion object {
        const val KEY_LAST_CARD = "KEY_LAST_CARD"
    }

}