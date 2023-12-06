package com.quiz.quizeegame.TicTacToe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.quiz.quizeegame.R;
import com.quiz.quizeegame.databinding.ActivityAddPlayersBinding;

public class AddPlayers extends AppCompatActivity {

    ActivityAddPlayersBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddPlayersBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
//        setContentView(R.layout.activity_add_players);

        final EditText playerOne = findViewById(R.id.playerOneName);
        final EditText playerTwo = findViewById(R.id.playerTwoName);
        final Button startGameBtn = findViewById(R.id.startGameBtn);

        startGameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String getPlayerOneName = playerOne.getText().toString();
                final String getPlayerTwoName = playerTwo.getText().toString();

                if (getPlayerOneName.isEmpty() || getPlayerTwoName.isEmpty()){
                    Toast.makeText(AddPlayers.this, "Please enter player names", Toast.LENGTH_SHORT).show();
                }else{
                    Intent intent = new Intent(AddPlayers.this, GamePlayActivity.class);
                    intent.putExtra("playerOne", getPlayerOneName);
                    intent.putExtra("playerTwo", getPlayerTwoName);
                    startActivity(intent);
                }
            }
        });
    }
}