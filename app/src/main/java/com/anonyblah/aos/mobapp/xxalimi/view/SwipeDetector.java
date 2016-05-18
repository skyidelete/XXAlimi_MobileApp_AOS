package com.anonyblah.aos.mobapp.xxalimi.view;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Kloc on 2016-05-13.
 */
public class SwipeDetector implements View.OnTouchListener {
    public final int VERTICAL_MIN_DISTANCE = 80;

    public enum Action{
        LR, RL, TB, BT, None
    }

    private static final int MIN_DISTANCE = 100;
    private float downX, downY, upX, upY;
    private Action mSwipeDetected = Action.None;

    public boolean swipeDetected() {
        return mSwipeDetected != Action.None;
    }

    public Action getAction() {
        return mSwipeDetected;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                downY = event.getY();
                mSwipeDetected = Action.None;
                return false;
            }
            case MotionEvent.ACTION_MOVE: {
                upY = event.getY();
                float deltaY = downY - upY;
                if (Math.abs(deltaY) > VERTICAL_MIN_DISTANCE) {
                    if (deltaY < 0) {
                        Log.i("swipe", "swipe t to b");
                        mSwipeDetected = Action.TB;
                        return true;
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            }
        }
        return false;
    }
}
