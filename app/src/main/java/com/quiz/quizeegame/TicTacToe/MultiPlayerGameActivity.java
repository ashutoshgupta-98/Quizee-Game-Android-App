package com.quiz.quizeegame.TicTacToe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.quiz.quizeegame.databinding.ActivityMultiPlayerGameBinding;

public class MultiPlayerGameActivity extends AppCompatActivity {

    ActivityMultiPlayerGameBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMultiPlayerGameBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.onlineBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(MultiPlayerGameActivity.this, OnlinePlayerNameGenerator.class));

            }
        });
        binding.offlineBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MultiPlayerGameActivity.this, AddPlayers.class));
            }
        });
    }
}