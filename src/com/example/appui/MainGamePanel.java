/*
 *Contains methods for game events
 *@author Rohin Patel (unless otherwise indicated)
 * Elements taken from tutorial Obviam.net
 */

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
import android.media.SoundPool;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.gamemodels.AbstractModel;
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
	private int balldx;
	private int balldy;
	private SoundPool sounds;
	private int scoreboard;

	public MainGamePanel(Context context) {
		super(context);
		// adding the callback (this) to the surface holder to intercept events
		getHolder().addCallback(this);

		// create AI to move around
		AI = new AI(BitmapFactory.decodeResource(getResources(),
				R.drawable.paddle), 350, 100, 10);
		AI.setHeight(AI.getHeight()*-1);
		AI.setWidth(AI.getWidth()*-1);
		// create paddle to move around
		paddle = new Paddle(BitmapFactory.decodeResource(getResources(),
				R.drawable.paddle2), 350, 1100);

		// create ball
		ball = new Ball(BitmapFactory.decodeResource(getResources(),
				R.drawable.ball), 250, 500, 45);
		ball.setXDirection(ball.DIRECTION_LEFT);
		long randomy = Math.round(Math.random());
		if (randomy == 0) {
			ball.setYDirection(ball.DIRECTION_DOWN);
		} else if (randomy == 1) {
			ball.setYDirection(ball.DIRECTION_UP);
		}
		
		SoundPool sounds = new SoundPool(1, 3, 0);
		scoreboard = sounds.load(context, R.raw.score, 1);
		sounds.play(scoreboard, 1, 1, 0, 0, 1);
		
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
		if (event.getX() + paddle.getWidth() / 2 >= getWidth()) {

			paddle.setX(getWidth() - paddle.getWidth() / 2);
			setTouchedWall(true);
		} else if (event.getX() - paddle.getWidth() / 2 <= 0) {
			paddle.setX(0 + paddle.getWidth() / 2);
			setTouchedWall(true);
		}
		return isTouchedWall();
	}

	// check whether the AI paddle has touched the wall
	public boolean aiBoundary() {
		setTouchedWall(false);
		if (AI.getX() + AI.getWidth() / 2 >= getWidth()) {
			AI.setX(getWidth() - AI.getWidth() / 2);
			setTouchedWall(true);
		} else if (AI.getX() - AI.getWidth() / 2 <= 0) {
			AI.setX(0 + AI.getWidth() / 2);
			setTouchedWall(true);
		}
		return isTouchedWall();

	}

	// draws all objects onto the canvas on the surfaceview
	protected void onDraw(Canvas canvas) {

		canvas.drawColor(Color.rgb(236,240,241));
		paddle.draw(canvas);
		ball.draw(canvas);
		AI.draw(canvas);

	}

	// checks if ball collides with AI or paddle
	public void isColliding(AbstractModel abm, Ball ball) {
		if (abm.getClass().isAssignableFrom(Paddle.class)) {
			if ((ball.getY() + ball.getRadius() > abm.getY() - abm.getHeight()
					/ 2)
					&& (ball.getY() + ball.getRadius() < abm.getY()
							+ abm.getHeight() / 2)
					&& (ball.getX() + ball.getRadius() > abm.getX()
							- abm.getWidth() / 4)
					&& (ball.getX() + ball.getRadius() < abm.getX()
							+ abm.getWidth() / 4)) {

				ball.setAngle(45);
				ball.toggleYDirection();
			}

			if ((ball.getY() + ball.getRadius() > abm.getY() - abm.getHeight()
					/ 2)
					&& (ball.getY() + ball.getRadius() < abm.getY()
							+ abm.getHeight() / 2)
					&& (ball.getX() + ball.getRadius() > abm.getX()
							+ abm.getWidth() / 4)
					&& (ball.getX() + ball.getRadius() < paddle.getX()
							+ abm.getWidth() / 2)) {

				if (ball.getXDirection() == ball.DIRECTION_LEFT) {
					ball.toggleXDirection();
				}
				ball.toggleYDirection();
				ball.setAngle(65);
			}

			if ((ball.getY() + ball.getRadius() > abm.getY() - abm.getHeight()
					/ 2)
					&& (ball.getY() + ball.getRadius() < abm.getY()
							+ abm.getHeight() / 2)
					&& (ball.getX() + ball.getRadius() < abm.getX()
							- abm.getWidth() / 4)
					&& (ball.getX() + ball.getRadius() > abm.getX()
							- abm.getWidth() / 2)) {

				if (ball.getXDirection() == ball.DIRECTION_RIGHT) {
					ball.toggleXDirection();
				}
				ball.toggleYDirection();
				ball.setAngle(65);
			}
		} else
		{
			if ((ball.getY() - ball.getRadius() < abm.getY() - abm.getHeight()
					/ 2)
					&& (ball.getY() - ball.getRadius() > abm.getY()
							+ abm.getHeight() / 2)
					&& (ball.getX() - ball.getRadius() < abm.getX()
							- abm.getWidth() / 4)
					&& (ball.getX() - ball.getRadius() > abm.getX()
							+ abm.getWidth() / 4)) {

				ball.setAngle(45);
				ball.toggleYDirection();
			}

			if ((ball.getY() - ball.getRadius() < abm.getY() - abm.getHeight()
					/ 2)
					&& (ball.getY() - ball.getRadius() > abm.getY()
							+ abm.getHeight() / 2)
					&& (ball.getX() - ball.getRadius() < abm.getX()
							+ abm.getWidth() / 4)
					&& (ball.getX() - ball.getRadius() > abm.getX()
							+ abm.getWidth() / 2)) {

				if (ball.getXDirection() == ball.DIRECTION_LEFT) {
					ball.toggleXDirection();
				}
				ball.toggleYDirection();
				ball.setAngle(65);
			}

			if ((ball.getY() - ball.getRadius() < abm.getY() - abm.getHeight()
					/ 2)
					&& (ball.getY() - ball.getRadius() > abm.getY()
							+ abm.getHeight() / 2)
					&& (ball.getX() - ball.getRadius() < abm.getX()
							- abm.getWidth() / 4)
					&& (ball.getX() - ball.getRadius() > abm.getX()
							- abm.getWidth() / 2)) {

				if (ball.getXDirection() == ball.DIRECTION_RIGHT) {
					ball.toggleXDirection();
				}
				ball.toggleYDirection();
				ball.setAngle(65);
			}
			
		}
	
	}


	
	/* @author Matt Campbell
	 * Template taken from StackOverflow Question# 4577814. 
	 * Calculates predicted location of ball
	 */
	public void moveAI() {

		aiBoundary();

		int impactdistance = ball.getY() - ball.getRadius() - AI.getY();
		int impactTime = (int) (impactdistance / (ball.getDy()
				* ball.getYDirection()*1000));
		int targetX = (int) (ball.getX() + (ball.getDx() * ball.getXDirection() * 1000)
				* impactTime);
		if (targetX == AI.getX() ) {
			AI.update(ball.getXDirection());
		}
		if (targetX > AI.getX()) {
			AI.update(1);
		}
		if (targetX < AI.getX()) {

			AI.update(-1);

		}
	}

	// Updates score as ball hits wall behind AI or the player
	public void scoreboard() {
		
		if ((ball.getY() + ball.getRadius() >= getHeight())
				&& (ball.getYDirection() == ball.DIRECTION_DOWN)) {
			paddle.setScore();
			
		}
		if ((ball.getY() - ball.getRadius() <= 0)
				&& (ball.getYDirection() == ball.DIRECTION_UP)) {
			AI.setScore();
		}
	}
	
	//Changes direction of the ball depending on which wall the ball has hit
	public void update() {
		
	
		ball.update();
		moveAI();
		touchingWall();
		scoreboard();
		isColliding(paddle, ball);
		isColliding(AI, ball);



	}

	public boolean isTouchedWall() {
		return touchedWall;
	}

	public void setTouchedWall(boolean touchedWall) {
		this.touchedWall = touchedWall;
	}

	public void touchingWall() {
		if ((ball.getY() - ball.getRadius() <= 0)
				&& (ball.getYDirection() == ball.DIRECTION_UP)) {

			ball.toggleYDirection();
		} else if ((ball.getY() + ball.getRadius() >= getHeight())
				&& (ball.getYDirection() == ball.DIRECTION_DOWN)) {
			ball.toggleYDirection();
		}
		else if ((ball.getX() + ball.getRadius() >= getWidth())
					&& (ball.getXDirection() == ball.DIRECTION_RIGHT)) {
			ball.toggleXDirection();
			}
		else if ((ball.getX() - ball.getRadius() <= 0)
					&& (ball.getXDirection() == ball.DIRECTION_LEFT)) {
			ball.toggleXDirection();
			}

	}

}
