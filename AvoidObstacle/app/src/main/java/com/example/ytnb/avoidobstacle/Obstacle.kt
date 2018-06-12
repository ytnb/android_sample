package com.example.ytnb.avoidobstacle

class Obstacle(left: Int, top: Int, width: Int, height: Int, speed: Int):
        GameObject(left, top, width, height) {
    private var mSpeed = speed

    public fun move() {
        super.move(0, mSpeed)
    }

    public fun setSpeed(speed: Int) {
        mSpeed = speed
    }
}