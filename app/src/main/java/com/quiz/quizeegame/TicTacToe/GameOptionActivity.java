package com.quiz.quizeegame.TicTacToe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.quiz.quizeegame.databinding.ActivityGameOptionBinding;

public class GameOptionActivity extends AppCompatActivity {

    ActivityGameOptionBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGameOptionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

//        binding.singleplayerbtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {

//                startActivity(new Intent(GameOptionActivity.this, ComputerGameActivity.class));

//            }
//        });
        binding.multiplayerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            startActivity(new Intent(GameOptionActivity.this, MultiPlayerGameActivity.class));
            }
        });

    }
}