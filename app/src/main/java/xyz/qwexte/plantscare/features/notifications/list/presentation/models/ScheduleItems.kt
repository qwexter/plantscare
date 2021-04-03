package xyz.qwexte.plantscare.features.notifications.list.presentation.models

import androidx.annotation.ColorRes
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import xyz.qwexte.plantscare.R

sealed class ScheduleItems {

    abstract val resId: Int
        @LayoutRes get


    data class Dates(
        val nearestDates: List<DateFilter>,
        override val resId: Int = R.layout.item_schedule_dates
    ) : ScheduleItems()

    data class TutorialStep(
        @StringRes val text: Int,
        @StringRes val highlightText: Int,
        @ColorRes val highlightColor: Int,
        override val resId: Int = R.layout.item_tutorial_step
    ) : ScheduleItems()

}
