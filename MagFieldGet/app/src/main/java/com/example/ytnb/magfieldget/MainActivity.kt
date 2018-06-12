package com.example.ytnb.magfieldget

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), SensorEventListener {
    private var mSensorManager: SensorManager? = null
    private var mMagField: Sensor? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mSensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        mMagField = mSensorManager?.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)
    }

    override fun onResume() {
        super.onResume()
        mSensorManager?.registerListener(this, mMagField, SensorManager.SENSOR_DELAY_NORMAL,0)
    }

    override fun onPause() {
        super.onPause()
        mSensorManager?.unregisterListener(this)
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event != null) {
            val stringBuilder = StringBuilder()

            stringBuilder.appendln("X方向:${event.values[0]}μT")
            stringBuilder.appendln("Y方向:${event.values[1]}μT")
            stringBuilder.appendln("Z方向:${event.values[2]}μT")

            tv_txt.text = stringBuilder.toString()
        }
    }
}
