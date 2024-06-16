package com.example.module.di

import com.example.ModuleDb
import com.example.core.preferences.CustomPreferences
import com.example.core.preferences.CustomPreferencesImpl
import com.example.module.data.local.DriverFactory
import com.example.module.data.local.ModuleDataSource
import com.example.module.data.local.ModuleDataSourceImpl
import com.example.module.data.remote.RemoteDatabase
import com.example.module.data.remote.RemoteSupaDatabaseImpl
import com.example.module.data.remote.model.SupabaseModuleDto
import com.example.module.data.repository.ModuleRepositoryImpl
import com.example.module.domain.repository.ModuleRepository
import com.example.module.domain.use_cases.*
import com.example.module.presentation.all_modules_preview.AllModulesPreviewScreenViewModel
import com.example.module.presentation.main_screen.MainScreenViewModel
import com.example.module.presentation.module_screen.ModuleScreenViewModel
import com.example.module.presentation.utils.UndoHelper
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import org.koin.dsl.module
import kotlin.time.Duration.Companion.seconds


val featureModule = module {

    factory<SupabaseClient> {
        createSupabaseClient(
            supabaseUrl = "https://vntofffmdpjvqywefchb.supabase.co",
            supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InZudG9mZmZtZHBqdnF5d2VmY2hiIiwicm9sZSI6ImFub24iLCJpYXQiOjE3MDE3MDIyNTQsImV4cCI6MjAxNzI3ODI1NH0.1o-lzmwPyCvV5fVAKzhqD8sHHeZg7vCfT8HZBrD5XII"
        ) {
            install(Postgrest)
            requestTimeout = 50.seconds
        }
    }

    single<RemoteDatabase<SupabaseModuleDto>> { RemoteSupaDatabaseImpl(get()) }

    single<ModuleDb> { ModuleDb(DriverFactory().createDriver()) }
    single<ModuleDataSource> { ModuleDataSourceImpl(get()) }
    single<CustomPreferences> { CustomPreferencesImpl(get()) }

    single<ModuleRepository> { ModuleRepositoryImpl(get(), get(), get()) }

    factory { AddModuleUseCase(get()) }
    factory { DeleteModuleUseCase(get()) }
    factory { GetModulesUseCase(get()) }
    factory { DropDataBase(get()) }
    factory { FilterModulesUseCase() }
    factory { AddModulesUseCase(get()) }
    factory { GetModuleUseCase(get()) }
    factory { FetchModulesFromRemoteUseCase(get()) }
    factory { PushModulesToRemoteUseCase(get()) }
    factory { FilterAllModuleNames() }
    factory { DeleteModulesUseCase(get()) }
    factory { UpdateUndoListUseCase() }
    factory { SaveLastCardUseCase(get()) }
    factory { LoadLastCardUseCase(get()) }
    factory { GetModulesByNameUseCase(get()) }

    single<UndoHelper> { UndoHelper() }
    factory<ModuleUseCases> {
        ModuleUseCases(
            get(),
            get(),
            get(),
            get(),
            get(),
            get(),
            get(),
            get(),
            get(),
            get(),
            get(),
            get(),
            get(),
            get(),
            get()
        )
    }

    single { MainScreenViewModel(get(), get()) }
    factory { ModuleScreenViewModel(get(), get()) }
    factory { AllModulesPreviewScreenViewModel(get(), get()) }

}