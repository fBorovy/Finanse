package pl.fboro.finanse

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import pl.fboro.finanse.ui.screens.GreetScreen
import pl.fboro.finanse.ui.screens.MainScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.Greeting.name) {
        composable(
            route = Screen.Greeting.name
        ) {
            GreetScreen(navController)
        }
        composable(
            route = Screen.MainScreen.name
        ) {
            MainScreen()
        }
    }

}

enum class Screen {
    Greeting,
    MainScreen,
}