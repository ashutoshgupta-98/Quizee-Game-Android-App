package com.quiz.quizeegame;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.quiz.quizeegame.databinding.ActivityResultBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class ResultActivity extends AppCompatActivity {

    ActivityResultBinding binding;
    int POINTS = 2;
    MediaPlayer coinSounds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityResultBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        coinSounds = MediaPlayer.create(ResultActivity.this,R.raw.resultshowsound);
        coinSounds.start();

        int correctAnswers = getIntent().getIntExtra("correct",0);
        int totalQuestions = getIntent().getIntExtra("total",0);

        long points = correctAnswers * POINTS;

        binding.score.setText(String.format("%d/%d", correctAnswers, totalQuestions));
        binding.earnedCoins.setText(String.valueOf(points));

        FirebaseFirestore database = FirebaseFirestore.getInstance();

        database.collection("users")
                .document(FirebaseAuth.getInstance().getUid())
                .update("coins", FieldValue.increment(points));

        binding.shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

             Intent intent = new Intent(Intent.ACTION_SEND);
             intent.setType("text/plain");
             String Body = "I scored "+correctAnswers+" out of 10 and received coin "+points+" You can also play and score more by responding to the right questions.\n"+
                     "Click the link below and download it from PlayStore\n"+
                     "https://play.google.com/store/apps/details?id="+
                     getPackageName();
             String sub = "http://play.google.com";
             intent.putExtra(Intent.EXTRA_SUBJECT,sub);
             intent.putExtra(Intent.EXTRA_TEXT,Body);
             startActivity(Intent.createChooser(intent,"share using"));
            }
        });

        binding.restartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(ResultActivity.this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                finish();
            }
        });
    }
}

//    String Body =  "Hey, i'm using the best earning app. Join using my invite code to instantly get 50"
//            +" coins. My referral code is "+correctAnswers+"\n"+
//            "Download from Play Store\n"+
//            "https://play.google.com/store/apps/details?id="+
//            getPackageName();
//Download from Play Store