package xyz.qwexte.plantscare.features.notifications.list.data.impl

import xyz.qwexte.plantscare.features.notifications.list.data.TutorialUseCase

class TutorialUseCaseImpl : TutorialUseCase {
    override suspend fun canShow(): Boolean = true
}
