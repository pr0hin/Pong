 /*
  *Contains attributes of the Ball object
  *@author Rohin Patel (unless otherwise indicated)
  *Elements taken from tutorial Obviam.net
  */
package com.example.gamemodels;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Ball {

	public int x; // x position of ball
	public int y; // y position of ball
	private float dx = 1;
	private float dy = 1;
	public static final int DIRECTION_LEFT = -1;
	public static final int DIRECTION_RIGHT = 1;
	public static final int DIRECTION_DOWN = 1;
	public static final int DIRECTION_UP = -1;

	public Bitmap ball;
	private int radius;
	private int xDirection = DIRECTION_RIGHT;
	private int yDirection = DIRECTION_DOWN;

	public Ball(Bitmap ball, int x, int y, float dx, float dy) {
		this.ball = ball;
		this.x = x;
		this.y = y;
		this.setDx(dx);
		this.setDy(dy);
		setRadius(ball.getHeight() / 2);

	}

	public Bitmap getBall() {
		return ball;
	}

	public void setBall(Bitmap ball) {
		this.ball = ball;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void draw(Canvas canvas) {
		canvas.drawBitmap(ball, x - (ball.getWidth() / 2),
				y - (ball.getHeight() / 2), null);
	}

	public int getRadius() {
		return radius;
	}

	public void setRadius(int radius) {
		this.radius = radius;
	}

	public float getDx() {
		return dx;
	}

	public void setDx(float dx) {
		this.dx = dx;
	}

	public float getDy() {
		return dy;
	}

	public void setDy(float dy) {
		this.dy = dy;
	}

	public int getXDirection() {
		return xDirection;
	}

	public void setXDirection(int xDirection) {
		this.xDirection = xDirection;
	}

	// switches x direction
	public void toggleXDirection() {
		xDirection = xDirection * -1;
	}

	public int getYDirection() {
		return yDirection;
	}

	public void setYDirection(int yDirection) {
		this.yDirection = yDirection;
	}

	public void toggleYDirection() {
		yDirection = yDirection * -1;
	}

	public void update() {
		x += (this.getDx() * this.getXDirection());
		y += (this.getDy() * this.getYDirection());
	}


	

}
