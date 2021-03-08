package xyz.qwexte.plantscare.features.notifications.list.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import xyz.qwexte.plantscare.common.coroutines.DispatchersProvider
import xyz.qwexte.plantscare.features.notifications.list.data.impl.GetNotificationsUseCaseImpl
import xyz.qwexte.plantscare.features.notifications.list.data.impl.TutorialUseCaseImpl

class NotificationListViewModelFactory : NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if (NotificationListViewModel::class.java.isAssignableFrom(modelClass)) {
            return NotificationListViewModel(
                GetNotificationsUseCaseImpl(),
                TutorialUseCaseImpl(),
                object : DispatchersProvider {
                    override val io: CoroutineDispatcher = Dispatchers.IO
                    override val default: CoroutineDispatcher = Dispatchers.Default
                    override val unconfined: CoroutineDispatcher = Dispatchers.Unconfined
                    override val main: CoroutineDispatcher = Dispatchers.Main
                }
            ) as T
        }

        return super.create(modelClass)
    }
}
