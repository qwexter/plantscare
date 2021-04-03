package xyz.qwexte.plantscare.features.notifications.list.presentation

import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import xyz.qwexte.plantscare.R
import xyz.qwexte.plantscare.features.notifications.list.presentation.models.DateFiltersAdapter
import xyz.qwexte.plantscare.features.notifications.list.presentation.models.ScheduleItems

class ScheduleAdapter :
    ListAdapter<ScheduleItems, ScheduleAdapter.ViewHolder<ScheduleItems>>(
        diffUtil
    ) {

    override fun getItemViewType(position: Int): Int {
        return getItem(position).resId
    }

    @Suppress("UNCHECKED_CAST")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder<ScheduleItems> {
        val itemView = LayoutInflater.from(parent.context).inflate(viewType, parent, false)
        val holder = when (viewType) {
            R.layout.item_schedule_dates -> DatesViewHolder(itemView)
            R.layout.item_tutorial_step -> TutorialViewHolder(itemView)
            else -> throw IllegalStateException("Can't create view holder for view type: $viewType")
        }
        return holder as ViewHolder<ScheduleItems>
    }

    override fun onBindViewHolder(holder: ViewHolder<ScheduleItems>, position: Int) {
        holder.bind(getModel(position))
    }

    private inline fun <reified T> getModel(position: Int): T {
        return getItem(position) as T
    }

    class TutorialViewHolder(itemView: View) : ViewHolder<ScheduleItems.TutorialStep>(itemView) {
        private val textView: TextView = itemView.findViewById(R.id.item_tutorial_text)
        override fun bind(model: ScheduleItems.TutorialStep) {
            val resources = itemView.resources
            val text = resources.getString(model.text)
            val what = resources.getString(model.highlightText)
            val start = text.indexOf(what)
            val end = start + what.length
            val spannedText = SpannableString(text)
            spannedText.setSpan(
                ForegroundColorSpan(
                    ContextCompat.getColor(
                        itemView.context,
                        model.highlightColor
                    )
                ),
                start,
                end,
                Spannable.SPAN_INCLUSIVE_EXCLUSIVE
            )
            textView.text = spannedText
        }
    }

    class DatesViewHolder(itemView: View) : ViewHolder<ScheduleItems.Dates>(itemView) {

        private val recyclerView = itemView
            .findViewById<RecyclerView>(R.id.item_schedule_dates_recycler)
            .apply {
                adapter = DateFiltersAdapter()
            }

        override fun bind(model: ScheduleItems.Dates) {
            (recyclerView.adapter as DateFiltersAdapter).submitList(model.nearestDates)
        }

    }


    abstract class ViewHolder<T : ScheduleItems>(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        abstract fun bind(model: T)
    }

    companion object {
        private val diffUtil = object : DiffUtil.ItemCallback<ScheduleItems>() {
            override fun areItemsTheSame(
                oldItem: ScheduleItems,
                newItem: ScheduleItems
            ): Boolean {
                return oldItem.resId == newItem.resId
            }

            override fun areContentsTheSame(
                oldItem: ScheduleItems,
                newItem: ScheduleItems
            ): Boolean {
                return oldItem == newItem
            }

        }
    }

}
