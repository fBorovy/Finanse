package pl.fboro.finanse.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import pl.fboro.finanse.currentDay
import pl.fboro.finanse.currentMonth
import pl.fboro.finanse.currentYear
import pl.fboro.finanse.database.*
import pl.fboro.finanse.spending

@OptIn(ExperimentalCoroutinesApi::class)
class ActivityViewModel(
    private val dao: ActivityDao,
): ViewModel() {

    private val _years = dao.getYearsRange()
    private val _sortType = MutableStateFlow(SortType.YEAR_MONTH_DAY)
    private val _activities = _sortType
        .flatMapLatest { sortType ->
            when(sortType){
                SortType.YEAR_MONTH_DAY -> {
//                    if (activityType == 0) dao.getSpendingsOrderedByDate()
//                    else dao.getIncomesOrderedByDate()
                    dao.getSpendingsOrderedByDate()
                }
                SortType.AMOUNT -> {
//                    if (activityType == 0) dao.getSpendingsOrderedByAmount()
//                    else dao.getIncomesOrderedByAmount()
                    dao.getSpendingsOrderedByAmount()
                }
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())
    private val _state = MutableStateFlow(ActivityState())
    val state = combine(_state, _sortType, _activities, _years) { state, sortType, activities, years ->
        state.copy(
            activities = activities,
            sortType = sortType,
            years = years,
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ActivityState())


    fun onEvent(event: ActivityEvent) {
        when(event) {
//            is ActivityEvent.SetYears -> {
//                _state.update { it.copy(
//                    years = dao.getYearsRange()
//                ) }
//            }
            is ActivityEvent.DeleteActivity -> {
                viewModelScope.launch {
                    dao.deleteActivity(event.activity)
                }
            }
            ActivityEvent.HideAddingDialog -> _state.update { it.copy(
                isAddingActivity = false
            ) }
            is ActivityEvent.SaveActivity -> {
                val day = state.value.day
                val month = state.value.month
                val year = state.value.year
                val amount = state.value.amount
                val title = state.value.title
                val source = state.value.source

                if (day == 0 || month == 0 || year == 0 || amount == 0.0 || title.isBlank()){
                    return
                }

                val activity = Activity(
                    day = day,
                    month = month,
                    year = year,
                    amount = amount,
                    title = title,
                    source = source,
                    type = spending,
                )
                viewModelScope.launch {
                    dao.upsertActivity(activity)
                }
                _state.update { it.copy(
                    isAddingActivity = false,
                    day = currentDay,
                    month = currentMonth,
                    year = currentYear,
                    amount = 0.0,
                    title = "",
                    source = 'R',
                )}
            }
            ActivityEvent.ShowAddingDialog -> _state.update { it.copy(
                isAddingActivity = true
            ) }
            is ActivityEvent.SortActivities -> {
                _sortType.value = event.sortType
            }
            is ActivityEvent.SetAmount -> {
                _state.update { it.copy(
                    amount = event.amount
                ) }
            }
            is ActivityEvent.SetDay -> {
                _state.update { it.copy(
                    day = event.day
                ) }
            }
            is ActivityEvent.SetMonth -> {
                _state.update { it.copy(
                    month = event.month
                ) }
            }
            is ActivityEvent.SetSource -> {
                _state.update { it.copy(
                    source = event.source
                ) }
            }
            is ActivityEvent.SetTitle -> {
                _state.update { it.copy(
                    title = event.title
                ) }
            }
            is ActivityEvent.SetType -> {
                _state.update { it.copy(
                    type = event.type
                ) }
            }
            is ActivityEvent.SetYear -> {
                _state.update { it.copy(
                    year = event.year
                ) }
            }
        }
    }
}
