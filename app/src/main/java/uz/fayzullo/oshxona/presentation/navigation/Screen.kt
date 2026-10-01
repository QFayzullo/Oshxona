package uz.fayzullo.oshxona.presentation.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import uz.fayzullo.oshxona.presentation.coocking.CookingScreen
import uz.fayzullo.oshxona.presentation.coocking.CookingViewModel
import uz.fayzullo.oshxona.presentation.detail.DetailScreen
import uz.fayzullo.oshxona.presentation.detail.DetailViewModel
import uz.fayzullo.oshxona.presentation.home.HomeScreen
import uz.fayzullo.oshxona.presentation.home.HomeViewModel
import uz.fayzullo.oshxona.presentation.onboarding.OnboardingScreen

sealed class Screen(val route: String) {
    data object Onboarding : Screen("onboarding_screen")
    data object Home : Screen("home_screen")
    data object Detail : Screen("detail_screen/{recipeId}") {
        fun createRoute(recipeId: String) = "detail_screen/$recipeId"
    }
    data object Cooking : Screen("cooking_screen/{recipeId}") {
        fun createRoute(recipeId: String) = "cooking_screen/$recipeId"
    }
}

@Composable
fun OshxonaNavGraph() {
    val navController = rememberNavController()

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Onboarding.route,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            composable(Screen.Onboarding.route) {
                OnboardingScreen(
                    onFinish = {
                        navController.navigate(Screen.Home.route) {
                            popUpTo(Screen.Onboarding.route) { inclusive = true }
                        }
                    }
                )
            }

            composable(Screen.Home.route) {
                val viewModel: HomeViewModel = hiltViewModel()
                HomeScreen(
                    viewModel = viewModel,
                    onNavigateToDetails = { recipeId ->
                        navController.navigate(Screen.Detail.createRoute(recipeId))
                    }
                )
            }

            composable(
                route = Screen.Detail.route,
                arguments = listOf(navArgument("recipeId") { type = NavType.StringType })
            ) { backStackEntry ->
                val recipeId = backStackEntry.arguments?.getString("recipeId") ?: ""
                val viewModel: DetailViewModel = hiltViewModel()
                DetailScreen(
                    recipeId = recipeId,
                    viewModel = viewModel,
                    onBack = { navController.popBackStack() },
                    onNavigateToCooking = { id ->
                        navController.navigate(Screen.Cooking.createRoute(id))
                    }
                )
            }

            composable(
                route = Screen.Cooking.route,
                arguments = listOf(navArgument("recipeId") { type = NavType.StringType })
            ) { backStackEntry ->
                val recipeId = backStackEntry.arguments?.getString("recipeId") ?: ""
                val viewModel: CookingViewModel = hiltViewModel()
                CookingScreen(
                    recipeId = recipeId,
                    viewModel = viewModel,
                    onBack = { navController.popBackStack() }
                )
            }
        }
    }
}