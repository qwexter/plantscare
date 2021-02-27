package xyz.qwexte.plantscare.common

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import xyz.qwexte.plantscare.features.notifications.NotificationsHostFragment
import xyz.qwexte.plantscare.features.plants.PlantsHostFragment
import xyz.qwexte.plantscare.features.settings.SettingsHostFragment

sealed class HostNavigationItems(
    @IdRes val menuId: Int,
    val hostTag: String
) {
    abstract fun buildHostFragment(): Fragment

    class Notifications(
        menuId: Int,
        hostTag: String
    ) : HostNavigationItems(
        menuId,
        hostTag
    ) {
        override fun buildHostFragment(): Fragment {
            return NotificationsHostFragment()
        }
    }

    class Plants(
        menuId: Int,
        hostTag: String
    ) : HostNavigationItems(
        menuId,
        hostTag
    ) {
        override fun buildHostFragment(): Fragment {
            return PlantsHostFragment()
        }
    }

    class Settings(
        menuId: Int,
        hostTag: String
    ) : HostNavigationItems(
        menuId,
        hostTag
    ) {
        override fun buildHostFragment(): Fragment {
            return SettingsHostFragment()
        }
    }

}
