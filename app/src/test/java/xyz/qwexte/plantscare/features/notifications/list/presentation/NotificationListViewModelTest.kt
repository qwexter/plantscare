package xyz.qwexte.plantscare.features.notifications.list.presentation

import app.cash.turbine.test
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Rule
import org.junit.Test
import xyz.qwexte.plantscare.R
import xyz.qwexte.plantscare.commontest.TestDispatchersProviderRule
import xyz.qwexte.plantscare.features.notifications.list.data.GetNotificationsUseCase
import xyz.qwexte.plantscare.features.notifications.list.data.TutorialUseCase
import xyz.qwexte.plantscare.features.notifications.list.presentation.models.NotificationsState
import xyz.qwexte.plantscare.features.notifications.list.presentation.models.TutorialStep
import kotlin.time.ExperimentalTime

@ExperimentalTime
class NotificationListViewModelTest {

    @get:Rule
    private val dispatchersRule = TestDispatchersProviderRule()

    private var viewModel: NotificationListViewModel? = null

    @After
    fun tearDown() {
        viewModel = null
    }

    @Test
    fun `observeState - can show tutorial - tutorials items`() {
        runBlockingTest {
            viewModel = NotificationListViewModel(
                object : GetNotificationsUseCase {
                    override suspend fun get(date: Any): List<Any> = emptyList()
                },
                object : TutorialUseCase {
                    override suspend fun canShow(): Boolean = true
                },
                dispatchersRule.provider
            )
            viewModel?.observeState()
                ?.test {
                    assertEquals(
                        NotificationsState.Tutorial(
                            listOf(
                                TutorialStep(
                                    R.string.tutorial_first_text,
                                    R.string.tutorial_first_highlight_text,
                                    R.color.purple_200
                                ),
                                TutorialStep(
                                    R.string.tutorial_second_text,
                                    R.string.tutorial_second_highlight_text,
                                    R.color.purple_200
                                ),
                                TutorialStep(
                                    R.string.tutorial_third_text,
                                    R.string.tutorial_third_highlight_text,
                                    R.color.purple_200
                                )
                            )
                        ),
                        expectItem()
                    )
                }
        }
    }

    @Test
    fun `observeState - can't show tutorial, no notification - empty state`() {
        viewModel = NotificationListViewModel(
            object : GetNotificationsUseCase {
                override suspend fun get(date: Any): List<Any> = emptyList()
            },
            object : TutorialUseCase {
                override suspend fun canShow(): Boolean = false
            },
            dispatchersRule.provider
        )
        runBlockingTest {
            viewModel?.observeState()?.test {
                assertEquals(
                    NotificationsState.Empty,
                    expectItem()
                )
            }
        }
    }

    @Test
    fun `observeState - can't show tutorial, has notification - notifications items`() {
        val items = listOf(Any())
        viewModel = NotificationListViewModel(
            object : GetNotificationsUseCase {
                override suspend fun get(date: Any): List<Any> = items
            },
            object : TutorialUseCase {
                override suspend fun canShow(): Boolean = false
            },
            dispatchersRule.provider
        )
        runBlockingTest {
            viewModel?.observeState()?.test {
                assertEquals(
                    NotificationsState.Items(
                        items
                    ),
                    expectItem()
                )
            }
        }
    }

}
