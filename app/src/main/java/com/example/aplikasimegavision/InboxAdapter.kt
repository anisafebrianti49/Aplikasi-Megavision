package com.example.aplikasimegavision

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView

class InboxAdapter(private val itemList: List<InboxItem>) :
    RecyclerView.Adapter<InboxAdapter.InboxViewHolder>() {

    class InboxViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvTitle: TextView = view.findViewById(R.id.tvTitle)
        val tvDate: TextView = view.findViewById(R.id.tvDate)
        val tvDescription: TextView = view.findViewById(R.id.tvDescription)
        val viewRedDot: CardView = view.findViewById(R.id.viewRedDot)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InboxViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_inbox, parent, false)
        return InboxViewHolder(view)
    }

    override fun onBindViewHolder(holder: InboxViewHolder, position: Int) {
        val item = itemList[position]
        holder.tvTitle.text = item.title
        holder.tvDate.text = item.date
        holder.tvDescription.text = item.description

        if (item.isUnread) {
            holder.viewRedDot.visibility = View.VISIBLE
        } else {
            holder.viewRedDot.visibility = View.GONE
        }
        holder.itemView.setOnClickListener { view ->
            val activity = view.context as FragmentActivity
            val bundle = Bundle().apply {
                putString("TITLE", item.title)
                putString("DATE", item.date)
                putString("DESC", item.description)
            }

            val detailFragment = DetailInboxFragment().apply {
                arguments = bundle
            }

            activity.supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, detailFragment)
                .addToBackStack(null)
                .commit()
        }
    }

    override fun getItemCount(): Int = itemList.size
}