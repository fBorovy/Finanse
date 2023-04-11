package pl.fboro.finanse.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import pl.fboro.finanse.incomes
import pl.fboro.finanse.investments
import pl.fboro.finanse.polish
import pl.fboro.finanse.spendings
import pl.fboro.finanse.ui.theme.Aqua
import pl.fboro.finanse.ui.theme.Background
import pl.fboro.finanse.ui.theme.Typography

@Composable
fun MainScreen() {
    var chosenActivity by remember{mutableStateOf("spendings")}
    var language by remember{ mutableStateOf(polish) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
            .padding(top = 15.dp)
            .padding(horizontal = 15.dp)
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
                    .padding(vertical = 10.dp)
                    .weight(1f),
                contentAlignment = Alignment.Center
            ){
                Text(
                    text = spendings[language], style = Typography.h1
                )
            }
            Box(
                modifier = Modifier
                    .padding(horizontal = 5.dp)
                    .shadow(5.dp)
                    .clip(RoundedCornerShape(15.dp))
                    .background(Aqua)
                    .padding(10.dp)
                    .weight(1f),
                contentAlignment = Alignment.Center
            ){
                Text(
                    text = incomes[language], style = Typography.h1
                )
            }
            Box(
                modifier = Modifier
                    .padding(horizontal = 5.dp)
                    .clip(RoundedCornerShape(15.dp))
                    .shadow(5.dp)
                    .background(Aqua)
                    .padding(10.dp)
                    .weight(1f),
                contentAlignment = Alignment.Center
            ){
                Text(
                    text = investments[language], style = Typography.h1
                )
            }

        }

    }
}