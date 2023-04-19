package pl.fboro.finanse.ui.screens.mainScreen

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import pl.fboro.finanse.*
import pl.fboro.finanse.database.ActivityEvent
import pl.fboro.finanse.database.ActivityState
import pl.fboro.finanse.ui.theme.*

@Composable
fun AddActivityDialog(
    state: ActivityState,
    activityType: Int,
    language: Int,
    modifier: Modifier = Modifier,
    onEvent: (ActivityEvent) -> Unit,
) {
    val title = if (activityType == spending) addSpendingDialogTitle[language]
    else addIncomeDialogTitle[language]

    var source by remember { mutableStateOf('R') }
    var day by remember { mutableStateOf("$currentDay") }
    var month by remember { mutableStateOf("$currentMonth") }
    var year by remember { mutableStateOf("$currentYear") }
    var amount by remember { mutableStateOf(" ")}


    AlertDialog(
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .border(2.dp, Aqua, shape = RoundedCornerShape(10.dp)),
        backgroundColor = Background,
        onDismissRequest = {
            onEvent(ActivityEvent.HideAddingDialog)
        },
        title = { Text(text = title, color = TextWhite) },
        text = {
           Column {
               OutlinedTextField(
                   value = day,
                   colors = TextFieldDefaults.outlinedTextFieldColors(
                       focusedBorderColor = LightAqua,
                       unfocusedBorderColor = Aqua,
                       textColor = TextWhite),
                   onValueChange = {
                       day = it
                   },
                   keyboardOptions = KeyboardOptions(
                       keyboardType = KeyboardType.Number,
                       imeAction = ImeAction.Next,
                   ),
                   label = {Text(text = dayPlaceHolder[language], color = TextWhite)},
               )
               OutlinedTextField(
                   value = month,
                   colors = TextFieldDefaults.outlinedTextFieldColors(
                       focusedBorderColor = LightAqua,
                       unfocusedBorderColor = Aqua,
                       textColor = TextWhite),
                   onValueChange = {
                       month = it
                   },
                   keyboardOptions = KeyboardOptions(
                       keyboardType = KeyboardType.Number,
                       imeAction = ImeAction.Next,
                   ),
                   label = {Text(text = monthPlaceHolder[language], color = TextWhite)},
               )
               OutlinedTextField(
                   value = year,
                   colors = TextFieldDefaults.outlinedTextFieldColors(
                       focusedBorderColor = LightAqua,
                       unfocusedBorderColor = Aqua,
                       textColor = TextWhite),
                   onValueChange = {
                       year = it
                   },
                   keyboardOptions = KeyboardOptions(
                       keyboardType = KeyboardType.Number,
                       imeAction = ImeAction.Next,
                   ),
                   label = {Text(text = yearPlaceHolder[language], color = TextWhite)},
               )
               OutlinedTextField(
                   value = amount,
                   colors = TextFieldDefaults.outlinedTextFieldColors(
                       focusedBorderColor = LightAqua,
                       unfocusedBorderColor = Aqua,
                       textColor = TextWhite),
                   onValueChange = {
                       amount = it
                   },
                   keyboardOptions = KeyboardOptions(
                       keyboardType = KeyboardType.Number,
                       imeAction = ImeAction.Next,
                   ),
                   label = {Text(text = amountPlaceHolder[language], color = TextWhite)},
               )
               OutlinedTextField(
                   value = state.title,
                   colors = TextFieldDefaults.outlinedTextFieldColors(
                       focusedBorderColor = LightAqua,
                       unfocusedBorderColor = Aqua,
                       textColor = TextWhite),
                   onValueChange = {
                       onEvent(ActivityEvent.SetTitle(it))
                   },
                   keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                   label = {Text(text = titlePlaceHolder[language], color = TextWhite)},
               )
               Row(
                   verticalAlignment = Alignment.CenterVertically,
                   horizontalArrangement = Arrangement.SpaceEvenly,
                   modifier = modifier
                       .fillMaxWidth()
                       .padding(top = 8.dp)
               ) {
                   Box(
                       modifier = Modifier
                           .border(1.dp, Aqua, RoundedCornerShape(10.dp))
                           .padding(vertical = 5.dp, horizontal = 15.dp)
                           .clickable {
                               source = 'R'
                           }
                   ){
                       Text(
                           text = "Revolut",
                           color = if (source == 'R') Aqua else TextGray,
                       )
                   }
                   Box(
                       modifier = Modifier
                           .border(1.dp, Aqua, RoundedCornerShape(10.dp))
                           .padding(vertical = 5.dp, horizontal = 15.dp)
                           .clickable {
                               source = 'G'
                           }
                   ) {
                       Text(
                           text = "Gotówka",
                           color = if (source == 'G') Aqua else TextGray,
                       )
                   }
                   Box(
                       modifier = Modifier
                           .border(1.dp, Aqua, RoundedCornerShape(10.dp))
                           .padding(vertical = 5.dp, horizontal = 15.dp)
                           .clickable {
                               source = 'B'
                           }
                   ) {
                       Text(
                           text = "Bank",
                           color = if (source == 'B') Aqua else TextGray,
                       )
                   }
               }
           }
        },
        buttons = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 15.dp)
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 30.dp, end = 15.dp,)
                        .clickable {
                            onEvent(ActivityEvent.HideAddingDialog)
                        }
                        .border(1.dp, Aqua, RoundedCornerShape(10.dp))
                        .padding(vertical = 5.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = cancelButton[language], color = TextWhite
                    )
                }
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 15.dp, end = 30.dp)
                        .clickable {
                            onEvent(ActivityEvent.SetDay(day.toInt()))
                            onEvent(ActivityEvent.SetMonth(month.toInt()))
                            onEvent(ActivityEvent.SetYear(year.toInt()))
                            onEvent(ActivityEvent.SetAmount(amount.toDouble()))
                            when (source) {
                                'R' -> onEvent(ActivityEvent.SetSource('R'))
                                'G' -> onEvent(ActivityEvent.SetSource('G'))
                                'B' -> onEvent(ActivityEvent.SetSource('B'))
                            }
                            onEvent(ActivityEvent.SetType(activityType))
                            onEvent(ActivityEvent.SaveActivity)
                        }
                        .border(1.dp, Aqua, RoundedCornerShape(10.dp))
                        .padding(vertical = 5.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = saveButton[language], color = TextWhite
                    )
                }
            }
        }
    )
}