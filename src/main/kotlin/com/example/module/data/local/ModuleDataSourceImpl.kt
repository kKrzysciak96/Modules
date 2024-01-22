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

    override suspend fun getModulesByName(name: String): List<ModuleEntity> {
        return withContext(Dispatchers.IO) {
            queries.getModulesByName(name).executeAsList()
        }
    }

    override suspend fun getModules(): List<ModuleEntity> {
        return queries.getAllModules().executeAsList()
    }

    override fun getModulesFlow(): Flow<List<ModuleEntity>> {
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

    override suspend fun addModules(modules: List<ModuleEntity>) {
        println(modules.size.toString() + " remote size")
        queries.transaction {
            modules.forEach { module ->
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
    }

    override suspend fun deleteModules(modules: List<ModuleEntity>) {
        queries.transaction {
            modules.forEach { module -> queries.deleteModuleById(module.id) }
        }
    }

}

