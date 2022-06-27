package dev.ztivnick.boredwatch.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import dev.ztivnick.boredwatch.R
import dev.ztivnick.boredwatch.RetrofitObject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async


class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val requestActivityButton = findViewById<ImageButton>(R.id.requestActivityButton)
        val settingsButton = findViewById<ImageButton>(R.id.settingsButton)

        requestActivityButton.setOnClickListener {
             var deferred = CoroutineScope(Dispatchers.Main).async {
                val result = RetrofitObject.retrofitInstance.getActivity()
                if (result.body() != null) {
                    findViewById<TextView>(R.id.text).text = result.body()!!.activity
                }
            }
        }

        settingsButton.setOnClickListener {
            val switchActivityIntent = Intent(this, SettingsActivity::class.java)
            startActivity(switchActivityIntent)
        }
    }
}