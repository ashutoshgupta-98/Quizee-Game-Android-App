package com.quiz.quizeegame.SnakeGame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

import com.quiz.quizeegame.R;

import java.util.Random;

public class Worm {
    private Point positionOfWorm = new Point();
    private Bitmap wormBitmap;
    private int BLOCK_SIZE;
    private int Num_BLOCK_WIDE;
    private int NUM_BLOCK_HIGH;

    Worm(Context context , int NUM_BLOCK_HIGH , int NUM_BLOCK_WIDE , int BLOCK_SIZE)
    {
        this.BLOCK_SIZE = BLOCK_SIZE;
        this.NUM_BLOCK_HIGH = NUM_BLOCK_HIGH;
        this.Num_BLOCK_WIDE = NUM_BLOCK_WIDE;

        wormBitmap = BitmapFactory.decodeResource(context.getResources() , R.drawable.worm);
        wormBitmap = Bitmap.createScaledBitmap(wormBitmap , BLOCK_SIZE , BLOCK_SIZE , true);
    }
    public void setWormPosition()
    {
        Random random = new Random();
        positionOfWorm.x = random.nextInt(Num_BLOCK_WIDE);
        positionOfWorm.y = random.nextInt(NUM_BLOCK_HIGH);
    }

    public Point getPositionOfWorm()
    {
        return positionOfWorm;
    }
    void drawWorm (Canvas canvas , Paint paint)
    {
        canvas.drawBitmap(wormBitmap , positionOfWorm.x * BLOCK_SIZE ,
                positionOfWorm.y * BLOCK_SIZE , paint);
    }
}
