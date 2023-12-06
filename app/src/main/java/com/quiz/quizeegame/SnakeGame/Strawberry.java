package com.quiz.quizeegame.SnakeGame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

import com.quiz.quizeegame.R;

import java.util.Random;

public class Strawberry {
    private Point positionOfStrawberry = new Point();
    private Bitmap strawberryBitmap;
    private int BLOCK_SIZE;
    private int Num_BLOCK_WIDE;
    private int NUM_BLOCK_HIGH;

    Strawberry(Context context , int NUM_BLOCK_HIGH , int NUM_BLOCK_WIDE , int BLOCK_SIZE)
    {
        this.BLOCK_SIZE = BLOCK_SIZE;
        this.NUM_BLOCK_HIGH = NUM_BLOCK_HIGH;
        this.Num_BLOCK_WIDE = NUM_BLOCK_WIDE;

        strawberryBitmap = BitmapFactory.decodeResource(context.getResources() , R.drawable.strawberryui);
        strawberryBitmap = Bitmap.createScaledBitmap(strawberryBitmap , BLOCK_SIZE , BLOCK_SIZE , true);
    }
    public void setStrawberryPosition()
    {
        Random random = new Random();
        positionOfStrawberry.x = random.nextInt(Num_BLOCK_WIDE);
        positionOfStrawberry.y = random.nextInt(NUM_BLOCK_HIGH);
    }

    public Point getPositionOfStrawberry()
    {
        return positionOfStrawberry;
    }
    void drawStrawberry (Canvas canvas , Paint paint)
    {
        canvas.drawBitmap(strawberryBitmap , positionOfStrawberry.x * BLOCK_SIZE ,
                positionOfStrawberry.y * BLOCK_SIZE , paint);
    }
}
