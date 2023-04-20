package pl.fboro.finanse

import androidx.compose.runtime.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import pl.fboro.finanse.database.ActivityEvent
import pl.fboro.finanse.database.ActivityState
import pl.fboro.finanse.database.InvestmentState
import pl.fboro.finanse.ui.screens.GreetScreen
import pl.fboro.finanse.ui.screens.MainScreen

@Composable
fun Navigation(
    state: ActivityState,
    investmentState: InvestmentState,
    onEvent: (ActivityEvent) -> Unit,
) {
    val navController = rememberNavController()
    var language by remember{ mutableStateOf(polish) }

    NavHost(navController = navController, startDestination = Screen.Greeting.name) {
        composable(
            route = Screen.Greeting.name
        ) {
            GreetScreen(navController, language)
        }
        composable(
            route = Screen.MainScreen.name
        ) {
            MainScreen(navController, state, investmentState, onEvent, language) {
                language = it
            }
        }
    }

}

enum class Screen {
    Greeting,
    MainScreen,
}