package features.module.di

import features.module.data.remote.RemoteDataBase
import features.module.data.remote.RemoteSupaDataBaseImpl
import features.module.data.remote.model.SupabaseSpecificModule
import features.module.data.repository.ModuleRepositoryImpl
import features.module.domain.repository.ModuleRepository
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import org.koin.dsl.module

val featureModule = module {

    factory<SupabaseClient> {
        createSupabaseClient(
            supabaseUrl = "https://vntofffmdpjvqywefchb.supabase.co",
            supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InZudG9mZmZtZHBqdnF5d2VmY2hiIiwicm9sZSI6ImFub24iLCJpYXQiOjE3MDE3MDIyNTQsImV4cCI6MjAxNzI3ODI1NH0.1o-lzmwPyCvV5fVAKzhqD8sHHeZg7vCfT8HZBrD5XII"
        ) {
            install(Postgrest)
        }
    }

    single<RemoteDataBase<SupabaseSpecificModule>> { RemoteSupaDataBaseImpl(get()) }
    single<ModuleRepository> { ModuleRepositoryImpl(get()) }
}