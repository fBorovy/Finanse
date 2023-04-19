package pl.fboro.finanse.ui.screens.mainScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import pl.fboro.finanse.*
import pl.fboro.finanse.database.ActivityEvent
import pl.fboro.finanse.database.ActivityState
import pl.fboro.finanse.database.SortType
import pl.fboro.finanse.ui.theme.Aqua
import pl.fboro.finanse.ui.theme.Typography

@Composable
fun TopPanel(
    language: Int,
    state: ActivityState,
    onEvent: (ActivityEvent) -> Unit,
    changeActivity: (Int) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .padding(horizontal = 5.dp)
                    .clip(RoundedCornerShape(15.dp))
                    .shadow(5.dp)
                    .background(Aqua)
                    .clickable {
                        changeActivity(spending)
                        if (state.sortType == SortType.SPENDING_YEAR_MONTH_DAY || state.sortType == SortType.INCOME_YEAR_MONTH_DAY)
                        onEvent(ActivityEvent.SortActivities(SortType.SPENDING_YEAR_MONTH_DAY))
                        else onEvent(ActivityEvent.SortActivities(SortType.SPENDING_AMOUNT))
                    }
                    .padding(vertical = 10.dp)
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = spendings[language], style = Typography.h1, maxLines = 1
                )
            }
            Box(
                modifier = Modifier
                    .padding(horizontal = 5.dp)
                    .shadow(5.dp)
                    .clip(RoundedCornerShape(15.dp))
                    .background(Aqua)
                    .clickable {
                        changeActivity(income)
                        if (state.sortType == SortType.SPENDING_YEAR_MONTH_DAY || state.sortType == SortType.INCOME_YEAR_MONTH_DAY)
                            onEvent(ActivityEvent.SortActivities(SortType.INCOME_YEAR_MONTH_DAY))
                        else onEvent(ActivityEvent.SortActivities(SortType.INCOME_AMOUNT))
                    }
                    .padding(10.dp)
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = incomes[language], style = Typography.h1, maxLines = 1
                )
            }
            Box(
                modifier = Modifier
                    .padding(horizontal = 5.dp)
                    .clip(RoundedCornerShape(15.dp))
                    .shadow(5.dp)
                    .background(Aqua)
                    .clickable {
                        changeActivity(investment)
                    }
                    .padding(10.dp)
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = investments[language], style = Typography.h1, maxLines = 1
                )
            }
        }
    }
}