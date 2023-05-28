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
val DeleteSpendingDialogTitle = listOf("Edit spending", "Edytuj wydatek")
val addIncomeDialogTitle = listOf("Add income", "Dodaj dochód")
val DeleteIncomeDialogTitle = listOf("Edit income", "Edytuj dochód")
val addInvestmentDialogTitle = listOf("Add investment", "Dodaj inwestycję")
val saveButton = listOf("Save", "Zapisz")
val cancelButton = listOf("Cancel", "Anuluj")
val deleteButton = listOf("Delete", "Usuń")

val dayPlaceHolder = listOf("Day", "Dzień")
val monthPlaceHolder = listOf("Month", "Miesiąc")
val yearPlaceHolder = listOf("Year", "Rok")
val amountPlaceHolder = listOf("Amount", "Kwota")
val titlePlaceHolder = listOf("Title", "Tytuł")
val instrumentPlaceHolder = listOf("Instrument", "Instrument")
val amountInPlaceHolder = listOf("Invested amount", "Zainwestowana kwota")
val amountOutPlaceHolder = listOf("Result amount", "Zwrot z inwestycji")
val investmentResult = listOf("Result", "Wynik")
val paidFor = listOf("I've paid for", "zapłaciłem za")
val sold = listOf("I got", "wyciągnąłem")
val monthTotal = listOf("Month total: ", "Razem w miesiącu: ")

val calendar: Calendar = Calendar.getInstance()
val currentYear = calendar.get(Calendar.YEAR)
val currentMonth = calendar.get(Calendar.MONTH) + 1
val currentDay = calendar.get(Calendar.DAY_OF_MONTH)