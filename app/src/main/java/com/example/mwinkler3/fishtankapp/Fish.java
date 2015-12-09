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

    private Context context;

    public Fish(Context context) {

        this.context = context;

        // get the image - fish initially faces right
        if(MainActivity.fishColor.equals("Green")) {
            bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.green_swim_right);
        }
        else if (MainActivity.fishColor.equals("Yellow")) {
            bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.yellow_swim_right);
        }
        else if (MainActivity.fishColor.equals("Orange")) {
            bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.gold_swim_right);
        }
        else {
            bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.tuna_swim_right);
        }

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
    double step = 0.0;                      // timer for next animation step

    // what can our sprite do?
    private int which = 0;  // which index in a particular animation

    public void doUpdate(double elapsed, float foodX ,float foodY) {

        //make the fish change the direction it's facing depending on
        //which way it's swimming
        if(MainActivity.fishColor.equals("Green")){
            if ((foodX - x > 0))
                bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.green_swim_right);

            else
                bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.green_swim_left);

        }
        else if (MainActivity.fishColor.equals("Yellow")){
            if ((foodX - x > 0))
                bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.yellow_swim_right);

            else
                bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.yellow_swim_left);
        }
        else if (MainActivity.fishColor.equals("Orange")){
            if ((foodX - x > 0))
                bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.gold_swim_right);

            else
                bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.gold_swim_left);
        }
        else {
            if ((foodX - x > 0))
                bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.tuna_swim_right);

            else
                bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.tuna_swim_left);
        }


        step = step + elapsed;          // how much time has elapsed?
        if (step > STEP) {              // time to move to the next frame in the animation
            if (frame > 4){
                frame = 0;
            }
            else{
                step++;
            }

            step = 0.0;                 // reset timer
        }

        x = (float) (x + ((foodX - x) * elapsed *2));
        y = (float) (y + ((foodY - y) * elapsed * 2));


    }


}
