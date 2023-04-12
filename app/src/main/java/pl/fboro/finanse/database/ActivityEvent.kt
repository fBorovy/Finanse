package pl.fboro.finanse.database

sealed interface ActivityEvent{

    object SaveActivity: ActivityEvent
    data class setDay(val day: Int): ActivityEvent
    data class setMonth(val month: Int): ActivityEvent
    data class setYear(val year: Int): ActivityEvent
    data class setAmount(val amount: Double): ActivityEvent
    data class setTitle(val title: String): ActivityEvent
    data class setSource(val source: Char): ActivityEvent

    data class setType(val type: Int): ActivityEvent

    object ShowDialog: ActivityEvent
    object HideDialog: ActivityEvent

    data class SortActivities(val sortType: SortType): ActivityEvent
    data class DeleteActivity(val activity: Activity): ActivityEvent
}