package com.quiz.quizeegame.BrickBreaker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.quiz.quizeegame.R;

public class GameOver extends AppCompatActivity {

    TextView tvPoints;
    ImageView ivNewHighest;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_over);
        ivNewHighest = findViewById(R.id.ivNewHeighest);
        tvPoints = findViewById(R.id.tvPoints);
        int points = getIntent().getExtras().getInt("points");
        if (points == 240){
            ivNewHighest.setVisibility(View.VISIBLE);
        }
        tvPoints.setText("" + points);
    }
    public void restart(View view){
        Intent intent = new Intent(GameOver.this, BrickBreaker.class);
        startActivity(intent);
        finish();
    }

    public void  exit (View view){
        finish();
    }
}
