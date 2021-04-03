package xyz.qwexte.plantscare.features.notifications.list.data.impl

import xyz.qwexte.plantscare.features.notifications.list.data.GetNotificationsUseCase
import xyz.qwexte.plantscare.features.notifications.list.presentation.models.ScheduleItems

class GetNotificationsUseCaseImpl : GetNotificationsUseCase {
    override suspend fun get(date: Any): List<ScheduleItems> = emptyList()
}
