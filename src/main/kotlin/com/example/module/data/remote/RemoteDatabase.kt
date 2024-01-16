package com.example.module.data.remote

interface RemoteDatabase<T> {

    suspend fun pushModulesToRemote(modules: List<T>)
    suspend fun fetchModulesFromRemote(): List<T>

}