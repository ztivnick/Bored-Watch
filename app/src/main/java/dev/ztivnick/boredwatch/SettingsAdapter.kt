package dev.ztivnick.boredwatch

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
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
        val sharedPreferences = context.getSharedPreferences("WatchPrefs", Activity.MODE_PRIVATE)
        val prefNumKey: String = when (position) {
            0 -> "TYPE"
            1 -> "PARTICIPANTS"
            2 -> "MAX_PRICE"
            3 -> "MAX_ACCESSIBILITY"
            else -> ""
        }
        val prefToggleKey: String = prefNumKey + "_TOGGLE"

        val prefNumValue = sharedPreferences.getInt(prefNumKey, 0)
        val prefToggleValue = sharedPreferences.getBoolean(prefToggleKey, false)

        // Set initial values
        holder.settingsItemText.text = dataProvider.text
        holder.numPickerNum.text = prefNumValue.toString()
        holder.settingsItemSwitch.isChecked = prefToggleValue
        holder.settingsItemNumPicker.isVisible = prefToggleValue

        holder.settingsItem.setOnClickListener {
            callback?.onItemClicked(holder.bindingAdapterPosition)
        }

        holder.settingsItemSwitch.setOnClickListener {
            val isChecked = holder.settingsItemSwitch.isChecked
            holder.settingsItemNumPicker.isVisible = isChecked
            sharedPreferences.edit().putBoolean(prefToggleKey, isChecked).apply()
        }

        holder.numPickerDecrease.setOnClickListener {
            sharedPreferences.edit().putInt(prefNumKey, setNum(false, holder.numPickerNum)).apply()
        }
        holder.numPickerIncrease.setOnClickListener {
            sharedPreferences.edit().putInt(prefNumKey, setNum(true, holder.numPickerNum)).apply()
        }
    }

    private fun setNum(increase: Boolean, num: TextView): Int {
        val currentNum = num.text.toString().toInt()
        var newNum = 0
        if ((currentNum >= 0 && increase) || currentNum > 0) newNum =
            currentNum + (if (increase) 1 else -1)
        num.text = newNum.toString()
        return newNum
    }

    override fun getItemCount(): Int {
        return dataSource.size
    }

    class RecyclerViewHolder(view: View) : ViewHolder(view) {
        var settingsItem: RelativeLayout
        var settingsItemText: TextView
        var settingsItemSwitch: Switch
        var settingsItemNumPicker: View
        var numPickerDecrease: Button
        var numPickerIncrease: Button
        var numPickerNum: TextView

        init {
            settingsItem = view.findViewById(R.id.settings_item)
            settingsItemText = view.findViewById(R.id.settings_item_text)
            settingsItemSwitch = view.findViewById(R.id.settings_item_switch)
            settingsItemNumPicker = view.findViewById(R.id.settings_item_num_picker)
            numPickerDecrease = settingsItemNumPicker.findViewById(R.id.num_picker_decrease)
            numPickerIncrease = settingsItemNumPicker.findViewById(R.id.num_picker_increase)
            numPickerNum = settingsItemNumPicker.findViewById(R.id.num_picker_number)
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