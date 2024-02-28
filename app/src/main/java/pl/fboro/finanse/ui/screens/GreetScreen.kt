package pl.fboro.finanse.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import pl.fboro.finanse.Screen
import pl.fboro.finanse.greeting
import pl.fboro.finanse.ui.theme.Aqua
import pl.fboro.finanse.ui.theme.TopPanelBackground

@Composable
fun GreetScreen(navController: NavController, language: Int) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(TopPanelBackground)
            .padding(horizontal = 25.dp),
        contentAlignment = Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(15.dp)
                .clip(RoundedCornerShape(15.dp))
                .background(Aqua)
                .clickable{
                    navController.navigate(route = Screen.MainScreen.name)
                }
                .padding(vertical = 50.dp),
            contentAlignment = Center
        ) {
            Text(
                text = greeting[language],
                fontSize = 40.sp,
                color = TopPanelBackground,
            )
        }

    }
}