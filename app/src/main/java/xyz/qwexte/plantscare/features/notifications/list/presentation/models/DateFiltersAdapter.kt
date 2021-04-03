package xyz.qwexte.plantscare.features.notifications.list.presentation.models

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import xyz.qwexte.plantscare.R
import java.time.format.DateTimeFormatter

class DateFiltersAdapter : ListAdapter<DateFilter, DateFiltersAdapter.ViewHolder>(
    object : DiffUtil.ItemCallback<DateFilter>() {
        override fun areItemsTheSame(oldItem: DateFilter, newItem: DateFilter): Boolean {
            return oldItem.date == newItem.date
        }

        override fun areContentsTheSame(oldItem: DateFilter, newItem: DateFilter): Boolean {
            return oldItem.selected == newItem.selected
        }
    }
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(
                    R.layout.item_date_filter,
                    parent,
                    false
                )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val button: Button = itemView.findViewById(R.id.date_filter_button)

        fun bind(dateFilter: DateFilter) {
            button.text = dateFilter.date.format(DateTimeFormatter.ISO_LOCAL_DATE)
        }
    }
}
