package pl.fboro.finanse.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import pl.fboro.finanse.database.ActivityEvent
import pl.fboro.finanse.database.ActivityState
import pl.fboro.finanse.ui.screens.mainScreen.OptionsBar
import pl.fboro.finanse.ui.screens.mainScreen.SpendingContent
import pl.fboro.finanse.ui.screens.mainScreen.TopPanel
import pl.fboro.finanse.ui.theme.Background

@Composable
fun MainScreen(
    navController: NavController,
    state: ActivityState,
    onEvent: (ActivityEvent) -> Unit,
    language: Int,
    changeLanguage: (Int) -> Unit
) {
    var chosenActivity by remember{mutableStateOf("spendings")}


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
            .padding(horizontal = 15.dp)
    ) {
        OptionsBar(navController = navController, language = language){
            changeLanguage(it)
        }
        TopPanel(language = language) {
            chosenActivity = it
        }
        SpendingContent(
            state = state,
            onEvent = onEvent,
            language = language,
        )
    }
}