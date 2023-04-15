package pl.fboro.finanse.ui.screens.mainScreen

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pl.fboro.finanse.*
import pl.fboro.finanse.database.ActivityEvent
import pl.fboro.finanse.database.ActivityState
import pl.fboro.finanse.ui.theme.Aqua
import pl.fboro.finanse.ui.theme.Background
import pl.fboro.finanse.ui.theme.LightAqua
import pl.fboro.finanse.ui.theme.TextWhite

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
               //Row(){
                   OutlinedTextField(
                       value = state.day.toString(),
                       colors = TextFieldDefaults.outlinedTextFieldColors(
                           focusedBorderColor = LightAqua,
                           unfocusedBorderColor = Aqua,
                           textColor = TextWhite),
                       onValueChange = {
                           onEvent(ActivityEvent.SetDay(10))
                       },
                       label = {Text(text = dayPlaceHolder[language], color = TextWhite)},
                   )
                   OutlinedTextField(
                       value = state.month.toString(),
                       colors = TextFieldDefaults.outlinedTextFieldColors(
                           focusedBorderColor = LightAqua,
                           unfocusedBorderColor = Aqua,
                           textColor = TextWhite),
                       onValueChange = {
                           onEvent(ActivityEvent.SetMonth(10))
                       },
                       label = {Text(text = monthPlaceHolder[language], color = TextWhite)},
                   )
               OutlinedTextField(
                   value = state.year.toString(),
                   colors = TextFieldDefaults.outlinedTextFieldColors(
                       focusedBorderColor = LightAqua,
                       unfocusedBorderColor = Aqua,
                       textColor = TextWhite),
                   onValueChange = {
                       onEvent(ActivityEvent.SetYear(10))
                   },
                   label = {Text(text = yearPlaceHolder[language], color = TextWhite)},
               )
               OutlinedTextField(
                   value = state.amount.toString(),
                   colors = TextFieldDefaults.outlinedTextFieldColors(
                       focusedBorderColor = LightAqua,
                       unfocusedBorderColor = Aqua,
                       textColor = TextWhite),
                   onValueChange = {
                       onEvent(ActivityEvent.SetAmount(10.0))
                   },
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
                   label = {Text(text = titlePlaceHolder[language], color = TextWhite)},
               )
               Row(
                   verticalAlignment = Alignment.CenterVertically,
                   horizontalArrangement = Arrangement.SpaceEvenly,
                   modifier = modifier.fillMaxWidth().padding(top = 8.dp)
               ) {
                   Box(
                       modifier = Modifier
                           .border(1.dp, Aqua)
                           .padding(5.dp)
                           .clickable{
                               source = 'R'
                           }
                   ){
                       Text(
                           text = "Revolut",
                           color = if (source == 'R') Aqua else TextWhite,
                       )
                   }
                   Box(
                       modifier = Modifier
                           .border(1.dp, Aqua)
                           .padding(5.dp)
                           .clickable{
                               source = 'G'
                           }
                   ) {
                       Text(
                           text = "Gotówka",
                           color = if (source == 'G') Aqua else TextWhite,
                       )
                   }
                   Box(
                       modifier = Modifier
                           .border(1.dp, Aqua)
                           .padding(5.dp)
                           .clickable{
                               source = 'B'
                           }
                   ) {
                       Text(
                           text = "Bank",
                           color = if (source == 'B') Aqua else TextWhite,
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
                        .clickable {
                            onEvent(ActivityEvent.HideAddingDialog)
                        }
                        .border(1.dp, Aqua),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = cancelButton[language], color = TextWhite
                    )
                }
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clickable {
                            onEvent(ActivityEvent.SaveActivity)
                        }
                        .border(1.dp, Aqua),
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