package com.quiz.quizeegame.TicTacToe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.quiz.quizeegame.R;
import com.quiz.quizeegame.databinding.ActivityGamePlayBinding;

import java.util.ArrayList;
import java.util.List;


public class GamePlayActivity extends AppCompatActivity {
    ActivityGamePlayBinding binding;

    private final List<int[]> combinationsList = new ArrayList<>();

    int points = 10;

    private int [] boxPositions ={0,0,0,0,0,0,0,0,0};

    private int playerTurn = 1;

    private int totalSelectedBoxes = 1;

    private LinearLayout playerOneLayout, playerTwoLayout;
    private TextView playerOneName, playerTwoName;
    private ImageView image1, image2, image3, image4, image5, image6, image7, image8, image9;

    private InterstitialAd mInterstitialAd;

    MediaPlayer otik;
    MediaPlayer xtik;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGamePlayBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        otik = MediaPlayer.create(this, R.raw.otone);
        xtik = MediaPlayer.create(this,R.raw.xtone);

        AdRequest adRequest = new AdRequest.Builder().build();

        InterstitialAd.load(this,"ca-app-pub-6301220023928445/9409121271", adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        // The mInterstitialAd reference will be null until
                        // an ad is loaded.
                        mInterstitialAd = interstitialAd;
                        if (mInterstitialAd != null){
                            mInterstitialAd.show(GamePlayActivity.this);
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

        playerOneName = findViewById(R.id.playerOneName);
        playerTwoName = findViewById(R.id.playerTwoName);

        playerOneLayout = findViewById(R.id.playerOneLayout);
        playerTwoLayout = findViewById(R.id.playerTwoLayout);

         image1 = findViewById(R.id.image1);
         image2 = findViewById(R.id.image2);
         image3 = findViewById(R.id.image3);
         image4 = findViewById(R.id.image4);
         image5 = findViewById(R.id.image5);
         image6 = findViewById(R.id.image6);
         image7 = findViewById(R.id.image7);
         image8 = findViewById(R.id.image8);
         image9 = findViewById(R.id.image9);

         combinationsList.add(new int[]{0, 1, 2});
        combinationsList.add(new int[]{3, 4, 5});
        combinationsList.add(new int[]{6, 7, 8});
        combinationsList.add(new int[]{0, 3, 6});
        combinationsList.add(new int[]{1, 4, 7});
        combinationsList.add(new int[]{2, 5, 8});
        combinationsList.add(new int[]{2, 4, 6});
        combinationsList.add(new int[]{0, 4, 8});

        final String getPlayerOneName = getIntent().getStringExtra("playerOne");
        final String getPlayerTwoName = getIntent().getStringExtra("playerTwo");

        playerOneName.setText(getPlayerOneName);
        playerTwoName.setText(getPlayerTwoName);

        image1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if (isBoxSelectable(0)){
                   performAction((ImageView)view, 0);
               }
            }
        });

        image2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isBoxSelectable(1)){
                    performAction((ImageView)view, 1);
                }
            }
        });

        image3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isBoxSelectable(2)){
                    performAction((ImageView)view, 2);
                }
            }
        });

        image4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isBoxSelectable(3)){
                    performAction((ImageView)view, 3);
                }
            }
        });

        image5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isBoxSelectable(4)){
                    performAction((ImageView)view, 4);
                }
            }
        });

        image6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isBoxSelectable(5)) {
                    performAction((ImageView)view, 5);
                }
            }
        });

        image7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isBoxSelectable(6)){
                    performAction((ImageView)view, 6);
                }
            }
        });

        image8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isBoxSelectable(7)){
                    performAction((ImageView)view, 7);
                }
            }
        });

        image9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isBoxSelectable(8)){
                   performAction((ImageView)view, 8);
                }
            }
        });



    }
    private void performAction(ImageView imageView, int selectedBoxPosition ){

        boxPositions[selectedBoxPosition] = playerTurn;

        if (playerTurn == 1){
            imageView.setImageResource(R.drawable.close);
             xtik.start();
            if (checkPlayerWin()){

                WinDialog winDialog = new WinDialog(GamePlayActivity.this, playerOneName.getText().toString() + " has won the match",GamePlayActivity.this);
                winDialog.setCancelable(false);
                winDialog.show();
                 coin();

            }
            else if(totalSelectedBoxes == 9){
                WinDialog winDialog = new WinDialog(GamePlayActivity.this, "It is a draw!",GamePlayActivity.this);
                winDialog.setCancelable(false);
                winDialog.show();
            }
            else{
                changePlayerTrun(2);

                totalSelectedBoxes++;
            }
        }
        else{
            imageView.setImageResource(R.drawable.o);
             otik.start();
            if (checkPlayerWin()){
                WinDialog winDialog = new WinDialog(GamePlayActivity.this, playerTwoName.getText().toString() + " has won the match",GamePlayActivity.this);
                winDialog.setCancelable(false);
                winDialog.show();
                coin();
            }
            else if(selectedBoxPosition == 9){
                WinDialog winDialog = new WinDialog(GamePlayActivity.this, "It is a draw!",GamePlayActivity.this);
                winDialog.setCancelable(false);
                winDialog.show();
            }
            else{
                changePlayerTrun(1);

                totalSelectedBoxes++;
            }
        }
    }
    private void changePlayerTrun(int currentPlayerTrun){
        playerTurn = currentPlayerTrun;

        if (playerTurn == 1){
            playerOneLayout.setBackgroundResource(R.drawable.round_back_blue_border);
            playerTwoLayout.setBackgroundResource(R.drawable.round_back_dark_blue);
        }else{
            playerTwoLayout.setBackgroundResource(R.drawable.round_back_blue_border);
            playerOneLayout.setBackgroundResource(R.drawable.round_back_dark_blue);
        }
    }

    private boolean checkPlayerWin(){
        boolean response = false;

        for (int i=0; i<combinationsList.size(); i++){
            final int [] combination = combinationsList.get(i);

            if (boxPositions[combination[0]] == playerTurn &&
                    boxPositions[combination[1]] == playerTurn &&
                    boxPositions[combination[2]] == playerTurn){
                response = true;
            }
        }
        return response;
    }

    private boolean isBoxSelectable(int boxPosition){

        boolean response = false;

        if (boxPositions[boxPosition] == 0){
            response = true;
        }
        return response;
    }

    public void restartMatch(){
        boxPositions = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};

        playerTurn = 1;

        totalSelectedBoxes = 1;

        image1.setImageResource(R.drawable.tranparent);
        image2.setImageResource(R.drawable.tranparent);
        image3.setImageResource(R.drawable.tranparent);
        image4.setImageResource(R.drawable.tranparent);
        image5.setImageResource(R.drawable.tranparent);
        image6.setImageResource(R.drawable.tranparent);
        image7.setImageResource(R.drawable.tranparent);
        image8.setImageResource(R.drawable.tranparent);
        image9.setImageResource(R.drawable.tranparent);
    }
    public void coin(){

        FirebaseFirestore database = FirebaseFirestore.getInstance();
        database.collection("users")
                .document(FirebaseAuth.getInstance().getUid())
                .update("coins", FieldValue.increment(points));
    }
}