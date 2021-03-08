package xyz.qwexte.plantscare.features.notifications.list.data.impl

import xyz.qwexte.plantscare.features.notifications.list.data.GetNotificationsUseCase

class GetNotificationsUseCaseImpl : GetNotificationsUseCase {
    override suspend fun get(date: Any): List<Any> = emptyList()
}
