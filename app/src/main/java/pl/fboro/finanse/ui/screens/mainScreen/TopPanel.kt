package pl.fboro.finanse.ui.screens.mainScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.DragInteraction
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.navigation.NavController
import androidx.navigation.navOptions
import pl.fboro.finanse.*
import pl.fboro.finanse.R
import pl.fboro.finanse.ui.theme.Aqua
import pl.fboro.finanse.ui.theme.TextWhite
import pl.fboro.finanse.ui.theme.Typography

@Composable
fun TopPanel(
    language: Int,
    changeActivity: (String) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
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
                    .clickable {
                        changeActivity("spendings")
                    }
                    .padding(vertical = 10.dp)
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = spendings[language], style = Typography.h1, maxLines = 1
                )
            }
            Box(
                modifier = Modifier
                    .padding(horizontal = 5.dp)
                    .shadow(5.dp)
                    .clip(RoundedCornerShape(15.dp))
                    .background(Aqua)
                    .clickable {
                        changeActivity("incomes")
                    }
                    .padding(10.dp)
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = incomes[language], style = Typography.h1, maxLines = 1
                )
            }
            Box(
                modifier = Modifier
                    .padding(horizontal = 5.dp)
                    .clip(RoundedCornerShape(15.dp))
                    .shadow(5.dp)
                    .background(Aqua)
                    .clickable {
                        changeActivity("investments")
                    }
                    .padding(10.dp)
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = investments[language], style = Typography.h1, maxLines = 1
                )
            }
        }
    }
}