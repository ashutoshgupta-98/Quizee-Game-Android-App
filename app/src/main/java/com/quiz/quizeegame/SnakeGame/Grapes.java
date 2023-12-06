package com.quiz.quizeegame.SnakeGame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

import com.quiz.quizeegame.R;

import java.util.Random;

public class Grapes {
    private Point positionOfGrapes = new Point();
    private Bitmap grapesBitmap;
    private int BLOCK_SIZE;
    private int Num_BLOCK_WIDE;
    private int NUM_BLOCK_HIGH;

    Grapes(Context context , int NUM_BLOCK_HIGH , int NUM_BLOCK_WIDE , int BLOCK_SIZE)
    {
        this.BLOCK_SIZE = BLOCK_SIZE;
        this.NUM_BLOCK_HIGH = NUM_BLOCK_HIGH;
        this.Num_BLOCK_WIDE = NUM_BLOCK_WIDE;

        grapesBitmap = BitmapFactory.decodeResource(context.getResources() , R.drawable.grapes);
        grapesBitmap = Bitmap.createScaledBitmap(grapesBitmap , BLOCK_SIZE , BLOCK_SIZE , true);
    }
    public void setGrapesPosition()
    {
        Random random = new Random();
        positionOfGrapes.x = random.nextInt(Num_BLOCK_WIDE);
        positionOfGrapes.y = random.nextInt(NUM_BLOCK_HIGH);
    }

    public Point getPositionofGrapes()
    {
        return positionOfGrapes;
    }
    void drawGrapes (Canvas canvas , Paint paint)
    {
        canvas.drawBitmap(grapesBitmap , positionOfGrapes.x * BLOCK_SIZE ,
                positionOfGrapes.y * BLOCK_SIZE , paint);
    }
}
