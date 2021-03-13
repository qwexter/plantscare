package xyz.qwexte.plantscare.features.notifications.list.presentation.models

sealed class NotificationsState {
    data class Tutorial(val items: List<TutorialStep>) : NotificationsState()
    data class Items(val items: List<Any>) : NotificationsState()
    object Empty : NotificationsState()
    object Initial : NotificationsState()
}
