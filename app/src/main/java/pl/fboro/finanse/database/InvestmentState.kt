package pl.fboro.finanse.database

import pl.fboro.finanse.currentDay
import pl.fboro.finanse.currentMonth
import pl.fboro.finanse.currentYear

data class InvestmentState(

    val investments: List<Investment> = emptyList(),
    val day: Int = currentDay,
    val month: Int = currentMonth,
    val year: Int = currentYear,
    val instrument: String = "",
    val investedIn: Double = 0.0,
    val takenOut: Double = 0.0,
    val difference: Double = 0.0,
    val isAddingInvestment: Boolean = false,
    val sortType: InvestmentSortType = InvestmentSortType.DATE,
    val years: List<Int> = listOf(currentYear),
)
