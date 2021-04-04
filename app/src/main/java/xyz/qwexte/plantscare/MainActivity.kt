package xyz.qwexte.plantscare

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.google.android.material.bottomnavigation.BottomNavigationView
import xyz.qwexte.plantscare.common.BottomNavigationController
import xyz.qwexte.plantscare.common.FeatureFlags
import xyz.qwexte.plantscare.common.HostNavigationItems

class MainActivity : AppCompatActivity() {

    private var navigationView: BottomNavigationView? = null
    private var bottomNavigationController: BottomNavigationController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupBottomNavigation()
        if (supportFragmentManager.findFragmentById(R.id.main_fragment_container) == null) {
            bottomNavigationController?.let {
                navigationView?.selectedItemId = it.getDefaultTabResId()
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        bottomNavigationController?.let {
            navigationView?.selectedItemId = it.findTopItemSelection()
        }
    }

    override fun onDestroy() {
        bottomNavigationController = null
        navigationView?.setOnNavigationItemSelectedListener(null)
        navigationView = null
        super.onDestroy()
    }

    private fun setupBottomNavigation() {
        bottomNavigationController = BottomNavigationController(
            supportFragmentManager,
            listOf(
                HostNavigationItems.Notifications(
                    R.id.bottom_notification_host,
                    "TAG_NOTIFICATION_HOST"
                ),
                HostNavigationItems.Plants(
                    R.id.bottom_plants_host,
                    "TAG_PLANTS_HOST"
                ),
                HostNavigationItems.Settings(
                    R.id.bottom_settings_host,
                    "TAG_SETTINGS_HOST"
                )
            )
        )
        navigationView = findViewById(R.id.main_navigation_bar)
        navigationView?.setOnNavigationItemSelectedListener {
            bottomNavigationController?.onSelectTab(it.itemId)
            true
        }
        navigationView?.isVisible = FeatureFlags.BOTTOM_NAVIGATION
    }

}

