package com.quiz.quizeegame.BrickBreaker;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.quiz.quizeegame.databinding.ActivityBrickBreakerBinding;

public class BrickBreaker extends AppCompatActivity {

    ActivityBrickBreakerBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBrickBreakerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }
    public void startGame(View view){
        GameView gameView = new GameView(this);
        setContentView(gameView);

    }
}