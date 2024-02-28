package pl.fboro.finanse.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import pl.fboro.finanse.database.ActivityEvent
import pl.fboro.finanse.database.ActivityState
import pl.fboro.finanse.database.InvestmentState
import pl.fboro.finanse.income
import pl.fboro.finanse.spending
import pl.fboro.finanse.ui.screens.mainScreen.ActivitiesContent
import pl.fboro.finanse.ui.screens.mainScreen.InvestmentsContent
import pl.fboro.finanse.ui.screens.mainScreen.OptionsBar
import pl.fboro.finanse.ui.screens.mainScreen.TopPanel
import pl.fboro.finanse.ui.theme.Background
import pl.fboro.finanse.ui.theme.TopPanelBackground

@Composable
fun MainScreen(
    navController: NavController,
    state: ActivityState,
    investmentState: InvestmentState,
    onEvent: (ActivityEvent) -> Unit,
    language: Int,
    changeLanguage: (Int) -> Unit
) {
    var chosenActivity by remember { mutableStateOf(spending) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(TopPanelBackground)
                .padding(horizontal = 15.dp)
        ) {
            OptionsBar(navController = navController, language = language){
                changeLanguage(it)
            }
            TopPanel(
                language = language,
                state = state,
                onEvent = onEvent
            ) {
                chosenActivity = it
            }
            Spacer(modifier = Modifier.fillMaxWidth().height(15.dp))
        }

        if (chosenActivity == spending || chosenActivity == income) {
            ActivitiesContent(
                state = state,
                onEvent = onEvent,
                language = language,
                activityType = chosenActivity,
            )
        }
        else InvestmentsContent(
            investmentState = investmentState,
            onEvent = onEvent,
            language = language,
        )

    }
}