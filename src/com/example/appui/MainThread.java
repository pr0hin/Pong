 /*
  *Handles the thread running the game
  *@author Rohin Patel (unless otherwise indicated)
  *Elements taken from tutorial Obviam.net
  */
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
		long previous = System.currentTimeMillis();
		long lag = 0;
		while (running) {
			canvas = null;
			// try locking the canvas for exclusive pixel editing
			try {
				canvas = this.surfaceHolder.lockCanvas();
                long current = System.currentTimeMillis();
				long elapsed = current - previous;
				previous = current;
				lag += elapsed;
				if (canvas != null) {
					synchronized (surfaceHolder) {
						while (lag >= 14) {
						// update game state
						gamePanel.update();
						lag -= 14;
						}
						// render updated gamestate 
						gamePanel.onDraw(canvas);
					}
				}
			}
			catch (Exception e) {
				System.out.println(e.getMessage());
			}	
				finally {
			}
				// in case of an exception the surface is not left in
				// inconsistent state
				if (canvas != null) {
					surfaceHolder.unlockCanvasAndPost(canvas);
				}
			}

		}

	}

