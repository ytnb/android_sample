package com.example.ytnb.ryukyusound

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.media.MediaPlayer
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), SensorEventListener {

    private var mSensorManager: SensorManager? = null
    private var mMagField: Sensor? = null
    private var mAccelerometer: Sensor? = null

    companion object {
        const val AZIMUTH_THRESHOLD = 15
        const val MATRIX_SIZE = 16
    }

    private var mplayer: ArrayList<MediaPlayer> = arrayListOf()
    private var mgValues: FloatArray = FloatArray(3)
    private var acValues: FloatArray = FloatArray(3)

    private var nowScale = 0
    private var oldScale = 9
    private var nowAzimuth = 0
    private var oldAzimuth = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        mSensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        mAccelerometer = mSensorManager?.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        mMagField = mSensorManager?.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)
    }

    override fun onResume() {
        super.onResume()
        mSensorManager?.registerListener(this, mAccelerometer, 100000)
        mSensorManager?.registerListener(this, mMagField, 100000)

        val notes = resources.obtainTypedArray(R.array.notes)
        var index = 0
        while (index < notes.length()) {
            mplayer.add(MediaPlayer.create(this, notes.getResourceId(index, -1)))
            index++
        }
        notes.recycle()
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
                Sensor.TYPE_ACCELEROMETER -> acValues = event.values.clone()
                Sensor.TYPE_MAGNETIC_FIELD -> mgValues = event.values.clone()
            }
            val inR = FloatArray(MATRIX_SIZE)
            val I = FloatArray(MATRIX_SIZE)
            SensorManager.getRotationMatrix(inR, I, acValues, mgValues)

            val outR = FloatArray(MATRIX_SIZE)
            SensorManager.remapCoordinateSystem(inR, SensorManager.AXIS_X, SensorManager.AXIS_Z, outR)
            val orValues = FloatArray(MATRIX_SIZE)
            SensorManager.getOrientation(outR, orValues)

            val strBuild = StringBuilder()
            strBuild.appendln("方位角（アジマス）:${rad2Deg(orValues[0])}")
            strBuild.appendln("傾斜角（ピッチ）:${rad2Deg(orValues[1])}")

            nowScale = rad2Deg(orValues[1]) / 10
            strBuild.append("index: $nowScale")
            nowAzimuth = rad2Deg(orValues[0])
            tv_txt.text = strBuild.toString()

            if(nowScale != oldScale) {
                playSound(nowScale)
                oldScale = nowScale
                oldAzimuth = nowAzimuth
            } else if (Math.abs(oldAzimuth - nowAzimuth) > AZIMUTH_THRESHOLD) {
                playSound(nowScale)
                oldAzimuth = nowAzimuth
            }
        }
    }

    private fun rad2Deg(rad: Float): Int {
        return Math.floor(Math.abs(Math.toDegrees(rad.toDouble()))).toInt()
    }

    private fun playSound(scale: Int) {
        mplayer[scale].seekTo(0)
        mplayer[scale].start()
    }
}
