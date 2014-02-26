package com.example.appui;

//TODO
/*
 * divide background in half
 * spawn ball from center on scoring
 * fix AI
 * larger paddles and ball
 */
import java.util.Random;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.gamemodels.Ball;
import com.example.gamemodels.Paddle;
import com.example.gamemodels.AI;

public class MainGamePanel extends SurfaceView implements
		SurfaceHolder.Callback {
	private static final String TAG = MainGamePanel.class.getSimpleName();

	private MainThread thread;
	private Paddle paddle;
	private AI AI;
	private Ball ball;
	private boolean touchedWall; // true means object has touched wall
	private boolean isCollision; // true means ball has collided with paddle
	private int cpuScore;
	private int userScore;

	public MainGamePanel(Context context) {
		super(context);
		// adding the callback (this) to the surface holder to intercept events
		getHolder().addCallback(this);

		// create AI to move around
		AI = new AI(BitmapFactory.decodeResource(getResources(),
				R.drawable.paddle), 350, 100, 12);

		// create paddle to move around
		paddle = new Paddle(BitmapFactory.decodeResource(getResources(),
				R.drawable.paddle), 350, 1100);

		// create ball
		ball = new Ball(BitmapFactory.decodeResource(getResources(),
				R.drawable.ball), 250, getHeight() / 2, 12, 7);
		ball.setXDirection(ball.DIRECTION_LEFT);
		long randomy = Math.round(Math.random());
		if (randomy == 0) {
			ball.setYDirection(ball.DIRECTION_DOWN);
		} else if (randomy == 1) {
			ball.setYDirection(ball.DIRECTION_UP);
		}
		// create the game loop thread
		thread = new MainThread(getHolder(), this);

		// make the gamepanel focusable so it can handle events
		setFocusable(true);
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {

		thread.setRunning(true);
		thread.start();
		// TODO Auto-generated method stub

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		Log.d(TAG, "Surface is being destroyed...");
		// tell the thread to shut down and wait for it to finish
		// this is a clean shutdown
		thread.setRunning(false);
		thread.interrupt();
		boolean retry = true;
		while (retry) {
			try {
				thread.join();
				retry = false;

			} catch (InterruptedException e) {

				// try again shutting down the thread
			}
		}

		Log.d(TAG, "Thread was shut down cleanly");

	}

	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {

			paddle.handleOnPressure((int) event.getX(), (int) event.getY());

		}

		if (event.getAction() == MotionEvent.ACTION_MOVE) {
			if (paddle.isTouched() && (!paddleBoundary(event))) {

				paddle.setX((int) event.getX());

			}
		}
		if (event.getAction() == MotionEvent.ACTION_UP) {
			if (paddle.isTouched()) {
				paddle.setTouched(false);
			}
		}

		return true;
	}

	// checks whether the edge of each paddle touches the wall
	public boolean paddleBoundary(MotionEvent event) {
		setTouchedWall(false);
		if (event.getX() + paddle.width / 2 >= getWidth()) {

			paddle.setX(getWidth() - paddle.width / 2);
			setTouchedWall(true);
		} else if (event.getX() - paddle.width / 2 <= 0) {
			paddle.setX(0 + paddle.width / 2);
			setTouchedWall(true);
		}
		return isTouchedWall();
	}

	// check whether the AI paddle has touched the wall
	public boolean aiBoundary() {
		setTouchedWall(false);
		if (AI.getX() + AI.width / 2 >= getWidth()) {
			AI.setX(getWidth() - AI.width / 2);
			setTouchedWall(true);
		} else if (AI.getX() - AI.width / 2 <= 0) {
			AI.setX(0 + AI.width / 2);
			setTouchedWall(true);
		}
		return isTouchedWall();

	}

	// draws all objects onto the canvas on the surfaceview
	protected void onDraw(Canvas canvas) {

		canvas.drawColor(Color.WHITE);
		paddle.draw(canvas);
		ball.draw(canvas);
		AI.draw(canvas);

	}

	public void isCollidingPaddle(Paddle paddle, Ball ball) {
		if (ball.getYDirection() == ball.DIRECTION_DOWN) {
			if ((ball.getY() + ball.getRadius() > paddle.getY() - paddle.height
					/ 2)
					&& (ball.getY() + ball.getRadius() < paddle.getY()
							+ paddle.height / 2)
					&& (ball.getX() + ball.getRadius() > paddle.getX()
							- paddle.width / 4)
					&& (ball.getX() + ball.getRadius() < paddle.getX()
							+ paddle.width / 4)) {
				if (!ball.isMaxDx() & !ball.isMaxDy()) {
					ball.setDy(10);
					ball.setDx(5);
					ball.toggleYDirection();
				} else {
					ball.toggleYDirection();
				}
			}

			if ((ball.getY() + ball.getRadius() > paddle.getY() - paddle.height
					/ 2)
					&& (ball.getY() + ball.getRadius() < paddle.getY()
							+ paddle.height / 2)
					&& (ball.getX() + ball.getRadius() > paddle.getX()
							+ paddle.width / 4)
					&& (ball.getX() + ball.getRadius() < paddle.getX()
							+ paddle.width / 2)) {
				if (!ball.isMaxDx() & !ball.isMaxDy()) {
					if (ball.getXDirection() == ball.DIRECTION_LEFT) {
						ball.toggleXDirection();
					}
					ball.toggleYDirection();
					ball.setDx(12);
					ball.setDy(8);
				} else {
					ball.toggleYDirection();
				}

			}

			if ((ball.getY() + ball.getRadius() > paddle.getY() - paddle.height
					/ 2)
					&& (ball.getY() + ball.getRadius() < paddle.getY()
							+ paddle.height / 2)
					&& (ball.getX() + ball.getRadius() < paddle.getX()
							- paddle.width / 4)
					&& (ball.getX() + ball.getRadius() > paddle.getX()
							- paddle.width / 2)) {
				if (!ball.isMaxDx() & !ball.isMaxDy()) {
					if (ball.getXDirection() == ball.DIRECTION_RIGHT) {
						ball.toggleXDirection();
					}
					ball.toggleYDirection();
					ball.setDx(12);
					ball.setDy(8);
				} else {
					ball.toggleYDirection();
				}
			}
		}
	}

	public boolean isCollidingAI(AI AI, Ball ball) {
		if (ball.getYDirection() == ball.DIRECTION_UP) {
			if ((ball.getY() + ball.getRadius() > AI.getY() - AI.height / 2)
					&& (ball.getY() + ball.getRadius() < AI.getY() + AI.height
							/ 2)
					&& (ball.getX() + ball.getRadius() > AI.getX() - AI.width
							/ 4)
					&& (ball.getX() + ball.getRadius() < AI.getX() + AI.width
							/ 4)) {
				if (!ball.isMaxDx() & !ball.isMaxDy()) {
					ball.setDy(12);
					ball.setDx(7);
					ball.toggleYDirection();
				} else {
					ball.toggleYDirection();
				}
				isCollision = true;
			}

			if ((ball.getY() + ball.getRadius() > AI.getY() - AI.height / 2)
					&& (ball.getY() + ball.getRadius() < AI.getY() + AI.height
							/ 2)
					&& (ball.getX() + ball.getRadius() > AI.getX() + AI.width
							/ 4)
					&& (ball.getX() + ball.getRadius() < AI.getX() + AI.width
							/ 2)) {
				if (!ball.isMaxDx() & !ball.isMaxDy()) {
					if (ball.getXDirection() == ball.DIRECTION_LEFT) {
						ball.toggleXDirection();
					}
					ball.toggleYDirection();
					ball.setDx(13);
					ball.setDy(8);
				} else {
					ball.toggleYDirection();
				}
				isCollision = true;
			}

			if ((ball.getY() + ball.getRadius() > AI.getY() - AI.height / 2)
					&& (ball.getY() + ball.getRadius() < AI.getY() + AI.height
							/ 2)
					&& (ball.getX() + ball.getRadius() < AI.getX() - AI.width
							/ 4)
					&& (ball.getX() + ball.getRadius() > AI.getX() - AI.width
							/ 2)) {
				if (!ball.isMaxDx() & !ball.isMaxDy()) {
					if (ball.getXDirection() == ball.DIRECTION_RIGHT) {
						ball.toggleXDirection();
					}
					ball.toggleYDirection();
					ball.setDx(13);
					ball.setDy(8);
				} else {
					ball.toggleYDirection();
				}
				isCollision = true;
			}
		}
		return isCollision;
	}

	public void moveAI() {

		aiBoundary();
		
			int impactdistance = ball.getY() - ball.getRadius() - AI.getY();
			int impactTime = (int) (impactdistance / (ball.getDy()
					* ball.getYDirection() * 1000));
			int targetX = (int) (ball.getX() + (ball.getDx()
					* ball.getXDirection() * 1000)
					* impactTime);

			if (targetX > AI.getX() + AI.width / 4) {
				AI.update(AI.getXDirection());
			} else if (targetX < AI.getX() - AI.width / 4) {

				AI.update(AI.getXDirection()* -1);

			} else {
				AI.setX(AI.getX());
			}
		}



	public void scoreboard() {
		if ((ball.getY() + ball.getRadius() >= getHeight())
				&& (ball.getYDirection() == ball.DIRECTION_DOWN)) {
			cpuScore = cpuScore++;
		}
		if ((ball.getY() - ball.getRadius() <= 0)
				&& (ball.getYDirection() == ball.DIRECTION_UP)) {
			userScore = userScore++;
		}
	}

	public void updateBall() {
		if ((ball.getX() + ball.getRadius() >= getWidth())
				&& (ball.getXDirection() == ball.DIRECTION_RIGHT)) {
			ball.toggleXDirection();
		}
		if ((ball.getX() - ball.getRadius() <= 0)
				&& (ball.getXDirection() == ball.DIRECTION_LEFT)) {
			ball.toggleXDirection();
		}
		if ((ball.getY() - ball.getRadius() <= 0)
				&& (ball.getYDirection() == ball.DIRECTION_UP)) {
			ball.toggleYDirection();
			setTouchedWall(true);
		}
		if ((ball.getY() + ball.getRadius() >= getHeight())
				&& (ball.getYDirection() == ball.DIRECTION_DOWN)) {
			ball.toggleYDirection();
		}

		isCollidingPaddle(paddle, ball);
		isCollidingAI(AI, ball);

		ball.update();

	}

	public boolean isTouchedWall() {
		return touchedWall;
	}

	public void setTouchedWall(boolean touchedWall) {
		this.touchedWall = touchedWall;
	}

	public boolean touchingWall() {
		if ((ball.getY() - ball.getRadius() <= 0)
				&& (ball.getYDirection() == ball.DIRECTION_UP)) {
			ball.toggleYDirection();
			setTouchedWall(true);
		} else if ((ball.getY() + ball.getRadius() >= getHeight())
				&& (ball.getYDirection() == ball.DIRECTION_DOWN)) {
			ball.toggleYDirection();
			setTouchedWall(true);
		} else {
			setTouchedWall(false);

		}
		return isTouchedWall();
	}

}
