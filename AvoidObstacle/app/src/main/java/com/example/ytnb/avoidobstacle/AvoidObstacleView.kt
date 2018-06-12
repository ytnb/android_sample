package com.example.ytnb.avoidobstacle

import android.content.Context
import android.graphics.*
import android.graphics.BitmapFactory.decodeResource
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import java.util.*

class AvoidObstacleView(context: Context?): SurfaceView(context), SurfaceHolder.Callback, Runnable {
    private val mHolder = holder
    private val mPaint = Paint()
    private var mWidth = 0
    private var mHeight = 0

    private var mBitmapDroid: Bitmap? = null
    private var mBitmapObstacle: Bitmap? = null
    private var mIsAttached = false
    private var mThread = Thread()
    private var mRegionStartZone = Region()

    private var mGoalZone = Path()
    private var mRegionGoalZone = Region()
    private var mStartZone = Path()
    private var mOutZoneL = Path()
    private var mRegionOutZoneL = Region()
    private var mOutZoneR = Path()
    private var mRegionOutZoneR = Region()

    companion object {
        private const val OUT_WIDTH = 50.0F
        private const val GOAL_HEIGHT = 150.0F
        private const val START_HEIGHT = 150.0F
        private const val JUMP_HEIGHT = START_HEIGHT - 30.0F
        private const val DROID_POS = OUT_WIDTH + 50.0F
    }
    private var mIsGoal = false
    private var mIsGone = false
    private var mDroid: Droid? = null

    private val mObstacleList = mutableListOf<Obstacle>()
    private var mCanvas: Canvas? = null

    private var startTime = 0L
    private var endTime = 0L

    init {
        mHolder?.addCallback(this)
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        mPaint.color = Color.RED
        mPaint.isAntiAlias = true

        mWidth = width
        mHeight = height

        val res = resources
        mBitmapDroid = decodeResource(res, R.mipmap.android_black)
        mBitmapObstacle = decodeResource(res, R.mipmap.car_black)

        zoneDecide()
        newDroid()
        newObstacle()
        mIsAttached = true
        mThread = Thread(this)
        mThread.start()
    }

    override fun run() {
        while (mIsAttached) {
            drawGameBoard()
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        event?.let {
            when (it.action) {
                MotionEvent.ACTION_DOWN -> {
                    if (mRegionStartZone.contains(it.x.toInt(), it.y.toInt())) {
                        newDroid()
                        newObstacle()
                    }
                }
            }
        }
        return true
    }

    private fun zoneDecide() {
        mGoalZone.addRect(
                OUT_WIDTH,
                0.0f,
                mWidth - OUT_WIDTH,
                GOAL_HEIGHT,
                Path.Direction.CW
        )
        val regionWholeScreen = Region(0, 0, mWidth, mHeight)
        mRegionGoalZone.setPath(mGoalZone, regionWholeScreen)

        mStartZone.addRect(
                OUT_WIDTH,
                mHeight - START_HEIGHT,
                mWidth - OUT_WIDTH,
                mHeight.toFloat(),
                Path.Direction.CW
        )
        mRegionStartZone.setPath(mStartZone, regionWholeScreen)

        mOutZoneL.addRect(0.0f, 0.0f, OUT_WIDTH, mHeight.toFloat(), Path.Direction.CW)
        mRegionOutZoneL.setPath(mOutZoneL, regionWholeScreen)

        mOutZoneR.addRect(
                mWidth - OUT_WIDTH,
                0.0f,
                mWidth.toFloat(),
                mHeight.toFloat(),
                Path.Direction.CW
        )
        mRegionOutZoneR.setPath(mOutZoneR, regionWholeScreen)
    }

    public fun drawGameBoard() {
        if (mIsGone || mIsGoal) {
            return
        }

        mDroid?.let {
            it.move(MainActivity.role, MainActivity.pitch)
            if (it.getBottom() > mHeight) {
                it.setLocate(it.getLeft(), (mHeight - JUMP_HEIGHT).toInt())
            }
        }

        try {
            for (obstacle in mObstacleList) {
                obstacle.move()
            }

            mCanvas = holder.lockCanvas()
            mCanvas?.let {
                it.drawColor(Color.LTGRAY)

                mPaint.color = Color.MAGENTA
                it.drawPath(mGoalZone, mPaint)
                mPaint.color = Color.GRAY
                it.drawPath(mStartZone, mPaint)
                mPaint.color = Color.BLACK
                it.drawPath(mOutZoneR, mPaint)
                it.drawPath(mOutZoneL, mPaint)
                mPaint.textSize = 50F

                it.drawText(
                        resources.getString(R.string.goal),
                        (mWidth / 2 - 50).toFloat(),
                        100F,
                        mPaint
                )
                it.drawText(
                        resources.getString(R.string.start),
                        (mWidth / 2 - 50).toFloat(),
                        (mHeight - 50).toFloat(),
                        mPaint
                )
            }

            mDroid?.let {
                if (mRegionOutZoneL.contains(it.getCenterX(), it.getCenterY())) mIsGone = true
                if (mRegionOutZoneR.contains(it.getCenterX(), it.getCenterY())) mIsGone = true
                if (mRegionGoalZone.contains(it.getCenterX(), it.getCenterY())) {
                    mIsGoal = true
                    mPaint.color = Color.WHITE
                    mCanvas?.drawText(goaled(), OUT_WIDTH + 10, GOAL_HEIGHT - 100, mPaint)
                }
            }

            for (obstacle in mObstacleList) {
                if (mRegionStartZone.contains(obstacle.getLeft(), obstacle.getBottom())) {
                    obstacle.setLocate(obstacle.getLeft(), 0)
                }
            }
            if (mIsGoal.not()) {
                mDroid?.let {
                    for (obstacle in mObstacleList) {
                        if (it.collisionCheck(obstacle)) {
                            mPaint.color = Color.WHITE
                            mCanvas?.drawText(
                                    resources.getString(R.string.collision),
                                    OUT_WIDTH + 10,
                                    GOAL_HEIGHT - 100,
                                    mPaint
                            )
                            mIsGone = true
                        }
                    }
                }
            }

            if (!((mIsGone) || (mIsGoal))) {
                mPaint.color = Color.DKGRAY
                for (obstacle in mObstacleList) {
                    mCanvas?.drawBitmap(
                            mBitmapObstacle,
                            obstacle.getLeft().toFloat(),
                            obstacle.getTop().toFloat(),
                            null
                    )
                }
                mDroid?.let {
                    mCanvas?.drawBitmap(
                            mBitmapDroid,
                            it.getLeft().toFloat(),
                            it.getTop().toFloat(),
                            null
                    )
                }
            }
            holder.unlockCanvasAndPost(mCanvas)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun goaled(): String {
        endTime = System.currentTimeMillis()
        val sectime = ((endTime-startTime) / 10000).toInt()
        return "Goal! $sectime 秒"
    }

    override fun surfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {
    }

    override fun surfaceDestroyed(holder: SurfaceHolder?) {
        if (mBitmapDroid != null) {
            mBitmapDroid?.recycle()
            mBitmapDroid = null
        }
        if (mBitmapObstacle != null) {
            mBitmapObstacle?.recycle()
            mBitmapObstacle = null
        }
        mIsAttached = false
        while (mThread.isAlive) {}
    }

    private fun newDroid() {
        mBitmapDroid?.let {
            mDroid = Droid(
                    DROID_POS.toInt(),
                    (mHeight - JUMP_HEIGHT).toInt(),
                    it.width,
                    it.height
            )
        }
        mIsGone = false
        mIsGoal = false
        startTime = System.currentTimeMillis()
    }

    private fun newObstacle() {
        mObstacleList.clear()
        val rand = Random()
        mBitmapObstacle?.let {
            (1..20).forEach { i ->
                val bound = mWidth - (OUT_WIDTH.toInt() * 2 + it.width) + OUT_WIDTH.toInt()
                val left = rand.nextInt(bound)
                val top = rand.nextInt(mHeight - it.height * 2)
                val speed = rand.nextInt(3) + 1

                val obstacle = Obstacle(left, top, it.width, it.height, speed)
                mObstacleList.add(obstacle)
            }
        }
    }
}