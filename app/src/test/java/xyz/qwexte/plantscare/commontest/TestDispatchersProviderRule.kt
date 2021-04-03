package xyz.qwexte.plantscare.commontest

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import xyz.qwexte.plantscare.common.coroutines.DispatchersProvider
import xyz.qwexte.plantscare.features.notifications.list.presentation.TestDispatchersProviderImpl

@ExperimentalCoroutinesApi
class TestDispatchersProviderRule(
    val dispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher()
) : TestWatcher() {

    val provider: DispatchersProvider = TestDispatchersProviderImpl(dispatcher)

    override fun starting(description: Description?) {
        super.starting(description)
        Dispatchers.setMain(dispatcher)
    }

    override fun finished(description: Description?) {
        super.finished(description)
        Dispatchers.resetMain()
    }
}
