package com.quiz.quizeegame.SnakeGame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.quiz.quizeegame.R;

public class SnakeGameLoop extends SurfaceView implements Runnable {
    private Thread thread;
    private Boolean isThreadRunning;
    private int score = 0;
    private int time = 0;
    private final int NUM_BLOCK_WIDE = 50;
    private int blockSize;
    private int NUM_BLOCK_HIGH;
    private Bitmap backgroundImage;
    private SurfaceHolder surfaceHolder;
    private long nextFrameTime;
    private Boolean paused = true;
    private Canvas canvas;
    private Paint paint = new Paint();
    private GestureDetector gestureDetector;
    private float x1 , x2 , y1 , y2;
    private float abs_x , abs_y;
    static final int MIN_DISTANCE = 100;
    private Apple apple;
    private Lemon lemon;
    private Grapes grapes;
    private Caterpillar caterpillar;
    private Worm worm;
    private Strawberry strawberry;
    private Snake snake;
    CountDownTimer timer;
    MediaPlayer snakeSound;
    MediaPlayer snakeOgg;

    private InterstitialAd mInterstitialAd;

    SnakeGameLoop(Context context , Point screenSize)
    {
        super(context);
        blockSize = screenSize.x / NUM_BLOCK_WIDE;
        NUM_BLOCK_HIGH = screenSize.y / blockSize;
        backgroundImage = BitmapFactory.decodeResource(context.getResources() , R.drawable.grasses);
        backgroundImage = Bitmap.createScaledBitmap(backgroundImage , screenSize.x , screenSize.y , false);

        apple = new Apple(context , NUM_BLOCK_HIGH , NUM_BLOCK_WIDE , blockSize);
        lemon = new Lemon(context , NUM_BLOCK_HIGH , NUM_BLOCK_WIDE , blockSize);
        grapes = new Grapes(context , NUM_BLOCK_HIGH , NUM_BLOCK_WIDE , blockSize);
        caterpillar = new Caterpillar(context , NUM_BLOCK_HIGH , NUM_BLOCK_WIDE , blockSize);
        worm = new Worm(context , NUM_BLOCK_HIGH , NUM_BLOCK_WIDE , blockSize);
        strawberry = new Strawberry(context , NUM_BLOCK_HIGH , NUM_BLOCK_WIDE , blockSize);
        snake = new Snake(context , new Point(NUM_BLOCK_WIDE , NUM_BLOCK_HIGH) , blockSize);

        gestureDetector = new GestureDetector(getContext() , getGestureListener());

        surfaceHolder = getHolder();
        startNewGame();
        snakeSound = MediaPlayer.create(getContext(),R.raw.eatso);
        snakeOgg = MediaPlayer.create(getContext(),R.raw.snakesound);

    }

    public void resetTimer() {
        timer = new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                String.valueOf(millisUntilFinished / 1000);
                time++;
            }

            @Override
            public void onFinish() {
                paused= true;
                coin();
                startNewGame();
                time = 0;
                Toast.makeText(getContext().getApplicationContext(), "You Collect", Toast.LENGTH_SHORT).show();


                AdRequest adRequest = new AdRequest.Builder().build();

                InterstitialAd.load(getContext(),"ca-app-pub-6301220023928445/9828564304", adRequest,
                        new InterstitialAdLoadCallback() {
                            @Override
                            public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                                // The mInterstitialAd reference will be null until
                                // an ad is loaded.
                                mInterstitialAd = interstitialAd;
                                if (mInterstitialAd != null){
                                    mInterstitialAd.show(null);

                                }
//                        Log.i(TAG, "onAdLoaded");
//                        Toast.makeText(SnakeGameActivity.this, "Ad is loaded.", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                                // Handle the error
//                        Log.i(TAG, loadAdError.getMessage());
                                mInterstitialAd = null;
                            }
                        });
            }
        };
    }

    void startNewGame()
    {

        score = 0;
        //Apple position....
        apple.setApplePosition();
        //Lemon position...
        lemon.setLemonPosition();
        // Grapes position...
        grapes.setGrapesPosition();
        // Caterpillar position...
        caterpillar.setCaterpillarPosition();
        // Worm position...
        worm.setWormPosition();
        //Strawberry position...
        strawberry.setStrawberryPosition();
        // move Snake...
        snake.moveSnake();

        nextFrameTime = System.currentTimeMillis();
    }

    @Override
    public void run() {
        while(isThreadRunning)
        {
            snakeOgg.start();
            if (updateRequired())
            {
                if (!paused)
                {
                    update();
                }
                draw();
            }
        }
    }
    private Boolean updateRequired()
    {
        if (System.currentTimeMillis() > nextFrameTime)
        {
            nextFrameTime += 100;
            return true;
        }
        return false;
    }

    private void update()
    {
     snake.moveSnake();

     if (snake.isDead())
     {
         paused = true;
     }

      if (snake.haveDinner(apple.getPositionOfApple()))
      {
          score++;
          apple.setApplePosition();
          snakeSound.start();
      }
      if (snake.haveDinner(lemon.getPositionofLemon()))
      {
          score++;
          lemon.setLemonPosition();
          snakeSound.start();
      }
      if (snake.haveDinner(grapes.getPositionofGrapes()))
      {
          score++;
          grapes.setGrapesPosition();
          snakeSound.start();
      }
      if (snake.haveDinner(caterpillar.getPositionOfCaterpillar()))
      {
          score++;
          caterpillar.setCaterpillarPosition();
          snakeSound.start();
      }
        if (snake.haveDinner(strawberry.getPositionOfStrawberry()))
        {
            score++;
            strawberry.setStrawberryPosition();
            snakeSound.start();
        }
      if (snake.haveDinner(worm.getPositionOfWorm()))
      {
          score++;
          worm.setWormPosition();
          snakeSound.start();
      }

    }
    private void draw()
    {
        if (surfaceHolder.getSurface().isValid())
        {
           canvas = surfaceHolder.lockCanvas();
           canvas.drawBitmap(backgroundImage ,0, 0, paint);
           paint.setColor(Color.BLACK);
           paint.setTextSize(120);
           canvas.drawText("" + score , 40, 120 , paint);
           canvas.drawText("" + time, 2020,120, paint);
           if (!paused)
           {
               //Draw snake
               //Draw Apple //Draw Lemon // Draw Grapes
               //Draw Caterpillar // Draw Worm
               apple.drawApple(canvas , paint);
               lemon.drawLemon(canvas , paint);
               grapes.drawGrapes(canvas , paint);
               caterpillar.drawCaterpillar(canvas , paint);
               worm.drawWorm(canvas , paint);
               strawberry.drawStrawberry(canvas , paint);
               snake.drawSnake(canvas , paint);
           }else{
               paint.setTextSize(200);
               paint.setColor(Color.BLACK);
               canvas.drawText("Tap to Play...",550, 550, paint);
           }
           surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        gestureDetector.onTouchEvent(event);

        return true;
    }

    private GestureDetector.OnGestureListener getGestureListener() {
      return new GestureDetector.OnGestureListener() {
          @Override
          public boolean onDown(MotionEvent motionEvent) {
              return false;
          }

          @Override
          public void onShowPress(MotionEvent motionEvent) {

          }

          @Override
          public boolean onSingleTapUp(MotionEvent motionEvent) {
              if (paused)
              {
                  paused = false;
                  score = 0;
                  snake.reset();
                  resetTimer();
                  if(timer != null)
                      timer.cancel();
                  timer.start();

              }
              return false;
          }

          @Override
          public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
              return false;
          }

          @Override
          public void onLongPress(MotionEvent motionEvent) {

          }

          @Override
          public boolean onFling(MotionEvent downEvent, MotionEvent upEvent, float v, float v1) {
              x1 = downEvent.getX();
              y1 = downEvent.getY();

              x2 = upEvent.getX();
              y2 = upEvent.getY();

              abs_x = Math.abs(x2 - x1);
              abs_y = Math.abs(y2 - y1);

              if (abs_y > MIN_DISTANCE || abs_x > MIN_DISTANCE)
              {
                  if (abs_y > abs_x)
                  {
                    if (snake.getMovingDirection() == Heading.RIGHT || snake.getMovingDirection() == Heading.LEFT)
                    {
                        if (y1 > y2)
                        {
                            snake.setMovingDirection(Heading.UP);
                        }else
                        {
                            snake.setMovingDirection(Heading.DOWN);
                        }
                    }
                  }else if (abs_x > MIN_DISTANCE)
                  {
                      if (snake.getMovingDirection() == Heading.UP || snake.getMovingDirection() == Heading.DOWN)
                      {
                          if (x1 > x2)
                          {
                              snake.setMovingDirection(Heading.LEFT);
                          }else
                          {
                              snake.setMovingDirection(Heading.RIGHT);
                          }
                      }
                  }
              }
              return false;
          }
      };
    }

    public void onResume()
    {
        isThreadRunning = true;
        thread = new Thread(this);
        thread.start();
    }

    public void onPause()
    {
        isThreadRunning = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void coin(){
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        database.collection("users")
                .document(FirebaseAuth.getInstance().getUid())
                .update("coins", FieldValue.increment(score));
    }


}
