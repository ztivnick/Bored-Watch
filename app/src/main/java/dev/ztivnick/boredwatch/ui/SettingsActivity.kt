package dev.ztivnick.boredwatch.ui

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.wear.widget.WearableLinearLayoutManager
import androidx.wear.widget.WearableRecyclerView
import dev.ztivnick.boredwatch.R
import dev.ztivnick.boredwatch.SettingsAdapter
import dev.ztivnick.boredwatch.SettingsItem

class SettingsActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val recyclerView: WearableRecyclerView = findViewById(R.id.settings_view)
        recyclerView.setHasFixedSize(true)
        recyclerView.isEdgeItemsCenteringEnabled = true
        recyclerView.layoutManager = WearableLinearLayoutManager(this)

        val settingsItems: ArrayList<SettingsItem> = ArrayList()
        settingsItems.add(SettingsItem("Type"))
        settingsItems.add(SettingsItem("Participants"))
        settingsItems.add(SettingsItem("Max Price"))
        settingsItems.add(SettingsItem("Max Accessibility"))

        val onPressCallback = object : SettingsAdapter.AdapterCallback {
            override fun onItemClicked(settingsMenuPosition: Int?) {
                Log.d("CLICKED", settingsMenuPosition!!.toString())
            }
        }

        recyclerView.adapter = SettingsAdapter(this, settingsItems, onPressCallback)
    }
}