package pl.fboro.finanse.ui.screens.mainScreen

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import pl.fboro.finanse.*
import pl.fboro.finanse.R
import pl.fboro.finanse.database.ActivityEvent
import pl.fboro.finanse.database.ActivityState
import pl.fboro.finanse.database.SortType
import pl.fboro.finanse.ui.theme.Aqua
import pl.fboro.finanse.ui.theme.Background
import pl.fboro.finanse.ui.theme.LightAqua
import pl.fboro.finanse.ui.theme.Typography

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SpendingContent(
    state: ActivityState,
    onEvent: (ActivityEvent) -> Unit,
    language: Int
) {
    var chosenSortType by remember{ mutableStateOf(0) }
    var isDropDownVisible by remember { mutableStateOf(false) }
    var chosenYear by remember { mutableStateOf(2023) }

    Scaffold(
        modifier = Modifier
            .padding(top = 5.dp),
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
                            .size(25.dp)
                            .clickable {
                                onEvent(ActivityEvent.SortActivities(sortType = SortType.AMOUNT))
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
                            .size(25.dp)
                            .clickable {
                                onEvent(ActivityEvent.SortActivities(sortType = SortType.YEAR_MONTH_DAY))
                                chosenSortType = 0
                            }
                    )
                }
                Box(
                    modifier = Modifier
                        .weight(1f),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    Text(
                        "$currentYear",
                        style = Typography.h2,
                        modifier = Modifier
                            .clickable{
                                isDropDownVisible = !isDropDownVisible
                            }
                    )
                    DropdownMenu(
                        expanded = isDropDownVisible,
                        onDismissRequest = { isDropDownVisible = false }
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
                    activityType = spending,
                    language = language,
                    onEvent = onEvent
                )
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 30.dp)
                    .horizontalScroll(rememberScrollState()),
            ) {
                items(state.activities) { activity ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .combinedClickable(
                                onClick = {},
                                onLongClick = {
                                    onEvent(ActivityEvent.ShowEditDeleteDialog)
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
                                'R' -> Color.Blue
                                'G' -> Color.Red
                                else -> Color.Green
                            },
//                        if (activity.source == 'R') Color.Blue
//                        else if (activity.source == 'G') Color.Red
//                        else Color.Green
                        )
                    }
                }
            }
        }

    }
}