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
    RecyclerView.Adapter<ViewHolder>() {
    private var dataSource = ArrayList<SettingsItem>()

    interface AdapterCallback {
        fun onItemClicked(settingsMenuPosition: Int?)
    }

    private val callback: AdapterCallback?
    private val context: Context

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) 0 else 1
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {

        return if (viewType == 0) RecyclerViewHolderTextPicker(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.settings_item_text, parent, false)
        )
        else RecyclerViewHolderNumPicker(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.settings_item_num, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dataProvider = dataSource[position]
        val sharedPreferences = context.getSharedPreferences("WatchPrefs", Activity.MODE_PRIVATE)
        val prefValueKey: String = when (position) {
            0 -> "TYPE"
            1 -> "PARTICIPANTS"
            2 -> "MAX_PRICE"
            3 -> "MAX_ACCESSIBILITY"
            else -> ""
        }

        if (holder.itemViewType == 0) {
            holder as RecyclerViewHolderTextPicker
            val prefTextValue = sharedPreferences.getString(prefValueKey, "ANY")

            // Set initial values
            holder.settingsItemTitle.text = dataProvider.text
            holder.textPickerValue.text = prefTextValue
            holder.settingsItemTextPicker.isVisible = true

            // TODO: Implement callback for "ANY" selection, not settingsItem
            holder.settingsItem.setOnClickListener {
                callback?.onItemClicked(holder.bindingAdapterPosition)
            }

            // Listeners

            holder.textPickerLeft.setOnClickListener {

            }

            holder.textPickerRight.setOnClickListener {

            }

        } else {
            holder as RecyclerViewHolderNumPicker
            val prefToggleKey: String = prefValueKey + "_TOGGLE"
            val prefNumValue = sharedPreferences.getInt(prefValueKey, 0)
            val prefToggleValue = sharedPreferences.getBoolean(prefToggleKey, false)

            // Set initial values
            holder.settingsItemTitle.text = dataProvider.text
            holder.numPickerNum.text = prefNumValue.toString()
            holder.settingsItemSwitch.isChecked = prefToggleValue
            holder.settingsItemNumPicker.isVisible = prefToggleValue

            // TODO: Implement callback for toggle, not settingsItem
            holder.settingsItem.setOnClickListener {
                callback?.onItemClicked(holder.bindingAdapterPosition)
            }

            // Listeners

            holder.settingsItemSwitch.setOnClickListener {
                val isChecked = holder.settingsItemSwitch.isChecked
                holder.settingsItemNumPicker.isVisible = isChecked
                sharedPreferences.edit().putBoolean(prefToggleKey, isChecked).apply()
            }

            holder.numPickerDecrease.setOnClickListener {
                sharedPreferences.edit().putInt(prefValueKey, newNum(false, holder.numPickerNum.toString().toInt()))
                    .apply()
            }
            holder.numPickerIncrease.setOnClickListener {
                sharedPreferences.edit().putInt(prefValueKey, newNum(true, holder.numPickerNum.toString().toInt()))
                    .apply()
            }
        }

    }

    private fun newNum(increase: Boolean, num: Int): Int {
        var newNum = 0
        if ((num >= 0 && increase) || num > 0) newNum =
            num + (if (increase) 1 else -1)
        return newNum
    }

    private fun newText(increase: Boolean, values: ArrayList<String>, currentIndex: Int) : String {
        return "";
    }

    override fun getItemCount(): Int {
        return dataSource.size
    }

    class RecyclerViewHolderNumPicker(view: View) : ViewHolder(view) {
        var settingsItem: RelativeLayout
        var settingsItemTitle: TextView
        var settingsItemSwitch: Switch
        var settingsItemNumPicker: View
        var numPickerDecrease: Button
        var numPickerIncrease: Button
        var numPickerNum: TextView

        init {
            settingsItem = view.findViewById(R.id.settings_item_num)
            settingsItemTitle = view.findViewById(R.id.settings_item_num_title)
            settingsItemSwitch = view.findViewById(R.id.settings_item_switch)
            settingsItemNumPicker = view.findViewById(R.id.settings_item_num_picker)
            numPickerDecrease = settingsItemNumPicker.findViewById(R.id.picker_decrease)
            numPickerIncrease = settingsItemNumPicker.findViewById(R.id.picker_increase)
            numPickerNum = settingsItemNumPicker.findViewById(R.id.picker_value)
        }
    }


    class RecyclerViewHolderTextPicker(view: View) : ViewHolder(view) {
        var settingsItem: RelativeLayout
        var settingsItemTitle: TextView
        var settingsItemTextPicker: View
        var textPickerLeft: Button
        var textPickerRight: Button
        var textPickerValue: TextView

        init {
            settingsItem = view.findViewById(R.id.settings_item_text)
            settingsItemTitle = view.findViewById(R.id.settings_item_text_title)
            settingsItemTextPicker = view.findViewById(R.id.settings_item_text_picker)
            textPickerLeft = settingsItemTextPicker.findViewById(R.id.picker_decrease)
            textPickerRight = settingsItemTextPicker.findViewById(R.id.picker_increase)
            textPickerValue = settingsItemTextPicker.findViewById(R.id.picker_value)
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