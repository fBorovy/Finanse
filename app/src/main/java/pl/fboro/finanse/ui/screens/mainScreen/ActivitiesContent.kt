package pl.fboro.finanse.ui.screens.mainScreen

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import pl.fboro.finanse.*
import pl.fboro.finanse.R
import pl.fboro.finanse.database.ActivityEvent
import pl.fboro.finanse.database.ActivityState
import pl.fboro.finanse.database.SortType
import pl.fboro.finanse.ui.theme.*

@Suppress("OPT_IN_IS_NOT_ENABLED")
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ActivitiesContent(
    state: ActivityState,
    onEvent: (ActivityEvent) -> Unit,
    language: Int,
    activityType: Int,
) {
    var chosenSortType by remember{ mutableStateOf(0) }
    var isDropDownVisible by remember { mutableStateOf(false) }
    var chosenYear by remember { mutableStateOf(currentYear) }

    Scaffold(
        modifier = Modifier
            .padding(top = 10.dp),
        floatingActionButton = {
            FloatingActionButton(onClick = {
                onEvent(ActivityEvent.ShowAddingDialog)
            }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = addIconDesc[language],
                )
            }
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Background),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(text = sort[language], style = Typography.h2)
                Box(
                    modifier = Modifier.padding(horizontal = 5.dp)
                ) {
                    Image(
                        painterResource(id = R.drawable.dollar),
                        contentDescription = dollarIconDesc[language],
                        colorFilter = if (chosenSortType == 1) ColorFilter.tint(LightAqua)
                        else ColorFilter.tint(Aqua),
                        modifier = Modifier
                            .size(30.dp)
                            .clickable {
                                onEvent(
                                    ActivityEvent.SortActivities(
                                        sortType = if (activityType == spending) SortType.SPENDING_AMOUNT
                                        else SortType.INCOME_AMOUNT
                                    )
                                )
                                chosenSortType = 1
                            },
                    )
                }
                Box(
                    modifier = Modifier.padding(horizontal = 5.dp)
                ) {
                    Image(
                        painterResource(id = R.drawable.calendar),
                        contentDescription = calendarIconDesc[language],
                        colorFilter = if (chosenSortType == 0) ColorFilter.tint(LightAqua)
                        else ColorFilter.tint(Aqua),
                        modifier = Modifier
                            .size(30.dp)
                            .clickable {
                                onEvent(
                                    ActivityEvent.SortActivities(
                                        sortType = if (activityType == spending) SortType.SPENDING_YEAR_MONTH_DAY
                                        else SortType.INCOME_YEAR_MONTH_DAY
                                    )
                                )
                                chosenSortType = 0
                            }
                    )
                }
                Spacer(
                    modifier = Modifier
                        .weight(1f)
                )
                Box {
                    Text(
                        "$chosenYear",
                        style = Typography.h2,
                        modifier = Modifier
                            .clickable{
                                isDropDownVisible = !isDropDownVisible
                            }
                    )
                    DropdownMenu(
                        expanded = isDropDownVisible,
                        onDismissRequest = { isDropDownVisible = false },
                        modifier = Modifier
                            .background(Background)
                            .clip(RoundedCornerShape(10.dp))
                            .border(2.dp, Aqua, RoundedCornerShape(10.dp)),

                    ) {
                        state.years.forEach {
                            DropdownMenuItem(onClick = {
                                chosenYear = it
                                isDropDownVisible = false
                            }) {
                                Text(
                                    "$it",
                                    style = Typography.h2,
                                )
                            }
                        }
                    }
                }
            }
            if(state.isAddingActivity) {
                AddActivityDialog(
                    state = state,
                    activityType = activityType,
                    language = language,
                    onEvent = onEvent
                )
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 35.dp)
                    .horizontalScroll(rememberScrollState()),
            ) {
                var previousMonth = 0//state.activities[0].month
                var sum = 0.0
                items(state.activities) { activity ->
                    if (activity.year == chosenYear && activity.type == activityType) {
                        Column(modifier = Modifier.fillMaxWidth())
                        {
                            if (chosenSortType == 0 && activity.month != previousMonth && previousMonth != 0) {
                                Text(
                                    text = "\n\n${monthTotal[language]} $sum\n\n"
                                )
                                sum = 0.0
                            }
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .combinedClickable(
                                        onClick = {},
                                        onLongClick = {
                                            onEvent(ActivityEvent.DeleteActivity(activity))
                                        }
                                    )
                            ) {
                                Text(text =
                                if (activity.month < 10) {"${activity.day}.0${activity.month}: " +
                                        "${activity.amount} ${activity.title}"}
                                else {"${activity.day}.${activity.month}: " +
                                        "${activity.amount} ${activity.title}"},
                                    style = Typography.body1
                                )
                                Text(
                                    " ${activity.source}",
                                    style = Typography.body1,
                                    color = when(activity.source) {
                                        'R' -> Revo
                                        'G' -> Color.Red
                                        else -> Color.Green
                                    },
                                )
                            }
                            sum += activity.amount
                            if (state.activities.indexOf(activity) == state.activities.lastIndex
                                || previousMonth != activity.month && chosenSortType == 0) {
                                Text(
                                    text = "\n\n${monthTotal[language]} $sum\n\n"
                                )
                                sum = 0.0
                            }
                            previousMonth = activity.month
                        }
                    }
                }
            }
        }
    }
}