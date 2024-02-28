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
import pl.fboro.finanse.database.*
import pl.fboro.finanse.ui.theme.*

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun InvestmentsContent(
    investmentState: InvestmentState,
    onEvent: (ActivityEvent) -> Unit,
    language: Int,
) {
    var chosenSortType by remember{ mutableStateOf(0) }
    var isDropDownVisible by remember { mutableStateOf(false) }
    var chosenYear by remember { mutableStateOf(currentYear) }
    var result = 0.0

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                onEvent(ActivityEvent.ShowAddingInvestmentDialog)
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
                .background(Background)
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
                        painterResource(id = pl.fboro.finanse.R.drawable.calendar),
                        contentDescription = calendarIconDesc[language],
                        colorFilter = if (chosenSortType == 0) ColorFilter.tint(LightAqua)
                        else ColorFilter.tint(Aqua),
                        modifier = Modifier
                            .size(24.dp)
                            .clickable {
                                onEvent(ActivityEvent.SortInvestments(investmentSortType = InvestmentSortType.DATE))
                                chosenSortType = 0
                            }
                    )
                }
                Box(
                    modifier = Modifier.padding(horizontal = 5.dp)
                ) {
                    Image(
                        painterResource(id = pl.fboro.finanse.R.drawable.dollar),
                        contentDescription = dollarIconDesc[language],
                        colorFilter = if (chosenSortType == 1) ColorFilter.tint(LightAqua)
                        else ColorFilter.tint(Aqua),
                        modifier = Modifier
                            .size(24.dp)
                            .clickable {
                                onEvent(
                                    ActivityEvent.SortInvestments(investmentSortType = InvestmentSortType.PROFIT)
                                )
                                chosenSortType = 1
                            },
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
                        investmentState.years.forEach {
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
            if(investmentState.isAddingInvestment) {
                AddInvestmentDialog(
                    investmentState = investmentState,
                    language = language,
                    onEvent = onEvent
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
                    .padding(top = 20.dp, bottom = 20.dp)
                    .verticalScroll(rememberScrollState()),
            ) {
                for (investment in investmentState.investments) {
                    if (investment.year == chosenYear) {
                        result += investment.difference
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 15.dp)
                                .combinedClickable(
                                    onClick = {},
                                    onLongClick = {
                                        onEvent(ActivityEvent.DeleteInvestment(investment))
                                    }
                                )
                        ) {
                            Text(
                                text = "${investment.investedIn} ${paidFor[language]}" +
                                        " ${investment.instrument}\n${sold[language]} ${investment.takenOut}",
                                style = Typography.body1
                            )
                            Row(
                                modifier = Modifier.fillMaxWidth()
                            ){
                                Text(
                                    text = if (investment.month < 10) "${investment.day}.0${investment.month}"
                                    else "${investment.day}.0${investment.month}"
                                )
                                Text(
                                    text = "\t\t${investment.difference}\n",
                                    color = if (investment.difference >= 0) Bank else Cash,
                                    style = Typography.body1
                                )
                            }
                        }
                    }
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(elevation = 25.dp, shape = RoundedCornerShape(25.dp, 25.dp, 0.dp, 0.dp))
                    .background(Background)
                    .background(TopPanelBackground)
                    .padding(bottom = 5.dp)
                    .padding(horizontal = 25.dp),
            ){
                Text(
                    text = "\n${investmentYearTotal[language]}",
                    style = Typography.h1,
                    color = LightAqua //if (result >= 0) Bank else Cash
                )
                Text(
                    text = if (result > 0) "+$result\n" else "$result\n",
                    style = Typography.h2,
                    //color = if (result >= 0) Bank else Cash
                )
            }
        }
    }
}