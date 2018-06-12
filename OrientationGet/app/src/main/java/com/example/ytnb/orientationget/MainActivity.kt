package com.example.ytnb.orientationget

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
    private var mAccelerometer: Sensor? = null

    private var mgValues = FloatArray(3)
    private var acValues = FloatArray(3)

    companion object {
        const val MATRIX_SIZE = 16
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mSensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        mAccelerometer = mSensorManager?.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        mMagField = mSensorManager?.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)
    }

    override fun onResume() {
        super.onResume()
        mSensorManager?.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL,0)
        mSensorManager?.registerListener(this, mMagField, SensorManager.SENSOR_DELAY_NORMAL,0)
    }

    override fun onPause() {
        super.onPause()
        mSensorManager?.unregisterListener(this, mAccelerometer)
        mSensorManager?.unregisterListener(this, mMagField)
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event != null){
            when (event.sensor.type) {
                Sensor.TYPE_ACCELEROMETER -> acValues = event.values.clone()
                Sensor.TYPE_MAGNETIC_FIELD -> mgValues = event.values.clone()
            }
            val inR = FloatArray(MATRIX_SIZE)
            val I = FloatArray(MATRIX_SIZE)
            SensorManager.getRotationMatrix(inR, I, acValues, mgValues)

            val outR = FloatArray(MATRIX_SIZE)
            SensorManager.remapCoordinateSystem(inR, SensorManager.AXIS_X, SensorManager.AXIS_Y, outR)

            val orValues = FloatArray(3)
            SensorManager.getOrientation(outR, orValues)

            val stringBuilder = StringBuilder()
            stringBuilder.appendln("方位角（アジマス）:${rad2Deg(orValues[0])}")
            stringBuilder.appendln("傾斜角（ピッチ）:${rad2Deg(orValues[1])}")
            stringBuilder.appendln("回転角（ロール）:${rad2Deg(orValues[2])}")
            tv_txt.text = stringBuilder.toString()
        }
    }

    private fun rad2Deg(rad: Float): Int {
        return Math.floor(
                Math.toDegrees(
                        rad.toDouble()
                )).toInt()
    }
}
