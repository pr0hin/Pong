 /*
  *Contains the attributes of the AI object
  *@author Rohin Patel (unless otherwise indicated)
  *Elements taken from tutorial Obviam.net
  */
package com.example.gamemodels;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class AI extends AbstractModel {


	private double dx;
	private int score = 0;
	Paint paint = new Paint();
	private int LEFT_DIRECTION = -1;
	private int RIGHT_DIRECTION = 1;
	private int xDirection = RIGHT_DIRECTION;

	public AI(Bitmap img, int x, int y, double d) {
		super(img,x,y);
		this.dx = d;
		int ar = Color.argb(255, 52, 152, 219);
		paint.setColor(ar);
		paint.setFakeBoldText(true);
		paint.setTextAlign(Paint.Align.CENTER);

		
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



	public void draw(Canvas canvas) {
		canvas.drawBitmap(getImg(), getX() - (getImg().getWidth() / 2), getY()
				- (getImg().getHeight() / 2), null);
		String textscore = Integer.toString(score);
		paint.setTextSize(canvas.getHeight() / 6);
		canvas.drawText(textscore, canvas.getWidth()/2, canvas.getHeight()/2 + 300, paint);
		
	}

	public void update(int xDirection) {
		setX((int)(getX() + this.getDX() * xDirection));

	}

	public void setDX(int dx) {
		
		this.dx= dx;
		// TODO Auto-generated method stub
		
	}

	public int getScore() {
		return score;
	}

	public void setScore() {
		score++;
	}

	// TODO Auto-generated constructor stub
}
