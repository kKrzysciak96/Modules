package com.example.core.preferences

import com.example.ModuleDb

class CustomPreferencesImpl(private val db: ModuleDb) : CustomPreferences {

    private val queries = db.customPreferencesQueries

    override fun saveLastCard(page: Int) {
        queries.saveLastCard(CustomPreferences.KEY_LAST_CARD, page.toLong())
    }

    override fun loadLastCard(): Int {
        return queries.loadLastCard(CustomPreferences.KEY_LAST_CARD).executeAsOneOrNull()?.page?.toInt() ?: 36524
    }
}