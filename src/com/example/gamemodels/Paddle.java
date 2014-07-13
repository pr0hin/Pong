 /**
  *Contains the attributes of the player controlled Paddle object
  *@author Rohin Patel (unless otherwise indicated)
  *Elements taken from tutorial Obviam.net
  */
package com.example.gamemodels;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Paddle{
	
	private int x; // x position of paddle
	private int y; // y position of paddle
	private boolean touched; // return true if touched
	private Bitmap img;
	public int width;
	public int height;
    

	public Paddle(Bitmap img, int x, int y) {
		this.setImg(img);
		this.x = x;
		this.y = y;
		this.width = img.getWidth();
		this.height= img.getHeight();
		

		
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
	
	public boolean isTouched() {
		return touched;
	}

	public void setTouched(boolean touched) {
		this.touched = touched;
	}

	public int centerX() {
		int sum;
		sum = getX() - this.width / 2;
		return sum;
	}


	public Bitmap getImg() {
		return img;
	}


	public void setImg(Bitmap img) {
		this.img = img;
	}

	public void draw(Canvas canvas) {
		canvas.drawBitmap(img, x - (img.getWidth() / 2), y - (img.getHeight() / 2), null);
		
	}
	
	public void handleOnPressure(int eventX, int eventY) {
		// x - imgwidth/2 = boundary to left of image, x + imgwidth/2 = boundary to right of image
		if ( (eventX >= (x - img.getWidth() / 2)) && (eventX <= (x + img.getWidth() / 2)) ) {
			// y - imgheight/2 = boundary above image, y + imgheight/2 = boundary below image
		
			
				setTouched(true);
		}
			else {
				setTouched(false);
			}
		}
	

	}
	

