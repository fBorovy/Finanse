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
import java.time.LocalDate
import java.time.LocalTime

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
                Navigation(state = state, onEvent = viewModel::onEvent)
            }
        }
    }
}


//@Composable
//fun DateTest() {
//    var pickedDate by remember {
//        mutableStateOf(LocalDate.now())
//    }
//    var pickedTime by remember {
//        mutableStateOf(LocalTime.NOON)
//    }
//    val formattedTime by remember{
//        derivedStateOf
//    }
//}