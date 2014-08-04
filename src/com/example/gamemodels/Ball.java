 /*
  *Contains attributes of the Ball object
  *@author Rohin Patel (unless otherwise indicated)
  *Elements taken from tutorial Obviam.net
  */
package com.example.gamemodels;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Ball extends AbstractModel {


	private double dx;
	private double dy;
	public static final int DIRECTION_LEFT = -1;
	public static final int DIRECTION_RIGHT = 1;
	public static final int DIRECTION_DOWN = 1;
	public static final int DIRECTION_UP = -1;
	private int angle;
	private int radius;
	private int xDirection = DIRECTION_RIGHT;
	private int yDirection = DIRECTION_DOWN;

	public Ball(Bitmap ball, int x, int y, int angle) {
		super(ball, x, y);
		setRadius(ball.getHeight() / 2);
		setAngle(angle);
	}



	
	public void draw(Canvas canvas) {
		canvas.drawBitmap(getImg(), getX() - (getImg().getWidth() / 2),
				getY() - (getImg().getHeight() / 2), null);
	}

	public int getRadius() {
		return radius;
	}

	public void setRadius(int radius) {
		this.radius = radius;
	}

	public double getDx() {
		return dx;
	}

	public void setDx(float dx) {
		this.dx = dx;
	}

	public double getDy() {
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
		setX((int) (getX() + (this.getDx() * this.getXDirection())));
		setY((int) (getY() + (this.getDy() * this.getYDirection())));
	}

	public int getAngle() {
		return angle;
	}

	public void setAngle(int angle) {
		this.angle = angle;
		dx = 15 * Math.cos(Math.toRadians(angle));
		dy = 15 * Math.sin(Math.toRadians(angle));
	}


	

}
