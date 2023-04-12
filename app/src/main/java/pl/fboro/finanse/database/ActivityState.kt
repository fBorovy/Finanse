package pl.fboro.finanse.database

import androidx.compose.runtime.*
import java.time.LocalDate

data class ActivityState(

    val activities: List<Activity> = emptyList(),
    val day: Int = 0,
    val month: Int = 0,
    val year: Int = 0,
    val amount: Double = 0.0,
    val title: String = "",
    val source: Char = ' ',
    val type: Int = 0,
    val isAddingActivity: Boolean = false,
    val sortType: SortType = SortType.YEAR_MONTH_DAY
)
