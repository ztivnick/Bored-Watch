package dev.ztivnick.boredwatch

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.SeekBar
import android.widget.Switch
import android.widget.TextView
import androidx.core.view.isVisible
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
        fun onItemClicked(settingsMenuPosition: Int?)
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
        holder.settingsItemText.text = dataProvider.text
        holder.settingsItem.setOnClickListener {
            callback?.onItemClicked(holder.bindingAdapterPosition)
        }
        holder.settingsItemSwitch.setOnClickListener {
            holder.settingsItemSeekBar.isVisible = holder.settingsItemSwitch.isChecked
        }
    }

    override fun getItemCount(): Int {
        return dataSource.size
    }

    class RecyclerViewHolder(view: View) : ViewHolder(view) {
        var settingsItem: RelativeLayout
        var settingsItemText: TextView
        var settingsItemSeekBar: SeekBar
        var settingsItemSwitch: Switch

        init {
            settingsItem = view.findViewById(R.id.settings_item)
            settingsItemText = view.findViewById(R.id.settings_item_text)
            settingsItemSeekBar = view.findViewById(R.id.settings_item_seekbar)
            settingsItemSwitch = view.findViewById(R.id.settings_item_switch)
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
class SettingsItem(val text: String)