package com.quiz.quizeegame;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.quiz.quizeegame.SnakeGame.SnakeGameActivity;
import com.quiz.quizeegame.TicTacToe.GameOptionActivity;
import com.quiz.quizeegame.databinding.ActivityGameBinding;

public class GameActivity extends AppCompatActivity {
    ActivityGameBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGameBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

      binding.button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(GameActivity.this, GameOptionActivity.class));
            }
        });

      binding.button9.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              startActivity(new Intent(GameActivity.this, SnakeGameActivity.class));
          }
      });

//      binding.button2.setOnClickListener(new View.OnClickListener() {
//          @Override
//          public void onClick(View view) {
//              startActivity(new Intent(GameActivity.this, BrickBreaker.class));
//          }
//      });

//      binding.button5.setOnClickListener(new View.OnClickListener() {
//          @Override
//          public void onClick(View view) {
//              startActivity(new Intent(GameActivity.this, LudoGameActivity.class));
//          }
//      });
    }
}