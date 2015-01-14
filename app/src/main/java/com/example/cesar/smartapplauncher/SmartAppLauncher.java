package com.example.cesar.smartapplauncher;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.support.v4.view.GestureDetectorCompat;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

/**
 * Created by Cesar on 1/7/2015.
 */
public class SmartAppLauncher extends Service {

    private WindowManager windowManager;
    private ImageView chatHead;
    private int initialX, initialY;
    private float initialTouchX, initialTouchY;
    private WindowManager.LayoutParams paramsF;
    private boolean detectedLongPress;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        chatHead = new ImageView(this);
        chatHead.setImageResource(R.drawable.ic_launcher);

        final WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_TOAST,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);

        params.gravity = Gravity.TOP | Gravity.LEFT;
        params.x = 0;
        params.y = 200;

        windowManager.addView(chatHead, params);
        paramsF = params;

        try {

            final GestureDetectorCompat mDetector = new GestureDetectorCompat(this, new MyGestureListener());

            chatHead.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    mDetector.onTouchEvent(event);
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_UP:
                            detectedLongPress = false;
                            paramsF.alpha = 100;
                            Log.d("Action", "ACTION_UP");
                            break;
                        case MotionEvent.ACTION_MOVE:
                            if (detectedLongPress == true) {
                                Log.d("Action", "event.getRaw: " + event.getRawX() + "," + event.getRawY());
                                Log.d("Action", "event.get: " + event.getX() + "," + event.getY());
                                //Log.d("Action", "     " + event.getX() + "," + event.getY());
                                paramsF.x = initialX + (int) (event.getRawX() - initialTouchX);
                                paramsF.y = initialY + (int) (event.getRawY() - initialTouchY);
                                Log.d("Action", "params: " + params.x + "," + params.y);
                                Log.d("Action", "paramsF: " + paramsF.x + "," + paramsF.y);
                                windowManager.updateViewLayout(chatHead,paramsF);
                            }
                            break;
                    }
                    return false;
                }
            });
        } catch (Exception e) {

        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if(chatHead!=null) {
            windowManager.removeView(chatHead);
        }
    }

    class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
        private static final String DEBUG_TAG = "Gestures";

        @Override
        public void onLongPress(MotionEvent e) {
            detectedLongPress = true;
            paramsF.dimAmount = (float) .5;
            initialX = paramsF.x;
            initialY = paramsF.y;
            initialTouchX = e.getRawX();
            initialTouchY = e.getRawY();
            Log.d("Action", "initial: " + initialX + "," + initialY);
            Log.d("Action", "initialTouch: " + initialTouchX + "," + initialTouchY);
            

            //Android.app.startDrag(null, new View.DragShadowBuilder(chatHead), null, 0);
            //chatHead.startDrag(null, new View.DragShadowBuilder(chatHead), null, 0);


            return;
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            SmartAppLauncher.this.stopSelf();
            return true;
        }

    }

}
