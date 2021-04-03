package xyz.qwexte.plantscare.features.notifications

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import xyz.qwexte.plantscare.R
import xyz.qwexte.plantscare.features.notifications.list.presentation.NotificationListFragment

class NotificationsHostFragment : Fragment(R.layout.fragment_host_notifications) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (childFragmentManager.findFragmentById(R.id.notification_host_container) == null) {
            childFragmentManager.beginTransaction()
                .add(R.id.notification_host_container, NotificationListFragment())
                .commit()
        }
    }
}
