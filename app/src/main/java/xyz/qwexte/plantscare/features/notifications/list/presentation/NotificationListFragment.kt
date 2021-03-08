package xyz.qwexte.plantscare.features.notifications.list.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.AppBarLayout
import kotlinx.coroutines.flow.collect
import xyz.qwexte.plantscare.R

class NotificationListFragment : Fragment(R.layout.fragment_notification_list) {

    private var recyclerView: RecyclerView? = null
    private var appBarView: AppBarLayout? = null
    private var emptyView: View? = null

    private var adapter: ListAdapter<String, RecyclerView.ViewHolder>? = null

    private val tutorialAdapter by lazyOf(TutorialStepAdapter())

    private val viewModel by viewModels<NotificationListViewModel> {
        NotificationListViewModelFactory()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        findViews(view)
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.observeState().collect { bindScreenState(it) }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        clearViews()
    }

    private fun bindScreenState(state: NotificationListViewModel.NotificationsState) {
        when (state) {
            NotificationListViewModel.NotificationsState.Empty -> {
                emptyView?.visibility = View.VISIBLE
                recyclerView?.visibility = View.GONE
            }
            is NotificationListViewModel.NotificationsState.Items -> {
                emptyView?.visibility = View.GONE
                recyclerView?.visibility = View.VISIBLE
            }
            is NotificationListViewModel.NotificationsState.Tutorial -> {
                emptyView?.visibility = View.GONE
                recyclerView?.adapter = tutorialAdapter
                tutorialAdapter.submit(state.items)
                recyclerView?.visibility = View.VISIBLE

            }
        }
    }

    private fun findViews(view: View) {
        recyclerView = view.findViewById(R.id.fragment_notification_list_recycler)
        appBarView = view.findViewById(R.id.fragment_notification_list_app_bar)
        emptyView = view.findViewById(R.id.fragment_notification_empty_placeholder)
    }

    private fun clearViews() {
        recyclerView?.adapter = null
        adapter = null
        recyclerView = null
        appBarView = null
    }

}
