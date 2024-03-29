package pl.fboro.finanse.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import pl.fboro.finanse.*
import pl.fboro.finanse.database.*

@OptIn(ExperimentalCoroutinesApi::class)
class ActivityViewModel(
    private val dao: ActivityDao,
): ViewModel() {

    private val _years = dao.getYearsRange()
    private val _sortType = MutableStateFlow(SortType.SPENDING_YEAR_MONTH_DAY)
    private val _activities = _sortType
        .flatMapLatest { sortType ->
            when(sortType){
                SortType.SPENDING_YEAR_MONTH_DAY -> dao.getSpendingsOrderedByDate()
                SortType.SPENDING_YEAR_MONTH_DAY_ASC -> dao.getSpendingsOrderedByDateAsc()
                SortType.SPENDING_AMOUNT -> dao.getSpendingsOrderedByAmount()
                SortType.SPENDING_AMOUNT_ASC -> dao.getSpendingsOrderedByAmountAsc()
                SortType.INCOME_YEAR_MONTH_DAY -> dao.getIncomesOrderedByDate()
                SortType.INCOME_YEAR_MONTH_DAY_ASC -> dao.getIncomesOrderedByDateAsc()
                SortType.INCOME_AMOUNT -> dao.getIncomesOrderedByAmount()
                SortType.INCOME_AMOUNT_ASC -> dao.getIncomesOrderedByAmountAsc()
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


    private val _investmentYears = dao.getInvestmentYearsRange()
    private val _investmentSortType = MutableStateFlow(InvestmentSortType.DATE)
    private val _investments = _investmentSortType
        .flatMapLatest{ sortType ->
            when(sortType) {
                InvestmentSortType.DATE -> dao.getInvestmentsOrderedByTime()
                InvestmentSortType.DATE_ASC -> dao.getInvestmentsOrderedByTimeAsc()
                InvestmentSortType.PROFIT -> dao.getInvestmentsOrderedByProfitAmount()
                InvestmentSortType.PROFIT_ASC -> dao.getInvestmentsOrderedByProfitAmountAsc()
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())
    private val _investmentState = MutableStateFlow(InvestmentState())
    val investmentState = combine(_investmentState, _investmentSortType, _investments, _investmentYears) { state, sortType, investments, years ->
        state.copy(
            investments = investments,
            sortType = sortType,
            years = years,
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), InvestmentState())



    fun onEvent(event: ActivityEvent) {
        when(event) {
            is ActivityEvent.DeleteActivity -> {
                viewModelScope.launch {
                    dao.deleteActivity(event.activity)
                }
            }
            ActivityEvent.HideAddingDialog -> _state.update { it.copy(
                isAddingActivity = false
            ) }
            is ActivityEvent.SaveActivity -> {
                val day = _state.value.day
                val month = _state.value.month
                val year = _state.value.year
                val amount = _state.value.amount
                val title = _state.value.title
                val source = _state.value.source
                val type = _state.value.type
                val currency = _state.value.currency

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
                    type = type,
                    currency = currency,
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

            is ActivityEvent.SetCurrency -> {
                _state.update { it.copy(
                    currency = event.currency
                )}
            }

            //investments

            is ActivityEvent.DeleteInvestment -> {
                viewModelScope.launch {
                    dao.deleteInvestment(event.investment)
                }
            }

            ActivityEvent.HideAddingInvestmentDialog -> _investmentState.update { it.copy(
                isAddingInvestment = false
            ) }

            ActivityEvent.SaveInvestment -> {
                val day = _investmentState.value.day
                val month = _investmentState.value.month
                val year = _investmentState.value.year
                val instrument = _investmentState.value.instrument
                val investedIn = _investmentState.value.investedIn
                val takenOut = _investmentState.value.takenOut
                val difference = _investmentState.value.difference

                if (day == 0 || month == 0 || year == 0 || instrument.isBlank()
                    || investedIn == 0.0 ){
                    return
                }

                val investment = Investment(
                    day = day,
                    month = month,
                    year = year,
                    instrument = instrument,
                    investedIn = investedIn,
                    takenOut = takenOut,
                    difference = difference,
                )
                viewModelScope.launch {
                    dao.upsertInvestment(investment)
                }
                _investmentState.update { it.copy(
                        isAddingInvestment = false,
                        day = currentDay,
                        month = currentMonth,
                        year = currentYear,
                        instrument = "",
                        investedIn = 0.0,
                        takenOut = 0.0,
                        difference = 0.0,
                ) }
            }
            is ActivityEvent.SetDifference -> _investmentState.update { it.copy(
                difference = event.difference
            ) }
            is ActivityEvent.SetIDay -> _investmentState.update {it.copy(
                day = event.iDay
            )}
            is ActivityEvent.SetIMonth -> _investmentState.update {it.copy(
                month = event.iMonth
            )}
            is ActivityEvent.SetIYear -> _investmentState.update {it.copy(
                year = event.iYear
            )}
            is ActivityEvent.SetInvestedIn -> _investmentState.update {it.copy(
                investedIn = event.investedIn
            )}
            is ActivityEvent.SetTakenOut -> _investmentState.update {it.copy(
                takenOut = event.takenOut
            )}
            ActivityEvent.ShowAddingInvestmentDialog -> _investmentState.update { it.copy(
                isAddingInvestment = true
            ) }
            is ActivityEvent.SortInvestments -> _investmentSortType.value = event.investmentSortType
            is ActivityEvent.SetInstrument -> _investmentState.update { it.copy(
                instrument = event.instrument
            ) }
        }
    }
}
