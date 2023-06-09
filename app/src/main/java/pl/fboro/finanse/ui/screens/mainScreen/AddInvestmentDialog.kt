package pl.fboro.finanse.ui.screens.mainScreen

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.AlertDialog
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import pl.fboro.finanse.*
import pl.fboro.finanse.database.ActivityEvent
import pl.fboro.finanse.database.InvestmentState
import pl.fboro.finanse.ui.theme.*

@Composable
fun AddInvestmentDialog(
    investmentState: InvestmentState,
    language: Int,
    modifier: Modifier = Modifier,
    onEvent: (ActivityEvent) -> Unit,
)
     {
        var day by remember { mutableStateOf("$currentDay") }
        var month by remember { mutableStateOf("$currentMonth") }
        var year by remember { mutableStateOf("$currentYear") }
        var amountIn by remember { mutableStateOf(0.0) }
         var amountInString by remember { mutableStateOf("") }
        var amountOut by remember { mutableStateOf(0.0) }
         var amountOutString by remember { mutableStateOf("") }
         var difference by remember { mutableStateOf(0.0) }


        AlertDialog(
            modifier = modifier
                .clip(RoundedCornerShape(10.dp))
                .border(2.dp, Aqua, shape = RoundedCornerShape(10.dp)),
            backgroundColor = Background,
            onDismissRequest = {
                onEvent(ActivityEvent.HideAddingInvestmentDialog)
            },
            title = { Text(text = addInvestmentDialogTitle[language], color = TextWhite) },
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
                        label = { Text(text = dayPlaceHolder[language], color = TextWhite) },
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
                        label = { Text(text = monthPlaceHolder[language], color = TextWhite) },
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
                        label = { Text(text = yearPlaceHolder[language], color = TextWhite) },
                    )
                    OutlinedTextField(
                        value = investmentState.instrument,
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = LightAqua,
                            unfocusedBorderColor = Aqua,
                            textColor = TextWhite),
                        onValueChange = {
                            onEvent(ActivityEvent.SetInstrument(it))
                        },
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                        label = {Text(text = instrumentPlaceHolder[language], color = TextWhite)},
                    )
                    OutlinedTextField(value = amountInString,
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = LightAqua,
                            unfocusedBorderColor = Aqua,
                            textColor = TextWhite),
                        onValueChange = {
                            amountInString = it
                            if (it.isNotEmpty()) {
                                amountIn = it.toDouble()
                                difference = amountOut - amountIn
                            }
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Next,
                        ),
                        label = { Text(text = amountInPlaceHolder[language], color = TextWhite) },
                    )
                    OutlinedTextField(
                        value = amountOutString,
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = LightAqua,
                            unfocusedBorderColor = Aqua,
                            textColor = TextWhite),
                        onValueChange = {
                            amountOutString = it
                            if (it.isNotEmpty()) {
                                amountOut = it.toDouble()
                                difference = amountOut - amountIn
                            }
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Done,
                        ),
                        label = { Text(text = amountOutPlaceHolder[language], color = TextWhite) },
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp)
                            .clip(RoundedCornerShape(5.dp))
                            .border(1.dp, Aqua)
                            .padding(5.dp),
                        contentAlignment = Alignment.Center
                    ){
                        Text(
                            text = investmentResult[language] + ": " + difference.toString()
                        )
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
                            .padding(start = 30.dp, end = 15.dp)
                            .clickable {
                                onEvent(ActivityEvent.HideAddingInvestmentDialog)
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
                                onEvent(ActivityEvent.SetIDay(day.toInt()))
                                onEvent(ActivityEvent.SetIMonth(month.toInt()))
                                onEvent(ActivityEvent.SetIYear(year.toInt()))
                                onEvent(ActivityEvent.SetInvestedIn(amountIn))
                                onEvent(ActivityEvent.SetTakenOut(amountOut))
                                onEvent(ActivityEvent.SetDifference(difference))
                                onEvent(ActivityEvent.SaveInvestment)
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