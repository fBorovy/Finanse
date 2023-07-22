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
                            .size(24.dp)
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
                            .size(24.dp)
                            .clickable {
                                onEvent(
                                    ActivityEvent.SortActivities(
                                        sortType = if (activityType == spending) SortType.SPENDING_YEAR_MONTH_DAY
                                        else SortType.INCOME_YEAR_MONTH_DAY
                                    )
                                )
                                chosenSortType = 0
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
                            .border(2.dp, Aqua, RoundedCornerShape(10.dp)),
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

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 35.dp)
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
                        }
                    }
                }
                if (chosenSortType == 0) {
                    val roundOff = (sum * 100.0).roundToInt() / 100.0
                    Text(
                        text = "\n${monthTotal[language]}",
                        style = Typography.h1,
                        color = TextWhite
                    )
                    Text(
                        text = "$roundOff\n",
                        style = Typography.h1,
                        color = if (activityType == 0) Color.Red else Color.Green
                    )
                }
            }
        }
    }
}