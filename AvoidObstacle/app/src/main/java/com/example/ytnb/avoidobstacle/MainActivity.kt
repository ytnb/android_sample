package com.example.ytnb.avoidobstacle

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager

class MainActivity : AppCompatActivity(),SensorEventListener {

    private var mSurfaceView: AvoidObstacleView? = null
    private var mSensorManager: SensorManager? = null
    private var mMagField: Sensor? = null
    private var mAccelerometer : Sensor? = null
    companion object {
        const val MATRIX_SIZE = 16
        var pitch = 0
        var role = 0
    }
    private var mgValue: FloatArray = FloatArray(3)
    private var acValue: FloatArray = FloatArray(3)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        mSurfaceView = AvoidObstacleView(this)
        setContentView(mSurfaceView)

        mSensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        mAccelerometer = mSensorManager?.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        mMagField = mSensorManager?.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)
    }

    override fun onResume() {
        super.onResume()
        mSensorManager?.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_GAME)
        mSensorManager?.registerListener(this, mMagField, SensorManager.SENSOR_DELAY_GAME)
    }

    override fun onPause() {
        super.onPause()
        mSensorManager?.unregisterListener(this, mAccelerometer)
        mSensorManager?.unregisterListener(this, mMagField)
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event != null) {
            when (event.sensor.type) {
                Sensor.TYPE_ACCELEROMETER -> acValue = event.values.clone()
                Sensor.TYPE_MAGNETIC_FIELD -> mgValue = event.values.clone()
            }

            val inR = FloatArray(MATRIX_SIZE)
            val I = FloatArray(MATRIX_SIZE)
            SensorManager.getRotationMatrix(inR, I, acValue, mgValue)

            val outR = FloatArray(MATRIX_SIZE)
            SensorManager.remapCoordinateSystem(inR, SensorManager.AXIS_X, SensorManager.AXIS_Y, outR)

            val orValues = FloatArray(3)
            SensorManager.getOrientation(outR, orValues)

            pitch = rad2Deg(orValues[1])
            role = rad2Deg(orValues[2])
        }
    }

    private fun rad2Deg(rad: Float): Int {
        return Math.floor(Math.toDegrees(rad.toDouble())).toInt()
    }
}
