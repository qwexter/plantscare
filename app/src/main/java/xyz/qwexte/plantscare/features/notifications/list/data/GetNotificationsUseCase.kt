package xyz.qwexte.plantscare.features.notifications.list.data

import xyz.qwexte.plantscare.features.notifications.list.presentation.models.ScheduleItems

interface GetNotificationsUseCase {
    suspend fun get(date: Any): List<ScheduleItems>
}
