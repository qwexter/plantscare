package xyz.qwexte.plantscare.features.notifications.list.presentation.models

import androidx.annotation.ColorRes
import androidx.annotation.StringRes

data class TutorialStep(
    @StringRes val text: Int,
    @StringRes val highlightText: Int,
    @ColorRes val highlightColor: Int
)
