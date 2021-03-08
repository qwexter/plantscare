package xyz.qwexte.plantscare.features.notifications.list.presentation

import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import xyz.qwexte.plantscare.R
import xyz.qwexte.plantscare.features.notifications.list.presentation.models.TutorialStep

class TutorialStepAdapter : RecyclerView.Adapter<TutorialStepAdapter.ViewHolder>() {

    private val items = mutableListOf<TutorialStep>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_tutorial_step, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    fun submit(steps: List<TutorialStep>) {
        items.clear()
        items.addAll(steps)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textView: TextView = itemView.findViewById(R.id.item_tutorial_text)
        fun bind(tutorialStep: TutorialStep) {
            val resources = itemView.resources
            val text = resources.getString(tutorialStep.text)
            val what = resources.getString(tutorialStep.highlightText)
            val start = text.indexOf(what)
            val end = start + what.length
            val spannedText = SpannableString(text)
            spannedText.setSpan(
                ForegroundColorSpan(
                    ContextCompat.getColor(
                        itemView.context,
                        tutorialStep.highlightColor
                    )
                ),
                start,
                end,
                Spannable.SPAN_INCLUSIVE_EXCLUSIVE
            )
            textView.text = spannedText
        }
    }
}
