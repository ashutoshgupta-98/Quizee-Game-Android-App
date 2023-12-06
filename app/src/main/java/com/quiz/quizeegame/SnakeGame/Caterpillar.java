package com.quiz.quizeegame.SnakeGame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

import com.quiz.quizeegame.R;

import java.util.Random;

public class Caterpillar {
    private Point positionOfCaterpillar = new Point();
    private Bitmap caterpillarBitmap;
    private int BLOCK_SIZE;
    private int Num_BLOCK_WIDE;
    private int NUM_BLOCK_HIGH;

    Caterpillar(Context context , int NUM_BLOCK_HIGH , int NUM_BLOCK_WIDE , int BLOCK_SIZE)
    {
        this.BLOCK_SIZE = BLOCK_SIZE;
        this.NUM_BLOCK_HIGH = NUM_BLOCK_HIGH;
        this.Num_BLOCK_WIDE = NUM_BLOCK_WIDE;

        caterpillarBitmap = BitmapFactory.decodeResource(context.getResources() , R.drawable.caterpillar);
        caterpillarBitmap = Bitmap.createScaledBitmap(caterpillarBitmap , BLOCK_SIZE , BLOCK_SIZE , true);
    }
    public void setCaterpillarPosition()
    {
        Random random = new Random();
        positionOfCaterpillar.x = random.nextInt(Num_BLOCK_WIDE);
        positionOfCaterpillar.y = random.nextInt(NUM_BLOCK_HIGH);
    }

    public Point getPositionOfCaterpillar()
    {
        return positionOfCaterpillar;
    }
    void drawCaterpillar (Canvas canvas , Paint paint)
    {
        canvas.drawBitmap(caterpillarBitmap , positionOfCaterpillar.x * BLOCK_SIZE ,
                positionOfCaterpillar.y * BLOCK_SIZE , paint);
    }
}
