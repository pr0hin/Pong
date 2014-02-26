package com.example.appui;

import com.example.appui.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;



public class MainActivity extends Activity {
	public final static String EXTRA_MESSAGE = "com.example.appui.MESSAGE";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// Restore any saved state
		super.onCreate(savedInstanceState);
		
		// Set Content view
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		
		return super.onCreateOptionsMenu(menu);
	}
	
	public void startGame(View view) {
		Intent game = new Intent(this, SIGame.class);
		startActivity(game);
		
	}
	
	
	
	public void startOptions(View view) {
		Intent options = new Intent(this, Options.class);
		startActivity(options);
	}
 

}
