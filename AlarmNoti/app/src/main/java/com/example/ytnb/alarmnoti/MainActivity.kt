package com.example.ytnb.alarmnoti

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var mLayout: ConstraintLayout
    private lateinit var mInputMethodManager: InputMethodManager
    private var notificationId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //timePicker.setIs24HourView(true)

        mLayout = this.mainLayout
        mInputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        val btnSet = button_set
        btnSet.setOnClickListener(this)
        val btnCancel = button_cancel
        btnCancel.setOnClickListener(this)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        mInputMethodManager.hideSoftInputFromWindow(mLayout.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
        mLayout.requestFocus()
        return false
    }

    override fun onClick(v: View?) {
        val bootIntent = Intent(MainActivity@this, AlarmReceiver::class.java)
        bootIntent.putExtra("notificationId", notificationId)
        bootIntent.putExtra("todo", et_todo.text)

        val alarmIntent = PendingIntent.getBroadcast(this@MainActivity, 0, bootIntent, PendingIntent.FLAG_CANCEL_CURRENT)
        val alarm = getSystemService(Context.ALARM_SERVICE) as AlarmManager

        when (v?.id) {
            R.id.button_set -> {
                val hour = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    timePicker.hour
                } else {
                    timePicker.currentHour
                }
                val min = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    timePicker.minute
                } else {
                    timePicker.currentMinute
                }

                val startTime = Calendar.getInstance()
                startTime.set(Calendar.HOUR_OF_DAY, hour)
                startTime.set(Calendar.MINUTE, min)
                startTime.set(Calendar.SECOND, 0)
                val alarmStartTime = startTime.timeInMillis

                alarm.set(
                        AlarmManager.RTC_WAKEUP,
                        alarmStartTime,
                        alarmIntent
                )
                Toast.makeText(MainActivity@this, "通知をセットしました！", Toast.LENGTH_SHORT).show()
                notificationId++
            }
            R.id.button_cancel -> {
                alarm.cancel(alarmIntent)
                Toast.makeText(MainActivity@this, "通知をキャンセルしました！", Toast.LENGTH_SHORT).show()

            }

        }

    }
}
