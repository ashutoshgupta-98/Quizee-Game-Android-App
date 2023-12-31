package com.quiz.quizeegame.TicTacToe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.quiz.quizeegame.R;
import com.quiz.quizeegame.databinding.ActivityOnlineGamePlayBinding;

import java.util.ArrayList;
import java.util.List;

public class OnlineGamePlayActivity extends AppCompatActivity {

    ActivityOnlineGamePlayBinding binding;
    private LinearLayout player1Layout, player2Layout;
    private ImageView image1, image2, image3, image4, image5, image6, image7, image8, image9;
    private TextView player1TV, player2TV;
    private final List<int[]> combinationsList = new ArrayList<>();
    private final List<String> doneBoxes = new ArrayList<>();

    int points = 10;

    private String playerUniqueId = "0";

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://quizee-game-default-rtdb.firebaseio.com/");

    private boolean opponentFound = false;

    private String opponentUniqueId = "0";

    private String status = "matching";

    private String playerTurn = "";

    private String connectionId = "";

    ValueEventListener turnsEventListener, wowEventListener;

    private final String[] boxesSelectedBy = {"", "", "", "", "", "", "", "", ""};

    private InterstitialAd mInterstitialAd;

    MediaPlayer otik;
    MediaPlayer xtik;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOnlineGamePlayBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        otik = MediaPlayer.create(this, R.raw.otone);
        xtik = MediaPlayer.create(this,R.raw.xtone);

        AdRequest adRequest = new AdRequest.Builder().build();

        InterstitialAd.load(this,"ca-app-pub-6301220023928445/9217479777", adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        // The mInterstitialAd reference will be null until
                        // an ad is loaded.
                        mInterstitialAd = interstitialAd;
                        if (mInterstitialAd != null){
                            mInterstitialAd.show(OnlineGamePlayActivity.this);
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

        player1Layout = findViewById(R.id.player1Layout);
        player2Layout = findViewById(R.id.player2Layout);

        image1 = findViewById(R.id.image1);
        image2 = findViewById(R.id.image2);
        image3 = findViewById(R.id.image3);
        image4 = findViewById(R.id.image4);
        image5 = findViewById(R.id.image5);
        image6 = findViewById(R.id.image6);
        image7 = findViewById(R.id.image7);
        image8 = findViewById(R.id.image8);
        image9 = findViewById(R.id.image9);

        player1TV = findViewById(R.id.player1TV);
        player2TV = findViewById(R.id.player2TV);

        final String getPlayerName = getIntent().getStringExtra("playerName");

        combinationsList.add(new int[]{0,1,2});
        combinationsList.add(new int[]{3,4,5});
        combinationsList.add(new int[]{6,7,8});
        combinationsList.add(new int[]{0,3,6});
        combinationsList.add(new int[]{1,4,7});
        combinationsList.add(new int[]{2,5,8});
        combinationsList.add(new int[]{2,4,6});
        combinationsList.add(new int[]{0,4,8});

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Waiting for Opponent");
        progressDialog.show();
//        Runnable progressRunnable = new Runnable() {
//            @Override
//            public void run() {
//                progressDialog.cancel();
//
//            }
//        };
//
//        Handler pdCanceller = new Handler();
//        pdCanceller.postDelayed(progressRunnable, 5000);

        playerUniqueId = String.valueOf(System.currentTimeMillis());

        player1TV.setText(getPlayerName);

        databaseReference.child("connections").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(!opponentFound){

                    if(snapshot.hasChildren()){

                        for(DataSnapshot connections : snapshot.getChildren()){
                            String conId = connections.getKey();

                            int getPlayersCount = (int)connections.getChildrenCount();

                            if (status.equals("waiting")){

                                if (getPlayersCount == 2){

                                    playerTurn = playerUniqueId;
                                    applyPlayerTurn(playerTurn);

                                    boolean playerFound = false;

                                    for (DataSnapshot players : connections.getChildren()){

                                        String getPlayerUniqueId = players.getKey();

                                        if (getPlayerUniqueId.equals(playerUniqueId)){

                                            playerFound = true;
                                        }
                                        else if(playerFound){

                                            String getOpponentPlayerName = players.child("player_name").getValue(String.class);
                                            opponentUniqueId = players.getKey();

                                            player2TV.setText(getOpponentPlayerName);

                                            connectionId = conId;
                                            opponentFound = true;

                                            databaseReference.child("turns").child(connectionId).addValueEventListener(turnsEventListener);
                                            databaseReference.child("won").child(connectionId).addValueEventListener(wowEventListener);

                                            if (progressDialog.isShowing()){
                                                progressDialog.dismiss();
                                            }

                                            databaseReference.child("connections").removeEventListener(this);
                                            databaseReference.removeValue();
                                        }
                                    }
                                }
                            }

                            else{
                                if (getPlayersCount == 1){

                                    connections.child(playerUniqueId).child("player_name").getRef().setValue(getPlayerName);

                                    for(DataSnapshot players: connections.getChildren()){

                                        String getOpponentName = players.child("player_name").getValue(String.class);
                                        opponentUniqueId = players.getKey();

                                        playerTurn = opponentUniqueId;
                                        applyPlayerTurn(playerTurn);

                                        player2TV.setText(getOpponentName);

                                        connectionId = conId;
                                        opponentFound = true;

                                        databaseReference.child("turns").child(connectionId).addValueEventListener(turnsEventListener);
                                        databaseReference.child("won").child(connectionId).addValueEventListener(wowEventListener);

                                        if (progressDialog.isShowing()){
                                            progressDialog.dismiss();
                                        }

                                        databaseReference.child("connections").removeEventListener(this);
                                        databaseReference.removeValue();
                                        break;
                                    }
                                }
                            }
                        }

                        if(!opponentFound && !status.equals("waiting")){

                            String connectionUniqueId = String.valueOf(System.currentTimeMillis());

                            snapshot.child(connectionUniqueId).child(playerUniqueId).child("player_name").getRef().setValue(getPlayerName);

                            status = "waiting";
                        }
                    }
                    else{
                        String connectionUniqueId = String.valueOf(System.currentTimeMillis());

                        snapshot.child(connectionUniqueId).child(playerUniqueId).child("player_name").getRef().setValue(getPlayerName);

                        status = "waiting";

                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        turnsEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

               for (DataSnapshot dataSnapshot : snapshot.getChildren()){

                  if (dataSnapshot.getChildrenCount() == 2){

                      final int getBoxPosition = Integer.parseInt(dataSnapshot.child("box_position").getValue(String.class));

                      final String getPlayerId = dataSnapshot.child("player_id").getValue(String.class);

                      if (!doneBoxes.contains(String.valueOf(getBoxPosition))){

                          doneBoxes.add(String.valueOf(getBoxPosition));

                          if (getBoxPosition == 1){
                              selectBox(image1, getBoxPosition, getPlayerId);
                          }
                          else if (getBoxPosition == 2){
                              selectBox(image2, getBoxPosition, getPlayerId);
                          }
                          else if (getBoxPosition == 3){
                              selectBox(image3, getBoxPosition, getPlayerId);
                          }
                          else if (getBoxPosition == 4){
                              selectBox(image4, getBoxPosition, getPlayerId);
                          }
                          else if (getBoxPosition == 5){
                              selectBox(image5, getBoxPosition, getPlayerId);
                          }
                          else if (getBoxPosition == 6){
                              selectBox(image6, getBoxPosition, getPlayerId);
                          }
                          else if (getBoxPosition == 7){
                              selectBox(image7, getBoxPosition, getPlayerId);
                          }
                          else if (getBoxPosition == 8){
                              selectBox(image8, getBoxPosition, getPlayerId);
                          }
                          else if (getBoxPosition == 9){
                              selectBox(image9, getBoxPosition, getPlayerId);
                          }
                      }
                  }
               }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };

        wowEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.hasChild("player_id")){

                    String getWinPlayerId = snapshot.child("player_id").getValue(String.class);

                    final WinDialog1 winDialog1;

                    if (getWinPlayerId.equals(playerUniqueId)){
                         coin();
                        winDialog1 = new WinDialog1(OnlineGamePlayActivity.this, "You won the game");
                    }
                    else{
                        winDialog1 = new WinDialog1(OnlineGamePlayActivity.this, "Opponent won the game");
                    }
                    winDialog1.setCancelable(false);
                    winDialog1.show();

                    databaseReference.child("turns").child(connectionId).removeEventListener(turnsEventListener);
                    databaseReference.child("won").child(connectionId).removeEventListener(wowEventListener);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };

        image1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!doneBoxes.contains("1") && playerTurn.equals(playerUniqueId)){
                    ((ImageView)view).setImageResource(R.drawable.close);

                    databaseReference.child("turns").child(connectionId).child(String.valueOf(doneBoxes.size() + 1)).child("box_position").setValue("1");
                    databaseReference.child("turns").child(connectionId).child(String.valueOf(doneBoxes.size() + 1)).child("player_id").setValue(playerUniqueId);

                    playerTurn = opponentUniqueId;
                   xtik.start();
                }
            }
        });

        image2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!doneBoxes.contains("2") && playerTurn.equals(playerUniqueId)){
                    ((ImageView)view).setImageResource(R.drawable.close);

                    databaseReference.child("turns").child(connectionId).child(String.valueOf(doneBoxes.size() + 1)).child("box_position").setValue("2");
                    databaseReference.child("turns").child(connectionId).child(String.valueOf(doneBoxes.size() + 1)).child("player_id").setValue(playerUniqueId);

                    playerTurn = opponentUniqueId;
                    xtik.start();
                }
            }
        });

        image3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!doneBoxes.contains("3") && playerTurn.equals(playerUniqueId)){
                    ((ImageView)view).setImageResource(R.drawable.close);

                    databaseReference.child("turns").child(connectionId).child(String.valueOf(doneBoxes.size() + 1)).child("box_position").setValue("3");
                    databaseReference.child("turns").child(connectionId).child(String.valueOf(doneBoxes.size() + 1)).child("player_id").setValue(playerUniqueId);

                    playerTurn = opponentUniqueId;
                    xtik.start();
                }
            }
        });

        image4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!doneBoxes.contains("4") && playerTurn.equals(playerUniqueId)){
                    ((ImageView)view).setImageResource(R.drawable.close);

                    databaseReference.child("turns").child(connectionId).child(String.valueOf(doneBoxes.size() + 1)).child("box_position").setValue("4");
                    databaseReference.child("turns").child(connectionId).child(String.valueOf(doneBoxes.size() + 1)).child("player_id").setValue(playerUniqueId);

                    playerTurn = opponentUniqueId;
                    xtik.start();
                }
            }
        });

        image5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!doneBoxes.contains("5") && playerTurn.equals(playerUniqueId)){
                    ((ImageView)view).setImageResource(R.drawable.close);

                    databaseReference.child("turns").child(connectionId).child(String.valueOf(doneBoxes.size() + 1)).child("box_position").setValue("5");
                    databaseReference.child("turns").child(connectionId).child(String.valueOf(doneBoxes.size() + 1)).child("player_id").setValue(playerUniqueId);

                    playerTurn = opponentUniqueId;
                    xtik.start();
                }
            }
        });

        image6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!doneBoxes.contains("6") && playerTurn.equals(playerUniqueId)){
                    ((ImageView)view).setImageResource(R.drawable.close);

                    databaseReference.child("turns").child(connectionId).child(String.valueOf(doneBoxes.size() + 1)).child("box_position").setValue("6");
                    databaseReference.child("turns").child(connectionId).child(String.valueOf(doneBoxes.size() + 1)).child("player_id").setValue(playerUniqueId);

                    playerTurn = opponentUniqueId;
                    xtik.start();
                }
            }
        });

        image7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!doneBoxes.contains("7") && playerTurn.equals(playerUniqueId)){
                    ((ImageView)view).setImageResource(R.drawable.close);

                    databaseReference.child("turns").child(connectionId).child(String.valueOf(doneBoxes.size() + 1)).child("box_position").setValue("7");
                    databaseReference.child("turns").child(connectionId).child(String.valueOf(doneBoxes.size() + 1)).child("player_id").setValue(playerUniqueId);

                    playerTurn = opponentUniqueId;
                    xtik.start();
                }
            }
        });

        image8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!doneBoxes.contains("8") && playerTurn.equals(playerUniqueId)){
                    ((ImageView)view).setImageResource(R.drawable.close);

                    databaseReference.child("turns").child(connectionId).child(String.valueOf(doneBoxes.size() + 1)).child("box_position").setValue("8");
                    databaseReference.child("turns").child(connectionId).child(String.valueOf(doneBoxes.size() + 1)).child("player_id").setValue(playerUniqueId);

                    playerTurn = opponentUniqueId;
                    xtik.start();
                }
            }
        });

        image9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!doneBoxes.contains("9") && playerTurn.equals(playerUniqueId)){
                    ((ImageView)view).setImageResource(R.drawable.close);

                    databaseReference.child("turns").child(connectionId).child(String.valueOf(doneBoxes.size() + 1)).child("box_position").setValue("9");
                    databaseReference.child("turns").child(connectionId).child(String.valueOf(doneBoxes.size() + 1)).child("player_id").setValue(playerUniqueId);

                    playerTurn = opponentUniqueId;
                    xtik.start();
                }
            }
        });
    }

    private void applyPlayerTurn(String playerUniqueId2){
        if (playerUniqueId2.equals(playerUniqueId)){
            player1Layout.setBackgroundResource(R.drawable.round_back_blue_border);
            player2Layout.setBackgroundResource(R.drawable.round_back_dark_blue);
        }
        else{
            player2Layout.setBackgroundResource(R.drawable.round_back_blue_border);
            player1Layout.setBackgroundResource(R.drawable.round_back_dark_blue);
        }
    }
    private void selectBox(ImageView imageView, int selectedBoxPosition, String selectedByPlayer){

        boxesSelectedBy[selectedBoxPosition - 1] = selectedByPlayer;

        if (selectedByPlayer.equals(playerUniqueId)){
            imageView.setImageResource(R.drawable.close);
            playerTurn = opponentUniqueId;

        }
        else{
            imageView.setImageResource(R.drawable.o);
            playerTurn = playerUniqueId;
            otik.start();
        }

        applyPlayerTurn(playerTurn);

        if (checkplayerWin(selectedByPlayer)){

            databaseReference.child("won").child(connectionId).child("player_id").setValue(selectedByPlayer);
        }

        if (doneBoxes.size() == 9){

            final WinDialog1 winDialog1 = new WinDialog1(OnlineGamePlayActivity.this, "It is a Draw!");
            winDialog1.setCancelable(false);
            winDialog1.show();

        }
    }

    private boolean checkplayerWin(String playerId){

        boolean isPlayerWon = false;

        for (int i = 0; i < combinationsList.size(); i++){

            final int[] combination = combinationsList.get(i);

            if (boxesSelectedBy[combination[0]].equals(playerId) &&
                    boxesSelectedBy[combination[1]].equals(playerId) &&
                    boxesSelectedBy[combination[2]].equals(playerId)){
                isPlayerWon = true;
            }

        }
        return isPlayerWon;
    }

        public void coin(){
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        database.collection("users")
                .document(FirebaseAuth.getInstance().getUid())
                .update("coins", FieldValue.increment(points));
    }
}