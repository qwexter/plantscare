package xyz.qwexte.plantscare.common

import androidx.annotation.MenuRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LifecycleObserver
import xyz.qwexte.plantscare.R

class BottomNavigationController(
    private val fragmentManager: FragmentManager,
    private val hostsItems: List<HostNavigationItems>
) : LifecycleObserver {

    fun onSelectTab(@MenuRes menuResId: Int) {
        val hostItem = hostsItems.first { it.menuId == menuResId }
        val visibleFragment: Fragment? = fragmentManager.fragments.firstOrNull { it.isVisible }
        val existTabFragment: Fragment? = fragmentManager.findFragmentByTag(hostItem.hostTag)
        if (visibleFragment != null && existTabFragment != null && visibleFragment === existTabFragment) {
            return
        }
        fragmentManager.beginTransaction()
            .let {
                if (existTabFragment == null) {
                    val newFragment = hostItem.buildHostFragment()
                    it.add(
                        R.id.main_fragment_container,
                        newFragment,
                        hostItem.hostTag
                    )
                    it.setPrimaryNavigationFragment(newFragment)
                }
                if (visibleFragment != null) {
                    it.detach(visibleFragment)
                }
                if (existTabFragment != null) {
                    it.attach(existTabFragment)
                    it.setPrimaryNavigationFragment(existTabFragment)
                }
                if (fragmentManager.findFragmentById(R.id.main_fragment_container) != null && !it.isEmpty) {
                    it.addToBackStack(hostItem.hostTag)
                }
                it.commit()
            }
    }

    fun findTopItemSelection(): Int {
        return hostsItems.firstOrNull {
            it.hostTag == fragmentManager.fragments.lastOrNull()?.tag
        }?.menuId ?: getDefaultTabResId()
    }

    fun getDefaultTabResId(): Int = R.id.bottom_notification_host
}
