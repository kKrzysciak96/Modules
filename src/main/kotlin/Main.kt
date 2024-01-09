// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import core.di.appModule
import core.utils.ApiResult
import features.module.di.featureModule
import features.module.domain.repository.ModuleRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.context.startKoin
import org.koin.java.KoinJavaComponent.inject

@Composable
@Preview
fun App() {
    var text by remember { mutableStateOf("Hello, World!") }

    val repository: ModuleRepository by inject(ModuleRepository::class.java)

    val scope = CoroutineScope(Dispatchers.IO)
    scope.launch {
        repository.getModules().collect() { result ->
            when (result) {
                is ApiResult.Error -> {
                    println("ERROR")
                }

                ApiResult.Loading -> {
                    println("LOADING")
                }

                is ApiResult.Success<*> -> {
                    println(result.data.toString())
                }
            }
        }
    }

    MaterialTheme {
        Button(onClick = {
            text = "Hello, Desktop!"
        }) {
            Text(text)
        }
    }
}

fun main() = application {
    startKoin {
        modules(appModule, featureModule)
    }
    Window(onCloseRequest = ::exitApplication) {
        App()
    }
}
