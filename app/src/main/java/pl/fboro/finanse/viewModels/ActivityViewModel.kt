package pl.fboro.finanse.viewModels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import pl.fboro.finanse.database.*

@OptIn(ExperimentalCoroutinesApi::class)
class ActivityViewModel(
    private val dao: ActivityDao,
    private val activityType: Int
): ViewModel() {

    private val _sortType = MutableStateFlow(SortType.YEAR_MONTH_DAY)
    private val _activities = _sortType
        .flatMapLatest { sortType ->
            when(sortType){
                SortType.YEAR_MONTH_DAY -> {
                    if (activityType == 0) dao.getSpendingsOrderedByDate()
                    else dao.getIncomesOrderedByDate()
                }
                SortType.AMOUNT -> {
                    if (activityType == 0) dao.getSpendingsOrderedByAmount()
                    else dao.getIncomesOrderedByAmount()
                }
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())
    private val _state = MutableStateFlow(ActivityState())
    val state = combine(_state, _sortType, _activities) { state, sortType, activities ->
        state.copy(
            activities = activities,
            sortType = sortType,
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ActivityState())


    fun onEvent(event: ActivityEvent, activityType: Int) {
        when(event) {
            is ActivityEvent.DeleteActivity -> {
                viewModelScope.launch {
                    dao.deleteActivity(event.activity)
                }
            }
            ActivityEvent.HideDialog -> _state.update { it.copy(
                isAddingActivity = false
            ) }
            ActivityEvent.SaveActivity -> {
                val day = state.value.day
                val month = state.value.month
                val year = state.value.year
                val amount = state.value.amount
                val title = state.value.title
                val source = state.value.source

                if (day == 0 || month == 0 || year == 0 || amount == 0.0 || title.isBlank() ||
                        source.isWhitespace()){
                    return
                }

                val activity = Activity(
                    day = day,
                    month = month,
                    year = year,
                    amount = amount,
                    title = title,
                    source = source,
                    type = activityType
                )
                viewModelScope.launch {
                    dao.upsertActivity(activity)
                }
                _state.update { it.copy(
                    isAddingActivity = false,
                    day = 0,
                    month = 0,
                    year = 0,
                    amount = 0.0,
                    title = "",
                    source = ' ',
                )}
            }
            ActivityEvent.ShowDialog -> _state.update { it.copy(
                isAddingActivity = true
            ) }
            is ActivityEvent.SortActivities -> {
                _sortType.value = event.sortType
            }
            is ActivityEvent.setAmount -> {
                _state.update { it.copy(
                    amount = event.amount
                ) }
            }
            is ActivityEvent.setDay -> {
                _state.update { it.copy(
                    day = event.day
                ) }
            }
            is ActivityEvent.setMonth -> {
                _state.update { it.copy(
                    month = event.month
                ) }
            }
            is ActivityEvent.setSource -> {
                _state.update { it.copy(
                    source = event.source
                ) }
            }
            is ActivityEvent.setTitle -> {
                _state.update { it.copy(
                    title = event.title
                ) }
            }
            is ActivityEvent.setType -> {
                _state.update { it.copy(
                    type = event.type
                ) }
            }
            is ActivityEvent.setYear -> {
                _state.update { it.copy(
                    year = event.year
                ) }
            }

        }
    }
}
