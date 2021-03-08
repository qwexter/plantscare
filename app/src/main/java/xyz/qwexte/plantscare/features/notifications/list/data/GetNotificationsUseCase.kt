package xyz.qwexte.plantscare.features.notifications.list.data

interface GetNotificationsUseCase {
    suspend fun get(date: Any): List<Any>
}
