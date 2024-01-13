package com.example.module.data.local

import app.cash.sqldelight.coroutines.asFlow
import com.example.ModuleDb
import com.example.ModuleEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class ModuleDataSourceImpl(private val db: ModuleDb) : ModuleDataSource {

    private val queries = db.moduleEntityQueries

    override suspend fun getModuleById(id: String): ModuleEntity? {
        return withContext(Dispatchers.IO) {
            queries.getModuleById(id).executeAsOneOrNull()
        }
    }

    override fun getModules(): Flow<List<ModuleEntity>> {
        return queries.getAllModules().asFlow().map { it.executeAsList() }
    }

    override suspend fun addModule(module: ModuleEntity) {
        withContext(Dispatchers.IO) {
            queries.addModule(
                id = module.id,
                comment = module.comment,
                epochDay = module.epochDay,
                incrementation = module.incrementation,
                newIncrementation = module.newIncrementation,
                isSkipped = module.isSkipped,
                timeStamp = module.timeStamp,
                name = module.name,
            )
        }
    }

    override suspend fun deleteModuleById(id: String) {
        return withContext(Dispatchers.IO) {
            queries.deleteModuleById(id)
        }
    }

    override suspend fun dropDatabase() {
        return withContext(Dispatchers.IO) {
            queries.dropDatabase()
        }
    }

}