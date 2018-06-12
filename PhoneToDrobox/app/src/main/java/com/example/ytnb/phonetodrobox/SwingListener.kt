package com.example.ytnb.phonetodrobox

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager

class SwingListener(context: Context): SensorEventListener {
    private val mSensorManager: SensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    private lateinit var mListener: OnSwingListener
    private var mAccelerometer: Sensor? = null
    private var mPreTime: Long = 0L
    private var nValues = FloatArray(3)
    private var oValues = floatArrayOf(0.0F, 0.0F, 0.0F)
    private var mSwingCount = 0
    companion object {
        private const val LI_SWING = 50
        private const val CNT_SWING = 3
    }

    interface OnSwingListener {
        fun onSwing()
    }

    fun setOnSwingListener(listener: OnSwingListener) {
        mListener = listener
    }

    fun registSensor() {
        val list = mSensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER)
        if (list.size > 0) {
            mAccelerometer = list[0]
        }
        if (mAccelerometer != null) {
            mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_GAME)
        }
    }

    fun unregistSensor() {
        if (mAccelerometer != null) {
            mSensorManager.unregisterListener(this)
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event?.sensor?.type != Sensor.TYPE_ACCELEROMETER) {
            return
        }

        val currentTime = System.currentTimeMillis()
        val diffTime = currentTime - mPreTime

        if (diffTime > 100) {
            nValues[0] = event.values[0]
            nValues[1] = event.values[1]
            nValues[2] = event.values[2]
            val speed = (Math.abs(nValues[0] - oValues[0]) + Math.abs(nValues[1] - oValues[1]) + Math.abs(nValues[2] - oValues[2])) / diffTime * 1000
            if (speed > LI_SWING) {
                mSwingCount++
                if (mSwingCount > CNT_SWING) {
                    mListener.onSwing()
                }
                mSwingCount = 0
            }
        } else {
            mSwingCount = 0
        }
        mPreTime = currentTime
        oValues[0] = nValues[0]
        oValues[1] = nValues[1]
        oValues[2] = nValues[2]
    }
}