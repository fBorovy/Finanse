package pl.fboro.finanse.database

sealed interface ActivityEvent{

    object SaveActivity: ActivityEvent
    data class SetDay(val day: Int): ActivityEvent
    data class SetMonth(val month: Int): ActivityEvent
    data class SetYear(val year: Int): ActivityEvent
    data class SetAmount(val amount: Double): ActivityEvent
    data class SetTitle(val title: String): ActivityEvent
    data class SetSource(val source: Char): ActivityEvent

    data class SetType(val type: Int): ActivityEvent

    object ShowAddingDialog: ActivityEvent
    object HideAddingDialog: ActivityEvent

    object ShowEditDeleteDialog: ActivityEvent
    object HideEditDeleteDialog: ActivityEvent

    data class SortActivities(val sortType: SortType): ActivityEvent
    data class DeleteActivity(val activity: Activity): ActivityEvent

    object SaveInvestment: ActivityEvent
    data class SetIDay(val iDay: Int): ActivityEvent
    data class SetIMonth(val iMonth: Int): ActivityEvent
    data class SetIYear(val iYear: Int): ActivityEvent
    data class SetInstrument(val instrument: String): ActivityEvent
    data class SetInvestedIn(val investedIn: Double): ActivityEvent
    data class SetTakenOut(val takenOut: Double): ActivityEvent
    data class SetDifference(val difference: Double): ActivityEvent

    object ShowAddingInvestmentDialog: ActivityEvent
    object ShowEditDeleteInvestmentDialog: ActivityEvent

    object HideAddingInvestmentDialog: ActivityEvent
    object HideEditDeleteInvestmentDialog: ActivityEvent

    data class SortInvestments(val investmentSortType: InvestmentSortType): ActivityEvent
    data class DeleteInvestment(val investment: Investment): ActivityEvent
}