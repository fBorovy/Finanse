package pl.fboro.finanse.ui.screens.mainScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.navOptions
import pl.fboro.finanse.*
import pl.fboro.finanse.R
import pl.fboro.finanse.ui.theme.LightAqua

@Composable
fun OptionsBar(
    navController: NavController,
    language: Int,
    changeLanguage: (Int) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 7.dp)
    ){
        Box(
            modifier = Modifier.weight(1f),
            contentAlignment = Alignment.TopStart
        ){
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = previousScreenIconDescription[language],
                modifier = Modifier
                    .size(30.dp)
                    .clickable {
                        navController.navigate(
                            route = Screen.Greeting.name,
                            navOptions = navOptions {
                                popUpTo(Screen.Greeting.name) { inclusive = true }
                            })
                    },
                tint = LightAqua,
            )
        }
        Box() {
            Image(
                painter = painterResource(id = R.drawable.polish),
                contentDescription = polishImageDesc[language],
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(30.dp, 30.dp)
                    .clip(CircleShape)
                    .clickable {
                        changeLanguage(
                            polish
                        )
                    }
            )
        }
        Box(
            modifier = Modifier
                .padding(horizontal = 5.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.english),
                contentDescription = englishImageDesc[language],
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(30.dp, 30.dp)
                    .clip(CircleShape)
                    .clickable {
                        changeLanguage(
                            english
                        )
                    }
            )
        }
    }
}