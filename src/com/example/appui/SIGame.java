package com.example.appui;





import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;


public class SIGame extends Activity  {
	
	private static final String TAG = SIGame.class.getSimpleName();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requesting to turn the title OFF
        requestWindowFeature(Window.FEATURE_NO_TITLE);
       
        //Make the Window fullscreen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        

        
        setContentView(new MainGamePanel(this));
       

    
    
    }
    
    @Override
    protected void onStart() {
    	super.onStart();
    }



    @Override
    protected void onPause() {
    super.onPause();	
    
    }
    
   @Override 
    protected void onDestroy() {
    	Log.d(TAG, "Destroying...");
    	super.onDestroy();
    }
    @Override
    protected void onStop() {
    	Log.d(TAG, "Stopping...");
    	super.onStop();
    }
    
    @Override
    protected void onResume() {
    	super.onResume();
    }
    
    @Override
    protected void onRestart() {
    	super.onRestart();
    }
}
