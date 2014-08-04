package com.example.gamemodels;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class AbstractModel {
	private int x;
	private int y;
	private Bitmap img;
	private int width;
	private int height;

	
	public AbstractModel(Bitmap img, int x, int y) {
		// TODO Auto-generated constructor stub
		setImg(img);
		this.x = x;
		this.y = y;
		this.setWidth(img.getWidth());
		this.setHeight(img.getHeight());
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
	
	public Bitmap getImg() {
		return img;
	}


	public void setImg(Bitmap img) {
		this.img = img;
	}




	public int getWidth() {
		return width;
	}



	public void setWidth(int width) {
		this.width = width;
	}



	public int getHeight() {
		return height;
	}



	public void setHeight(int height) {
		this.height = height;
	}
}
