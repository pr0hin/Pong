 /*
  *Contains the attributes of the AI object
  *@author Rohin Patel (unless otherwise indicated)
  *Elements taken from tutorial Obviam.net
  */
package com.example.gamemodels;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class AI {

	private int x; // x position of AIpaddle
	private int y; // y position of AIpaddle
	private Bitmap img;
	public int width;
	public int height;
	private double dx;

	private int LEFT_DIRECTION = -1;
	private int RIGHT_DIRECTION = 1;
	private int xDirection = RIGHT_DIRECTION;

	public AI(Bitmap img, int x, int y, double d) {
		this.setImg(img);
		this.x = x;
		this.y = y;
		this.dx = d;
		this.width = img.getWidth();
		this.height = img.getHeight();

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

	public double getDX() {
		return dx;
	}

	public int getXDirection() {
		return xDirection;
	}

	public void toggleX() {
		xDirection = xDirection * -1;
	}

	public void setXDirection(int xDirection) {
		this.xDirection = xDirection;
	}

	public Bitmap getImg() {
		return img;
	}

	public void setImg(Bitmap img) {
		this.img = img;
	}

	public void draw(Canvas canvas) {
		canvas.drawBitmap(img, x - (img.getWidth() / 2), y
				- (img.getHeight() / 2), null);

	}

	public void update(int xDirection) {
		x += (this.getDX() * xDirection);

	}

	public void setDX(int dx) {
		
		this.dx= dx;
		// TODO Auto-generated method stub
		
	}

	// TODO Auto-generated constructor stub
}
