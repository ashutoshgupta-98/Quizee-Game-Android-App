package com.quiz.quizeegame.SnakeGame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

import com.quiz.quizeegame.R;

import java.util.Random;

public class Lemon {
    private Point positionOfLemon = new Point();
    private Bitmap lemonBitmap;
    private int BLOCK_SIZE;
    private int Num_BLOCK_WIDE;
    private int NUM_BLOCK_HIGH;

    Lemon(Context context , int NUM_BLOCK_HIGH , int NUM_BLOCK_WIDE , int BLOCK_SIZE)
    {
        this.BLOCK_SIZE = BLOCK_SIZE;
        this.NUM_BLOCK_HIGH = NUM_BLOCK_HIGH;
        this.Num_BLOCK_WIDE = NUM_BLOCK_WIDE;

        lemonBitmap = BitmapFactory.decodeResource(context.getResources() , R.drawable.lemon);
        lemonBitmap = Bitmap.createScaledBitmap(lemonBitmap , BLOCK_SIZE , BLOCK_SIZE , true);
    }
   public void setLemonPosition()
    {
        Random random = new Random();
        positionOfLemon.x = random.nextInt(Num_BLOCK_WIDE);
        positionOfLemon.y = random.nextInt(NUM_BLOCK_HIGH);
    }

    public Point getPositionofLemon()
    {
        return positionOfLemon;
    }
    void drawLemon (Canvas canvas , Paint paint)
    {
        canvas.drawBitmap(lemonBitmap , positionOfLemon.x * BLOCK_SIZE ,
                positionOfLemon.y * BLOCK_SIZE , paint);
    }
}
