package com.example.ytnb.sensorget

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sensorManager: SensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val list = sensorManager.getSensorList(Sensor.TYPE_ALL)
        val strBuilder = StringBuilder()
        for (sensor in list) {
            strBuilder.append("${sensor.type},${sensor.name},${sensor.vendor}")
            strBuilder.appendln()
        }
        tv_txt.text = strBuilder.toString()
    }
}
