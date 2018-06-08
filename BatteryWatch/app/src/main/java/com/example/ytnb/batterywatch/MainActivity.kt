package com.example.ytnb.batterywatch

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var mReceiver: MyBroadcastReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()
        mReceiver = MyBroadcastReceiver()
        val filter = IntentFilter().also {
            it.addAction(Intent.ACTION_BATTERY_CHANGED)
        }
        registerReceiver(mReceiver, filter)
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(mReceiver)
    }

    private class MyBroadcastReceiver: BroadcastReceiver() {

        override fun onReceive(context: Context?, intent: Intent?) {
            intent ?: return
            if (intent.action == Intent.ACTION_BATTERY_CHANGED) {
                val scale = intent.extras["scale"]
                val level = intent.extras["level"]
                val status =  intent.extras["status"]
                val statusString: String = when(status) {
                    BatteryManager.BATTERY_STATUS_CHARGING -> "charging"
                    BatteryManager.BATTERY_STATUS_DISCHARGING -> "discharging"
                    BatteryManager.BATTERY_STATUS_NOT_CHARGING -> "not charging"
                    BatteryManager.BATTERY_STATUS_FULL -> "full"
                    BatteryManager.BATTERY_STATUS_UNKNOWN -> "unknown"
                    else -> "unknown"
                }
                val calendar = Calendar.getInstance()
                val hour = calendar.get(Calendar.HOUR_OF_DAY)
                val min = calendar.get(Calendar.MINUTE)
                val sec = calendar.get(Calendar.SECOND)
                Log.v("Battery Watch", "$hour:$min:$sec $statusString $level/$scale")
            }
        }
    }
}
