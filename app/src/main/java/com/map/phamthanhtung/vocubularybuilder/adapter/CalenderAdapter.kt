package com.map.phamthanhtung.vocubularybuilder.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.map.phamthanhtung.vocubularybuilder.R
import com.map.phamthanhtung.vocubularybuilder.data.Event

class CalendarAdapter(
    private var events: MutableList<Event>,
    private val onFavoriteClicked: (Event) -> Unit,
    private val onDeleteClicked: (Event) -> Unit,
    private val onAddClicked: () -> Unit // Hàm không tham số cho nút "Add"
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val ITEM_TYPE_EVENT = 0
        private const val ITEM_TYPE_ADD_BUTTON = 1
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == events.size) ITEM_TYPE_ADD_BUTTON else ITEM_TYPE_EVENT
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == ITEM_TYPE_ADD_BUTTON) {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_add_button, parent, false)
            AddButtonViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_calendar_event, parent, false)
            EventViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is EventViewHolder) {
            val event = events[position]
            holder.descriptionTextView.text = event.description

            holder.favoriteIcon.setImageResource(
                if (event.isFavorite) R.drawable.ic_favorite else R.drawable.ic_favorite_border
            )

            holder.favoriteIcon.setOnClickListener {
                onFavoriteClicked(event)
            }

            holder.deleteIcon.setOnClickListener {
                onDeleteClicked(event)
            }
        } else if (holder is AddButtonViewHolder) {
            holder.addButton.setOnClickListener {
                onAddClicked()
            }
        }
    }

    override fun getItemCount(): Int {
        return events.size + 1
    }

    fun addEvent(event: Event) {
        events.add(event)
        notifyItemInserted(events.size - 1)
    }

    fun removeEvent(event: Event) {
        val position = events.indexOf(event)
        if (position >= 0) {
            events.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    fun submitList(newList: List<Event>) {
        events.clear()
        events.addAll(newList)
        notifyDataSetChanged()
    }

    fun toggleFavorite(event: Event) {
        val position = events.indexOf(event)
        if (position >= 0) {
            events[position].isFavorite = !events[position].isFavorite
            notifyItemChanged(position)
        }
    }

    class EventViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val descriptionTextView: TextView = itemView.findViewById(R.id.event_description)
        val favoriteIcon: ImageView = itemView.findViewById(R.id.favorite_icon)
        val deleteIcon: ImageView = itemView.findViewById(R.id.delete_icon)
    }

    class AddButtonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val addButton: Button = itemView.findViewById(R.id.add_event_button)
    }
}
