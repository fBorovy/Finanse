package pl.fboro.finanse

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import pl.fboro.finanse.database.ActivityDatabase
import pl.fboro.finanse.ui.theme.FinanseTheme
import pl.fboro.finanse.viewModels.ActivityViewModel

class MainActivity : ComponentActivity() {

    private val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            ActivityDatabase::class.java,
            "activities.db",
        ).build()
    }

    private val viewModel by viewModels<ActivityViewModel>(
        factoryProducer = {
            object: ViewModelProvider.Factory {
                override fun <T: ViewModel> create(modelClass: Class<T>): T {
                    return ActivityViewModel(db.dao) as T
                }
            }
        }
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FinanseTheme {
                val state by viewModel.state.collectAsState()
                val investmentState by viewModel.investmentState.collectAsState()
                Navigation(
                    state = state,
                    investmentState = investmentState,
                    onEvent = viewModel::onEvent
                )
            }
        }
    }
}