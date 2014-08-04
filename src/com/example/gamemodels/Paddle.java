 /**
  *Contains the attributes of the player controlled Paddle object
  *@author Rohin Patel (unless otherwise indicated)
  *Elements taken from tutorial Obviam.net
  */
package com.example.gamemodels;

import android.R;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Paddle extends AbstractModel{
	

	private boolean touched; // return true if touched

    private int score = 0;
    private Paint paint = new Paint();

	public Paddle(Bitmap img, int x, int y) {
		super(img,x,y);

		int ar = Color.argb(255, 230, 126, 34);
		paint.setFakeBoldText(true);
		paint.setColor(ar);
		paint.setTextAlign(Paint.Align.CENTER);

		
	}



	
	public boolean isTouched() {
		return touched;
	}

	public void setTouched(boolean touched) {
		this.touched = touched;
	}

	public int centerX() {
		int sum;
		sum = getX() - this.getWidth() / 2;
		return sum;
	}




	public void draw(Canvas canvas) {
		canvas.drawBitmap(this.getImg(), getX() - (this.getImg().getWidth() / 2), getY() - (this.getImg().getHeight() / 2), null);
		String textscore = Integer.toString(score);
		paint.setTextSize(canvas.getHeight() / 6);
		canvas.drawText(textscore, canvas.getWidth()/2, canvas.getHeight()/2 - 200, paint);
	}
	
	public void handleOnPressure(int eventX, int eventY) {
		// x - imgwidth/2 = boundary to left of image, x + imgwidth/2 = boundary to right of image
		if ( (eventX >= (getX() - getImg().getWidth() / 2)) && (eventX <= (getX() + getImg().getWidth() / 2)) ) {
			// y - imgheight/2 = boundary above image, y + imgheight/2 = boundary below image
		
			
				setTouched(true);
		}
			else {
				setTouched(false);
			}
		}


	public int getScore() {
		return score;
	}


	public void setScore() {
		score++;
	}
	

	}
	

