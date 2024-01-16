package com.example.module.data.local

import com.example.ModuleEntity
import kotlinx.coroutines.flow.Flow

interface ModuleDataSource {
    suspend fun getModuleById(id: String): ModuleEntity?
    fun getModulesFlow(): Flow<List<ModuleEntity>>
    suspend fun getModules(): List<ModuleEntity>
    suspend fun addModule(module: ModuleEntity)
    suspend fun deleteModuleById(id: String)
    suspend fun dropDatabase()
    suspend fun addModules(modules: List<ModuleEntity>)
    suspend fun deleteModules(modules: List<ModuleEntity>)

}