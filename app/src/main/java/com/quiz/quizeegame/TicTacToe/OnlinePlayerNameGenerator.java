package com.quiz.quizeegame.TicTacToe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.quiz.quizeegame.R;
import com.quiz.quizeegame.databinding.ActivityOnlinePlayerNameGeneratorBinding;

public class OnlinePlayerNameGenerator extends AppCompatActivity {

    ActivityOnlinePlayerNameGeneratorBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOnlinePlayerNameGeneratorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        final EditText playerNameEt = findViewById(R.id.playerNameEt);
        final AppCompatButton startGameBtn = findViewById(R.id.startGameBtn);

        startGameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String getPlayerName = playerNameEt.getText().toString();

                if (getPlayerName.isEmpty()){
                    Toast.makeText(OnlinePlayerNameGenerator.this, "Please enter player name", Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent intent = new Intent(OnlinePlayerNameGenerator.this, OnlineGamePlayActivity.class);
                    intent.putExtra("playerName", getPlayerName);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}