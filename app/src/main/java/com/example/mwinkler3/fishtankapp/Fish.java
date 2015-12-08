package com.example.mwinkler3.fishtankapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.Display;
import android.view.WindowManager;

/**
 * Created by mwinkler3 on 11/15/2015.
 */
public class Fish {

    //TODO write code so that the fish starts off facing the right direction

    protected float x, y;
    private float width, height;
    private Bitmap bitmap;

    private int screenWidth, screenHeight;

    private final float SCALE = 0.1f;

    private float frameWidthScaled, frameHeightScaled;

    private final int numFramesInSpriteSheet = 6;
    //frameHeight = 70
    //frameWidth = 85.3 repeating
    private int frameWidth, frameHeight;

    //starting frame
    private int frame = 0;

    public Fish(Context context) {

        // get the image - fish initially faces right
        bitmap = BitmapFactory.decodeResource(context.getResources(), MainActivity.fishId);

        // frameWidthScaled/frameHeightScaled of a single frame (scaled)
        frameWidthScaled = (bitmap.getWidth() / numFramesInSpriteSheet) * SCALE;
        frameHeightScaled = bitmap.getHeight() * SCALE;

        // frameWidthScaled/frameHeightScaled of a single frame (not scaled)
        frameWidth = (int) (bitmap.getWidth() / numFramesInSpriteSheet);
        frameHeight = (int) bitmap.getHeight();

        // figure out the screen width
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screenWidth = size.x;
        screenHeight = size.y;

        // start in the center
        x = screenWidth/2;
        y = screenHeight/2;
    }

    public void doDraw(Canvas canvas) {
        // first Rect uses sprite sheet coordinates (not scaled)
        // second Rect uses screen coordinated (scaled)
        // first Rect --> drawn into second Rect
        canvas.drawBitmap(bitmap,
                new Rect(frameWidth * frame,
                        0,
                        frameWidth * frame + frameWidth,
                        frameHeight),
                new Rect((int) (x - frameWidthScaled /2), (int) (y - frameHeightScaled /2),
                        (int) (x + frameWidthScaled /2), (int) (y + frameHeightScaled /2)),
                null);
    }

    private final double STEP = 0.25;       // time (in s) between animation steps
    private final double ANIM_TIME = 5.0;   // how long to do one type of animation
    double step = 0.0;                      // timer for next animation step
    double timer = 0.0;                     // timer for next type of animation

    // what can our sprite do?
    private enum State { WALKING, JUMPING, FALLING };
    private State state = State.WALKING;    // start in WALKING state
    private int [] walking = {0, 1};        // walking = frame[0], frame[1], frame[0], ...
    private int [] jumping = {2, 3};        // jumping = frame[2], frame[3], frame[2], ...
    private int [] falling = {4, 5};        // falling = frame[4], frame[5], frame[4], ...
    private int which = 0;  // which index in a particular animation

    public void doUpdate(double elapsed, float touchX ,float touchY) {

        timer = timer + elapsed;        // how much time has elapsed?
        if (timer > ANIM_TIME) {        // time to switch animations
            if (state == State.WALKING) {
                state = State.JUMPING;
            } else if (state == State.JUMPING) {
                state = State.FALLING;
            } else {
                state = State.WALKING;
            }

            timer = 0.0;                // reset timer
        }

        step = step + elapsed;          // how much time has elapsed?
        if (step > STEP) {              // time to move to the next frame in the animation
            if (state == State.WALKING) {
                which = (which + 1) % walking.length;
                frame = walking[which];
            }

            if (state == State.JUMPING) {
                which = (which + 1) % jumping.length;
                frame = jumping[which];
            }

            if (state == State.FALLING) {
                which = (which + 1) % falling.length;
                frame = falling[which];
            }

            step = 0.0;                 // reset timer
        }

    }

    /*public void doUpdate(double elapsed, float touchX ,float touchY) {



        //old update code
        // easing based on touch point
        // x = (float) (x + ((touchX - x) * elapsed *2));
        //y = (float) (y + ((touchY - y) * elapsed * 2));
    }*/

}
