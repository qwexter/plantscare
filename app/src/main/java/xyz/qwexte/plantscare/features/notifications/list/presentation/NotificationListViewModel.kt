package xyz.qwexte.plantscare.features.notifications.list.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import xyz.qwexte.plantscare.R
import xyz.qwexte.plantscare.common.FeatureFlags
import xyz.qwexte.plantscare.common.coroutines.DispatchersProvider
import xyz.qwexte.plantscare.features.notifications.list.data.GetNotificationsUseCase
import xyz.qwexte.plantscare.features.notifications.list.data.TutorialUseCase
import xyz.qwexte.plantscare.features.notifications.list.presentation.models.DateFilter
import xyz.qwexte.plantscare.features.notifications.list.presentation.models.NotificationsState
import xyz.qwexte.plantscare.features.notifications.list.presentation.models.ScheduleItems
import java.time.LocalDate

class NotificationListViewModel(
    getNotificationsUseCase: GetNotificationsUseCase,
    tutorialUseCase: TutorialUseCase,
    dispatchers: DispatchersProvider
) : ViewModel() {

    val events get() = _notifications

    private val _notifications = MutableStateFlow<NotificationsState>(NotificationsState.Initial)
    private val dates = MutableStateFlow<List<LocalDate>>(
        (0..30).map {
            LocalDate.now().minusDays(15L + it)
        }
    )

    init {
        if (FeatureFlags.TUTORIAL) {
            setupStateWithTutorial(dispatchers, tutorialUseCase, getNotificationsUseCase)
        } else {
            setupJustEvents(dispatchers, getNotificationsUseCase)
        }
    }

    private fun setupJustEvents(
        dispatchers: DispatchersProvider,
        notificationsUseCase: GetNotificationsUseCase
    ) {
        viewModelScope.launch(dispatchers.default) {
            val items = notificationsUseCase.get(Any())
            if (items.isEmpty()) {
                _notifications.value = NotificationsState.Empty
            } else {
                _notifications.value = NotificationsState.Items(items)
            }
        }
    }

    private fun setupStateWithTutorial(
        dispatchers: DispatchersProvider,
        tutorialUseCase: TutorialUseCase,
        getNotificationsUseCase: GetNotificationsUseCase
    ) {
        viewModelScope.launch(dispatchers.io) {
            if (_notifications.value == NotificationsState.Initial) {
                if (tutorialUseCase.canShow()) {
                    _notifications.value = createTutorial()
                } else {
                    val items = getNotificationsUseCase.get(Any())
                    if (items.isEmpty()) {
                        _notifications.value = NotificationsState.Empty
                    } else {
                        _notifications.value =
                            NotificationsState.Items(items)
                    }
                }
            }
        }
    }

    fun observeState(): Flow<NotificationsState> = dates.flatMapLatest { dateList ->
        _notifications.map { state ->
            if (state is NotificationsState.Items) {
                state.copy(
                    listOf(
                        ScheduleItems.Dates(
                            dateList.map {
                                DateFilter(it, it == LocalDate.now())
                            }
                        )
                    ) + state.items
                )
            } else {
                state
            }
        }
    }

    private fun createTutorial(): NotificationsState.Items {
        return NotificationsState.Items(
            listOf(
                ScheduleItems.TutorialStep(
                    R.string.tutorial_first_text,
                    R.string.tutorial_first_highlight_text,
                    R.color.purple_200,
                ),
                ScheduleItems.TutorialStep(
                    R.string.tutorial_second_text,
                    R.string.tutorial_second_highlight_text,
                    R.color.purple_200,
                ),
                ScheduleItems.TutorialStep(
                    R.string.tutorial_third_text,
                    R.string.tutorial_third_highlight_text,
                    R.color.purple_200,
                )
            )
        )
    }
}
