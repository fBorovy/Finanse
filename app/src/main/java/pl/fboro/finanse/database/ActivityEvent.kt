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
}