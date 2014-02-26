package com.example.appui;

import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;

public class MainThread extends Thread {

	private static final String TAG = MainThread.class.getSimpleName();
	private SurfaceHolder surfaceHolder;
	private MainGamePanel gamePanel;
	// flag to hold gamestate
	private boolean running;
	public int h;
	public int w;

	public MainThread(SurfaceHolder surfaceHolder, MainGamePanel gamePanel) {
		super();
		this.surfaceHolder = surfaceHolder;
		this.gamePanel = gamePanel;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}

	public void run() {
		Canvas canvas;
		Log.d(TAG, "Starting game loop...");
		while (running) {
			canvas = null;
			long starttime = System.currentTimeMillis();
			// try locking the canvas for exclusive pixel editing
			try {
				canvas = this.surfaceHolder.lockCanvas();
				if (canvas != null) {
					synchronized (surfaceHolder) {
						if (gamePanel.touchingWall()) {

						}
						// update game state
						// draw canvas on panel

						this.gamePanel.onDraw(canvas);
						gamePanel.moveAI();
						gamePanel.updateBall();

					}
				}
			} finally {
				// in case of an exception the surface is not left in
				// inconsistent state
				if (canvas != null) {
					surfaceHolder.unlockCanvasAndPost(canvas);
				}
			}
			long sleeptime = 1000 - (System.currentTimeMillis() - starttime);

		}

	}
}
