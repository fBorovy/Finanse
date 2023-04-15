package pl.fboro.finanse.database

import androidx.compose.runtime.*
import pl.fboro.finanse.currentDay
import pl.fboro.finanse.currentMonth
import pl.fboro.finanse.currentYear
import java.time.LocalDate

data class ActivityState(

    val activities: List<Activity> = emptyList(),
    val day: Int = currentDay,
    val month: Int = currentMonth,
    val year: Int = currentYear,
    val amount: Double = 0.0,
    val title: String = " ",
    val source: Char = 'R',
    val type: Int = 0,
    val isAddingActivity: Boolean = false,
    val sortType: SortType = SortType.YEAR_MONTH_DAY
)


