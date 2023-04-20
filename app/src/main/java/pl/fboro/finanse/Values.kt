package pl.fboro.finanse

import java.util.*

const val english = 0
const val polish = 1

const val spending = 0
const val income = 1
const val investment = 2

val spendings = listOf("Spendings", "Wydatki")
val incomes = listOf("Incomes", "Dochody")
val investments = listOf("Investments", "Inwestycje")
val greeting = listOf("Welcome\n\t\t\t\t to finance", "Witaj\n\t\t\t\t w finansach")

val previousScreenIconDescription = listOf("return", "wróc")
val polishImageDesc = listOf("polish language", "język polski")
val englishImageDesc = listOf("english language", "język angielski")
val addIconDesc = listOf("Add activity", "Dodaj akcję")
val sort = listOf("Sort by: ", "Sortuj po: ")
val dollarIconDesc = listOf("Amount", "Kwota")
val calendarIconDesc = listOf("Date", "Data")
val addSpendingDialogTitle = listOf("Add spending", "Dodaj wydatek")
val addIncomeDialogTitle = listOf("Add income", "Dodaj dochód")
val addInvestmentDialogTitle = listOf("Add investment", "Dodaj inwestycję")
val saveButton = listOf("Save", "Zapisz")
val cancelButton = listOf("Cancel", "Anuluj")

val dayPlaceHolder = listOf("Day", "Dzień")
val monthPlaceHolder = listOf("Month", "Miesiąc")
val yearPlaceHolder = listOf("Year", "Rok")
val amountPlaceHolder = listOf("Amount", "Kwota")
val titlePlaceHolder = listOf("Title", "Tytuł")
val sourcePlaceHolder = listOf("Source", "Źródło")

val calendar = Calendar.getInstance()
val currentYear = calendar.get(Calendar.YEAR)
val currentMonth = calendar.get(Calendar.MONTH) + 1
val currentDay = calendar.get(Calendar.DAY_OF_MONTH)
//val currentDate = Date(year, month, day)
