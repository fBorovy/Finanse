package pl.fboro.finanse

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import pl.fboro.finanse.ui.theme.FinanseTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FinanseTheme {
                Navigation()
            }
        }
    }
}