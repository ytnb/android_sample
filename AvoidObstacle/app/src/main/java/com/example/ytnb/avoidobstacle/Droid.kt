package com.example.ytnb.avoidobstacle

class Droid(left: Int, top: Int, width: Int, height: Int) : GameObject(left, top, width, height) {

    companion object {
        private const val SAFE_AREA = 80
    }

    override fun move(role: Int, pitch: Int) {
        super.move(role / 2, -(pitch / 2))
    }

    fun collisionCheck(obstacle: Obstacle): Boolean {
        return (this.getLeft() + SAFE_AREA < obstacle.getRight()) &&
                (this.getTop() + SAFE_AREA < obstacle.getBottom()) &&
                (this.getRight() - SAFE_AREA > obstacle.getLeft()) &&
                (this.getBottom() - SAFE_AREA > obstacle.getTop())
    }
}
