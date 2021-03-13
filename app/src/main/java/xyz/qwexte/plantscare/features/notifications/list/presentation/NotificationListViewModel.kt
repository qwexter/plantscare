package xyz.qwexte.plantscare.features.notifications.list.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combineTransform
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import xyz.qwexte.plantscare.R
import xyz.qwexte.plantscare.common.coroutines.DispatchersProvider
import xyz.qwexte.plantscare.features.notifications.list.data.GetNotificationsUseCase
import xyz.qwexte.plantscare.features.notifications.list.data.TutorialUseCase
import xyz.qwexte.plantscare.features.notifications.list.presentation.models.DateFilter
import xyz.qwexte.plantscare.features.notifications.list.presentation.models.NotificationsState
import xyz.qwexte.plantscare.features.notifications.list.presentation.models.TutorialStep
import java.time.LocalDate

class NotificationListViewModel(
    getNotificationsUseCase: GetNotificationsUseCase,
    tutorialUseCase: TutorialUseCase,
    dispatchers: DispatchersProvider
) : ViewModel() {

    private val notifications = MutableStateFlow<NotificationsState>(NotificationsState.Initial)
    private val dates = MutableStateFlow<List<LocalDate>>(
        (0..30).map {
            LocalDate.now().minusDays(15L + it)
        }
    )
    private val selectedDate = MutableStateFlow(LocalDate.now())

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

    fun observeState(): Flow<NotificationsState> = notifications

    fun observeDates(): Flow<List<DateFilter>> = dates.map {
        it.map { DateFilter(it, it == LocalDate.now()) }
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
}
