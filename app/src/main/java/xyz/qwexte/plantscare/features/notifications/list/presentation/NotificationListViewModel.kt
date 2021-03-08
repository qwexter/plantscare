package xyz.qwexte.plantscare.features.notifications.list.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import xyz.qwexte.plantscare.R
import xyz.qwexte.plantscare.common.coroutines.DispatchersProvider
import xyz.qwexte.plantscare.features.notifications.list.data.GetNotificationsUseCase
import xyz.qwexte.plantscare.features.notifications.list.data.TutorialUseCase
import xyz.qwexte.plantscare.features.notifications.list.presentation.models.TutorialStep

class NotificationListViewModel(
    getNotificationsUseCase: GetNotificationsUseCase,
    tutorialUseCase: TutorialUseCase,
    dispatchers: DispatchersProvider
) : ViewModel() {

    private val notifications = MutableStateFlow<NotificationsState>(NotificationsState.Initial)

    init {
        viewModelScope.launch(dispatchers.io) {
            if (notifications.value == NotificationsState.Initial) {
                if (tutorialUseCase.canShow()) {
                    notifications.value = createTutorial()
                } else {
                    val items = getNotificationsUseCase.get(Any())
                    if (items.isEmpty()) {
                        notifications.value = NotificationsState.Empty
                    } else {
                        notifications.value =
                            NotificationsState.Items(items)
                    }
                }
            }
        }
    }

    private fun createTutorial(): NotificationsState.Tutorial {
        return NotificationsState.Tutorial(
            listOf(
                TutorialStep(
                    R.string.tutorial_first_text,
                    R.string.tutorial_first_highlight_text,
                    R.color.purple_200,
                ),
                TutorialStep(
                    R.string.tutorial_second_text,
                    R.string.tutorial_second_highlight_text,
                    R.color.purple_200,
                ),
                TutorialStep(
                    R.string.tutorial_third_text,
                    R.string.tutorial_third_highlight_text,
                    R.color.purple_200,
                )
            )
        )
    }

    fun observeState(): Flow<NotificationsState> = notifications

    sealed class NotificationsState {
        data class Tutorial(val items: List<TutorialStep>) : NotificationsState()
        data class Items(val items: List<Any>) : NotificationsState()
        object Empty : NotificationsState()
        object Initial : NotificationsState()
    }

}
