package com.example.core.app

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.example.core.navigation.Routes
import com.example.module.presentation.all_modules_preview.AllModulesPreviewScreen
import com.example.module.presentation.all_modules_preview.AllModulesPreviewScreenViewModel
import com.example.module.presentation.main_screen.MainScreen
import com.example.module.presentation.main_screen.MainScreenViewModel
import com.example.module.presentation.module_screen.ModuleScreen
import com.example.module.presentation.module_screen.ModuleScreenViewModel
import moe.tlaster.precompose.PreComposeApp
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.path
import moe.tlaster.precompose.navigation.rememberNavigator
import org.koin.java.KoinJavaComponent.inject
import java.util.*


@Composable
fun App() {
    PreComposeApp {
        val snackBarHostState = remember { SnackbarHostState() }
        val navController = rememberNavigator()

        MaterialTheme {
            Scaffold(
                snackbarHost = { SnackbarHost(hostState = snackBarHostState) },
                modifier = Modifier.fillMaxSize(),
                content = {
                    NavHost(
                        navigator = navController,
                        initialRoute = Routes.MAIN_SCREEN
                    ) {
                        scene(Routes.MAIN_SCREEN) {
                            val viewModel by remember { inject<MainScreenViewModel>(MainScreenViewModel::class.java) }
                            MainScreen(
                                snackBarHostState = snackBarHostState,
                                viewModel = viewModel,
                                onNextScreen = { navController.navigate(it) }
                            )
                        }

                        scene(Routes.ALL_MODULES_PREVIEW_SCREEN) {
                            val viewModel by remember {
                                inject<AllModulesPreviewScreenViewModel>(
                                    AllModulesPreviewScreenViewModel::class.java
                                )
                            }
                            AllModulesPreviewScreen(viewModel = viewModel, onBack = {
                                navController.popBackStack()
                            })
                        }

                        scene("/${Routes.MODULE_SCREEN}/{id}") { backStackEntry ->
                            val viewModel by remember { inject<ModuleScreenViewModel>(ModuleScreenViewModel::class.java) }
                            val stringId: String? = backStackEntry.path<String>("id")
                            val id = UUID.fromString(stringId)
                            println(stringId)
                            id?.let {
                                ModuleScreen(
                                    snackBarHostState = snackBarHostState,
                                    passedId = it,
                                    viewModel = viewModel,
                                    onBackPress = { navController.popBackStack() }
                                )
                            }
                        }
                    }
                })
        }
    }
}