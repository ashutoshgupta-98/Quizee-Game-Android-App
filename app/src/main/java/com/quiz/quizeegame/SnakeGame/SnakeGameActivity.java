package com.quiz.quizeegame.SnakeGame;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.WindowManager;

import com.quiz.quizeegame.SnakeGame.SnakeGameLoop;
import com.quiz.quizeegame.databinding.ActivitySnakeGameBinding;

public class SnakeGameActivity extends AppCompatActivity {
    ActivitySnakeGameBinding binding;
    SnakeGameLoop snakeGameLoop;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySnakeGameBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Display display = getWindowManager().getDefaultDisplay();

        Point size = new Point();

        display.getSize(size);

        snakeGameLoop = new SnakeGameLoop(this, size);
        setContentView(snakeGameLoop);

    }
    @Override
    protected void onPause()
    {
        snakeGameLoop.onPause();
        super.onPause();
    }

    @Override
    protected void onResume()
    {
        snakeGameLoop.onResume();
        super.onResume();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
       snakeGameLoop.timer.cancel();
    }
}