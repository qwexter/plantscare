package xyz.qwexte.plantscare.features.notifications.list.presentation.models

sealed class NotificationsState {
    data class Items(val items: List<ScheduleItems>) : NotificationsState()
    object Empty : NotificationsState()
    object Initial : NotificationsState()
}
