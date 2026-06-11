package com.example.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.ui.city.AddEditCityScreen
import com.example.ui.city.CityListScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "cityList") {
        composable("cityList") {
            CityListScreen(
                onNavigateToAddCity = { navController.navigate("addEditCity/0") },
                onNavigateToEditCity = { cityId -> navController.navigate("addEditCity/$cityId") }
            )
        }
        composable(
            route = "addEditCity/{cityId}",
            arguments = listOf(navArgument("cityId") { type = NavType.IntType })
        ) { backStackEntry ->
            val cityId = backStackEntry.arguments?.getInt("cityId") ?: 0
            AddEditCityScreen(
                cityId = cityId,
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}
