package pl.fboro.finanse.ui.screens.mainScreen

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import pl.fboro.finanse.*
import pl.fboro.finanse.R.*
import pl.fboro.finanse.database.ActivityEvent
import pl.fboro.finanse.database.ActivityState
import pl.fboro.finanse.database.SortType
import pl.fboro.finanse.ui.theme.*
import kotlin.math.roundToInt

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
    var isYearDropDownVisible by remember { mutableStateOf(false) }
    var isMonthDropDownVisible by remember { mutableStateOf(false) }
    var chosenYear by remember { mutableStateOf(currentYear) }
    var chosenMonth by remember { mutableStateOf(currentMonth) }

    Scaffold(
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Background),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(elevation = 15.dp, shape = RoundedCornerShape(0.dp, 0.dp, 25.dp, 25.dp))
                    .background(TopPanelBackground)
                    .padding(bottom = 15.dp)
                    .padding(horizontal = 35.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(text = sort[language], style = Typography.h2)
                Box(
                    modifier = Modifier.padding(horizontal = 5.dp)
                ) {
                    Image(
                        painterResource(id = drawable.calendar),
                        contentDescription = calendarIconDesc[language],
                        colorFilter = if (chosenSortType == 0) ColorFilter.tint(Aqua)
                        else ColorFilter.tint(LightAqua),
                        modifier = Modifier
                            .size(24.dp)
                            .clickable {
                                onEvent(
                                    ActivityEvent.SortActivities(
                                        sortType = if (activityType == spending && (state.sortType == SortType.SPENDING_YEAR_MONTH_DAY || state.sortType == SortType.SPENDING_AMOUNT_ASC)) SortType.SPENDING_YEAR_MONTH_DAY_ASC
                                        else if (activityType == spending && (state.sortType == SortType.SPENDING_YEAR_MONTH_DAY_ASC || state.sortType == SortType.SPENDING_AMOUNT)) SortType.SPENDING_YEAR_MONTH_DAY
                                        else if (activityType == income && (state.sortType == SortType.INCOME_YEAR_MONTH_DAY || state.sortType == SortType.SPENDING_AMOUNT_ASC)) SortType.INCOME_YEAR_MONTH_DAY_ASC
                                        else SortType.INCOME_YEAR_MONTH_DAY
                                    )
                                )
                                chosenSortType = 0
                            },
                    )
                }
                Box(
                    modifier = Modifier.padding(horizontal = 5.dp)
                ) {
                    Image(
                        painterResource(id = drawable.dollar),
                        contentDescription = dollarIconDesc[language],
                        colorFilter = if (chosenSortType == 1) ColorFilter.tint(Aqua)
                        else ColorFilter.tint(LightAqua),
                        modifier = Modifier
                            .size(24.dp)
                            .clickable {
                                onEvent(
                                    ActivityEvent.SortActivities(
                                        sortType = if (activityType == spending && state.sortType == SortType.SPENDING_AMOUNT || state.sortType == SortType.SPENDING_YEAR_MONTH_DAY_ASC) SortType.SPENDING_AMOUNT_ASC
                                        else if (activityType == spending && state.sortType == SortType.SPENDING_AMOUNT_ASC || state.sortType == SortType.SPENDING_YEAR_MONTH_DAY) SortType.SPENDING_AMOUNT
                                        else if (activityType == income && (state.sortType == SortType.INCOME_AMOUNT || state.sortType == SortType.INCOME_YEAR_MONTH_DAY_ASC)) SortType.INCOME_AMOUNT_ASC
                                        else SortType.INCOME_AMOUNT
                                    )
                                )
                                chosenSortType = 1
                            },
                    )
                }
                Spacer(
                    modifier = Modifier
                        .weight(1f)
                )
                Box{
                    Text(
                        text = if (language == 0) month[chosenMonth - 1] else month[chosenMonth + 11],
                        style = Typography.h2,
                        modifier = Modifier
                            .clickable{
                                isMonthDropDownVisible = !isMonthDropDownVisible
                            },
                    )
                    DropdownMenu(
                        expanded = isMonthDropDownVisible,
                        onDismissRequest = { isMonthDropDownVisible = false },
                        modifier = Modifier
                            .background(Background)
                            .clip(RoundedCornerShape(10.dp))
                            .border(2.dp, LightAqua, RoundedCornerShape(10.dp)),
                    ) {
                        for (i in 0..11) {
                            DropdownMenuItem(onClick = {
                                chosenMonth = i + 1
                                isMonthDropDownVisible = false
                            }) {
                                Text(
                                    text = if (language == 0) month[i] else month[i+12],
                                    style = Typography.h2,
                                )
                            }
                        }
                    }
                }
                Box {
                    Text(
                        "$chosenYear",
                        style = Typography.h2,
                        modifier = Modifier
                            .clickable{
                                isYearDropDownVisible = !isYearDropDownVisible
                            },
                    )
                    DropdownMenu(
                        expanded = isYearDropDownVisible,
                        onDismissRequest = { isYearDropDownVisible = false },
                        modifier = Modifier
                            .background(Background)
                            .clip(RoundedCornerShape(10.dp))
                            .border(2.dp, Aqua, RoundedCornerShape(10.dp)),
                    ) {
                        state.years.forEach {
                            DropdownMenuItem(onClick = {
                                chosenYear = it
                                isYearDropDownVisible = false
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

            var sum = 0.0
            var euroSum = 0.0

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
                    .padding(top = 20.dp, bottom = 20.dp)
                    .verticalScroll(rememberScrollState()),
            ) {
                for (activity in state.activities) {
                    if (activity.year == chosenYear && activity.month == chosenMonth
                        && activity.type == activityType) {
                        Column(modifier = Modifier.fillMaxWidth())
                        {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 15.dp)
                                    .combinedClickable(
                                        onClick = {},
                                        onLongClick = {
                                            onEvent(ActivityEvent.DeleteActivity(activity))
                                        }
                                    )
                            ) {
                                val month = if (activity.month < 10) "0${activity.month}" else activity.month
                                val currency = if (activity.currency == 1) "€" else ""
                                Text(
                                    text = "${activity.day}.$month: "
                                            + "${activity.amount}$currency ${activity.title}",
                                    style = Typography.body1
                                )
                                Text(
                                    " ${activity.source}",
                                    style = Typography.body1,
                                    color = when(activity.source) {
                                        'R' -> Revo
                                        'G' -> Cash
                                        else -> Bank
                                    },
                                )
                            }
                            if (activity.currency == 0) sum += activity.amount
                            else euroSum += activity.amount
                        }
                    }
                }
            }
            val roundOff = (sum * 100.0).roundToInt() / 100.0
            val euroRoundOff = (euroSum * 100.0).roundToInt() / 100.0
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(elevation = 15.dp, shape = RoundedCornerShape(25.dp, 25.dp, 0.dp, 0.dp))
                    .background(Background)
                    .background(TopPanelBackground)
                    .padding(bottom = 5.dp)
                    .padding(horizontal = 25.dp),
            ){
                Text(
                    text = "\n${monthTotal[language]}",
                    style = Typography.h1,
                    color = LightAqua
                )
                Text(
                    text = "${roundOff}PLN   ${euroRoundOff}€\n",
                    style = Typography.h2,
                )
            }
        }
    }
}