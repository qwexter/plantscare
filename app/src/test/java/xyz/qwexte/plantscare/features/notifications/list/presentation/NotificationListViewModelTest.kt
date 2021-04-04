package xyz.qwexte.plantscare.features.notifications.list.presentation

import org.junit.Before
import org.junit.Rule
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import xyz.qwexte.plantscare.commontest.TestDispatchersProviderRule
import xyz.qwexte.plantscare.features.notifications.list.data.GetNotificationsUseCase
import xyz.qwexte.plantscare.features.notifications.list.data.TutorialUseCase
import kotlin.time.ExperimentalTime

@ExperimentalTime
class NotificationListViewModelTest {

    @get:Rule
    val dispatchersRule = TestDispatchersProviderRule()

    @Mock
    lateinit var notificationsUseCase: GetNotificationsUseCase

    @Mock
    lateinit var tutorialsUseCase: TutorialUseCase


    private lateinit var viewModel: NotificationListViewModel

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        viewModel = NotificationListViewModel(
            notificationsUseCase,
            tutorialsUseCase,
            dispatchersRule.provider
        )
    }

}
