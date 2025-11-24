package com.bteamcoding.vehicledetection.app.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.bteamcoding.vehicledetection.feature_home.presentation.AboutScreenRoot
import com.bteamcoding.vehicledetection.feature_home.presentation.HomeScreenRoot
import com.bteamcoding.vehicledetection.feature_result.presentation.ProcessingScreenRoot
import com.bteamcoding.vehicledetection.feature_result.presentation.ResultScreenRoot
import com.bteamcoding.vehicledetection.feature_select_type.presentation.SelectImageTypeScreenRoot
import kotlinx.serialization.Serializable

@Composable
fun AppNavHost(
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = NavRoutes.HOME
    ) {
        composable(route = NavRoutes.HOME) {
            HomeScreenRoot(navController = navController)
        }
        composable(route = NavRoutes.ABOUT) {
            AboutScreenRoot(navController = navController)
        }
        composable<ProcessingScreenParams> {
            val args = it.toRoute<ProcessingScreenParams>()

            args.uri?.let { it1 ->
                args.type?.let { it2 ->
                    ProcessingScreenRoot(
                        uri = it1,
                        type = it2,
                        navController = navController
                    )
                }
            }
        }
        composable<SelectTypeScreenParams> {
            val args = it.toRoute<SelectTypeScreenParams>()

            args.uri?.let { image ->
                SelectImageTypeScreenRoot(
                    uri = image,
                    navController = navController
                )
            }

        }
        composable<ResultScreenParams> {
            val args = it.toRoute<ResultScreenParams>()

            args.uri?.let { it1 ->
                args.type?.let { it2 ->
                    ResultScreenRoot(
                        uri = it1,
                        type = it2,
                        navController = navController
                    )
                }
            }
        }
    }
}

@Serializable
data class SelectTypeScreenParams(
    val uri: String? = null
)

@Serializable
data class ProcessingScreenParams(
    val uri: String? = null,
    val type: String? = null
)

@Serializable
data class ResultScreenParams(
    val uri: String? = null,
    val type: String? = null
)