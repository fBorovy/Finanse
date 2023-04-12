package pl.fboro.finanse

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.runtime.setValue
import pl.fboro.finanse.ui.theme.FinanseTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var language by remember{ mutableStateOf(polish) }

            FinanseTheme {
                Navigation(language) { language = it}
            }
        }
    }
}