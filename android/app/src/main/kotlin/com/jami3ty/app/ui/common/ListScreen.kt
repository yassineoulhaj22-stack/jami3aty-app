package com.jami3ty.app.ui.common

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jami3ty.app.R

data class Row(val title: String, val subtitle: String)

/** Shared list layout (fragment_list) with a Lottie empty state. */
class ListScreen(root: View, emptyText: Int) {
    private val recycler: RecyclerView = root.findViewById(R.id.recycler)
    private val emptyContainer: View = root.findViewById(R.id.emptyContainer)
    private val adapter = RowAdapter()

    init {
        root.findViewById<TextView>(R.id.emptyText).setText(emptyText)
        recycler.layoutManager = LinearLayoutManager(root.context)
        recycler.adapter = adapter
    }

    fun show(rows: List<Row>) {
        adapter.submit(rows)
        val empty = rows.isEmpty()
        recycler.visibility = if (empty) View.GONE else View.VISIBLE
        emptyContainer.visibility = if (empty) View.VISIBLE else View.GONE
    }
}

class RowAdapter : RecyclerView.Adapter<RowAdapter.VH>() {
    private var items: List<Row> = emptyList()

    class VH(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.titleText)
        val subtitle: TextView = view.findViewById(R.id.subtitleText)
    }

    fun submit(rows: List<Row>) {
        items = rows
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH =
        VH(LayoutInflater.from(parent.context).inflate(R.layout.item_row, parent, false))

    override fun onBindViewHolder(holder: VH, position: Int) {
        val row = items[position]
        holder.title.text = row.title
        holder.subtitle.text = row.subtitle
    }

    override fun getItemCount(): Int = items.size
}

fun formatDate(context: Context, millis: Long): String =
    android.text.format.DateFormat.getMediumDateFormat(context)
        .format(java.util.Date(millis))
