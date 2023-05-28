package pl.fboro.finanse.ui.screens.mainScreen

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import pl.fboro.finanse.*
import pl.fboro.finanse.database.Activity
import pl.fboro.finanse.database.ActivityEvent
import pl.fboro.finanse.ui.theme.Aqua
import pl.fboro.finanse.ui.theme.Background
import pl.fboro.finanse.ui.theme.TextWhite

@Composable
fun DeleteActivityDialog(
    activityType: Int,
    language: Int,
    activity: Activity,
    modifier: Modifier = Modifier,
    onEvent: (ActivityEvent) -> Unit,
) {
    val title = if (activityType == spending) DeleteSpendingDialogTitle[language]
    else DeleteIncomeDialogTitle[language]

    AlertDialog(
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .border(2.dp, Aqua, shape = RoundedCornerShape(10.dp)),
        backgroundColor = Background,
        onDismissRequest = {
            onEvent(ActivityEvent.HideDeleteDialog)
        },
        title = { Text(text = title, color = TextWhite) },
        text = {
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
                            onEvent(ActivityEvent.HideDeleteDialog)
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
                            onEvent(ActivityEvent.DeleteActivity(activity))
                        }
                        .border(1.dp, Aqua, RoundedCornerShape(10.dp))
                        .padding(vertical = 5.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = deleteButton[language], color = TextWhite
                    )
                }
            }
        }
    )
}

