package edu.uw.multitouchlab;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Main";

    private DrawingSurfaceView view;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        view = (DrawingSurfaceView)findViewById(R.id.drawingView);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY() - getSupportActionBar().getHeight(); //closer to center...

        int action = event.getActionMasked();
        switch(action) {
            case (MotionEvent.ACTION_DOWN) : //put finger down
                //Log.v(TAG, "finger down");
                int pointerIndex = event.getActionIndex();
                int downID = event.getPointerId(pointerIndex);
                Log.v(TAG, pointerIndex + " is the current index");
                view.ball.cx = x;
                view.ball.cy = y;

                view.addTouch(downID, x, y);
                return true;
            case(MotionEvent.ACTION_POINTER_DOWN):
                Log.v(TAG, "second finger down");
                int secondPointerIndex = event.getActionIndex();
                int secondDown = event.getPointerId(secondPointerIndex);
                Log.v(TAG, "second:  " + secondPointerIndex);
                view.addTouch(secondDown, event.getX(secondPointerIndex), event.getY(secondPointerIndex) - getSupportActionBar().getHeight());
                return true;
            case (MotionEvent.ACTION_MOVE) : //move finger
                //Log.v(TAG, "finger move");
                view.ball.cx = x;
                view.ball.cy = y;
                int count = event.getPointerCount();
                for(int i = 0; i < count; i++){
                    int id = event.getPointerId(i);
                    float currX = event.getX(id);
                    float currY = event.getY(id) - getSupportActionBar().getHeight();
                    view.moveTouch(id, currX, currY);
                }


                return true;
            case (MotionEvent.ACTION_UP) : //lift finger up\
                int upPointerIndex = event.getActionIndex();
                int upID = event.getPointerId(upPointerIndex);
                view.removeTouch(upID);
                Log.v(TAG, upID + " finger was lifted");
                return true;
            case (MotionEvent.ACTION_POINTER_UP):
                int secPointerIndex = event.getActionIndex();
                int secondUp = event.getPointerId(secPointerIndex);
                Log.v(TAG, "second finger up");
                view.removeTouch(secondUp);
                return true;
            case (MotionEvent.ACTION_CANCEL) : //aborted gesture
            case (MotionEvent.ACTION_OUTSIDE) : //outside bounds
            default :
                return super.onTouchEvent(event);
        }
    }
}