package xyz.qwexte.plantscare.features.notifications.list.presentation.models

import java.time.LocalDate

data class DateFilter(
    val date: LocalDate,
    val selected: Boolean
)
