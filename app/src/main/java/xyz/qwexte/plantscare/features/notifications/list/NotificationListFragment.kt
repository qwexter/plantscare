package xyz.qwexte.plantscare.features.notifications.list

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.AppBarLayout
import xyz.qwexte.plantscare.R

class NotificationListFragment : Fragment(R.layout.fragment_notification_list) {

    private var recyclerView: RecyclerView? = null
    private var appBarView: AppBarLayout? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.fragment_notification_list_recycler)
        appBarView = view.findViewById(R.id.fragment_notification_list_app_bar)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        recyclerView = null
        appBarView = null
    }
}
