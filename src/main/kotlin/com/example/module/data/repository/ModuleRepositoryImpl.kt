package com.example.module.data.repository

import com.example.ModuleEntity
import com.example.core.extensions.toModuleDomain
import com.example.core.extensions.toModuleEntity
import com.example.core.preferences.CustomPreferences
import com.example.core.utils.ApiResult
import com.example.module.data.local.ModuleDataSource
import com.example.module.data.remote.RemoteDatabase
import com.example.module.data.remote.model.SupabaseModuleDto
import com.example.module.domain.model.ModuleDomain
import com.example.module.domain.repository.ModuleRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.util.*

class ModuleRepositoryImpl(
    private val remoteDataBase: RemoteDatabase<SupabaseModuleDto>,
    private val localDataBase: ModuleDataSource,
    private val customPreferences: CustomPreferences,
) : ModuleRepository {

    //        override suspend fun getModules(): Flow<ApiResult> {
//        return flow {
//            emit(ApiResult.Loading)
//            try {
//                val remoteData = remoteDataBase.fetchModulesFromRemote().map { it.toModule() }
//                emit(ApiResult.Success(remoteData))
//            } catch (e: Exception) {
//                emit(ApiResult.Error(e.message))
//            }
//        }
//    }

    override suspend fun editModule(module: ModuleDomain) {
        localDataBase.addModule(module.toModuleEntity())
    }

    override suspend fun addModule(module: ModuleDomain) {
        localDataBase.addModule(module.toModuleEntity())
    }

    override suspend fun addModules(modules: List<ModuleDomain>) {
        localDataBase.addModules(modules.map { it.toModuleEntity() })
    }

    override suspend fun deleteModule(module: ModuleDomain) {
        localDataBase.deleteModuleById(module.id.toString())
    }

    override suspend fun deleteModules(modules: List<ModuleDomain>) {
        localDataBase.deleteModules(modules.map { it.toModuleEntity() })
    }

    override fun getModulesFlow(): Flow<List<ModuleDomain>> {
        return localDataBase.getModulesFlow().map { list ->
            list.map { it.toModuleDomain() }
        }
    }

    override suspend fun getModule(id: UUID): ModuleDomain {
        return localDataBase.getModuleById(id.toString())!!.toModuleDomain()
    }

    override suspend fun dropDatabase() {
        localDataBase.dropDatabase()
    }

    override suspend fun pushModulesToRemote(): Flow<ApiResult> {
        return flow {
            emit(ApiResult.Loading)
            try {
                remoteDataBase.pushModulesToRemote(getModulesFromLocal())
                emit(ApiResult.Success(Unit))
            } catch (e: Exception) {
                emit(ApiResult.Error(e.message))
            }
        }
    }

    override suspend fun fetchModulesFromRemote(): Flow<ApiResult> {
        return flow {
            emit(ApiResult.Loading)
            try {
                remoteDataBase.fetchModulesFromRemote().map { it.toModuleEntity() }.also { saveToLocal(it) }
                emit(ApiResult.Success(Unit))
            } catch (e: Exception) {
                println(e.message)
                emit(ApiResult.Error(e.message))
            }
        }
    }

    override suspend fun saveLastCard(page: Int) {
        customPreferences.saveLastCard(page)
    }

    override suspend fun loadLastCard(): Int {
        return customPreferences.loadLastCard()
    }

    private suspend fun getModulesFromLocal(): List<SupabaseModuleDto> {
        return localDataBase.getModules().map { SupabaseModuleDto(it) }
    }

    private suspend fun saveToLocal(data: List<ModuleEntity>) {
        localDataBase.addModules(data)
    }
}