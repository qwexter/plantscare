package xyz.qwexte.plantscare.features.notifications.list.presentation

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineDispatcher
import xyz.qwexte.plantscare.common.coroutines.DispatchersProvider

class TestDispatchersProviderImpl(
    override val io: CoroutineDispatcher,
    override val default: CoroutineDispatcher,
    override val unconfined: CoroutineDispatcher,
    override val main: CoroutineDispatcher
) : DispatchersProvider {
    constructor(dispatcher: TestCoroutineDispatcher) : this(
        dispatcher,
        dispatcher,
        dispatcher,
        dispatcher
    )
}
