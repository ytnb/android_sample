package com.example.ytnb.avoidobstacle

open class GameObject(left: Int, top: Int, width: Int, height: Int) {
    private var mLeft = left
    private var mTop = top
    private var mWidth = width
    private var mHeight = height

    fun setLocate(left: Int, top: Int) {
        mLeft = left
        mTop = top
    }

    open fun move(left: Int, top: Int) {
        mLeft += left
        mTop += top
    }

    fun getLeft(): Int {
        return mLeft
    }

    fun getRight(): Int {
        return mLeft + mWidth
    }

    fun getTop(): Int {
        return mTop
    }

    fun getBottom(): Int {
        return mTop + mHeight
    }

    fun getCenterX(): Int {
        return mLeft + mWidth / 2
    }

    fun getCenterY(): Int {
        return mTop + mHeight /2
    }
}