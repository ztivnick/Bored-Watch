package dev.ztivnick.boredwatch

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder


class SettingsAdapter(
    context: Context,
    dataArgs: ArrayList<SettingsItem>,
    onPressCallback: AdapterCallback?
) :
    RecyclerView.Adapter<SettingsAdapter.RecyclerViewHolder>() {
    private var dataSource = ArrayList<SettingsItem>()

    interface AdapterCallback {
        fun onItemClicked(menuPosition: Int?)
    }

    private val callback: AdapterCallback?
    private val context: Context

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.settings_item, parent, false)
        return RecyclerViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        val dataProvider = dataSource[position]
        holder.menuItem.text = dataProvider.text
        holder.menuContainer.setOnClickListener {
            callback?.onItemClicked(holder.bindingAdapterPosition)
        }
    }

    override fun getItemCount(): Int {
        return dataSource.size
    }

    class RecyclerViewHolder(view: View) : ViewHolder(view) {
        var menuContainer: RelativeLayout
        var menuItem: TextView

        init {
            menuContainer = view.findViewById(R.id.menu_container)
            menuItem = view.findViewById(R.id.settings_item)
        }
    }

    init {
        this.context = context
        dataSource = dataArgs
        this.callback = onPressCallback
    }
}

/**
 * @param text The settings item description
 */
// TODO: Add icon and slider
class SettingsItem(val text: String)