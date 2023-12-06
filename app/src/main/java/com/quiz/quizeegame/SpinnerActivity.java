package com.quiz.quizeegame;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.quiz.quizeegame.SpinWheel.LuckyWheelView;
import com.quiz.quizeegame.SpinWheel.model.LuckyItem;
import com.quiz.quizeegame.databinding.ActivitySpinnerBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SpinnerActivity extends AppCompatActivity {

    ActivitySpinnerBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySpinnerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        List<LuckyItem> data = new ArrayList<>();

        LuckyItem luckyitem1 = new LuckyItem();
        luckyitem1.topText = "5";
        luckyitem1.secondaryText = "Coins";
        luckyitem1.color = Color.parseColor("#224BDD");
        luckyitem1.textColor = Color.parseColor("#ffffff");
        data.add(luckyitem1);

        LuckyItem luckyitem2 = new LuckyItem();
        luckyitem2.topText = "10";
        luckyitem2.secondaryText = "Coins";
        luckyitem2.color = Color.parseColor("#00cf00");
        luckyitem2.textColor = Color.parseColor("#ffffff");
        data.add(luckyitem2);

        LuckyItem luckyitem3 = new LuckyItem();
        luckyitem3.topText = "15";
        luckyitem3.secondaryText = "Coins";
        luckyitem3.color = Color.parseColor("#DDD625");
        luckyitem3.textColor = Color.parseColor("#ffffff");
        data.add(luckyitem3);

        LuckyItem luckyitem4 = new LuckyItem();
        luckyitem4.topText = "20";
        luckyitem4.secondaryText = "Coins";
        luckyitem4.color = Color.parseColor("#7f00d9");
        luckyitem4.textColor = Color.parseColor("#ffffff");
        data.add(luckyitem4);

        LuckyItem luckyItem5 = new LuckyItem();
        luckyItem5.topText = "25";
        luckyItem5.secondaryText = "Coins";
        luckyItem5.color = Color.parseColor("#EE6909");
        luckyItem5.textColor = Color.parseColor("#eceff1");
        data.add(luckyItem5);

        LuckyItem luckyitem6 = new LuckyItem();
        luckyitem6.topText = "30";
        luckyitem6.secondaryText = "Coins";
        luckyitem6.color = Color.parseColor("#dc0000");
        luckyitem6.textColor = Color.parseColor("#ffffff");
        data.add(luckyitem6);

        LuckyItem luckyItem7 = new LuckyItem();
        luckyItem7.topText = "35";
        luckyItem7.secondaryText = "Coins";
        luckyItem7.color = Color.parseColor("#1FE1E1");
        luckyItem7.textColor = Color.parseColor("#ffffff");
        data.add(luckyItem7);

        binding.wheelview.setData(data);
        binding.wheelview.setRound(6);

        final MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.spinsound);

        binding.spinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Random r = new Random();
                int randomNumber = r.nextInt(7);
                binding.wheelview.startLuckyWheelWithTargetIndex(randomNumber);
                mediaPlayer.start();
                }
        });

        binding.wheelview.setLuckyRoundItemSelectedListener(new LuckyWheelView.LuckyRoundItemSelectedListener() {
            @Override
            public void LuckyRoundItemSelected(int index) {

                updateCash(index);
            }
        });
    }

    void updateCash(int index){
        long cash = 0;
        switch (index){
            case 0:
                cash = 5;
                 break;
            case 1:
                cash = 10;
                break;
            case 2:
                cash = 15;
                break;
            case 3:
                cash = 20;
                break;
            case 4:
                cash = 25;
                break;
            case 5:
                cash = 30;
                break;
            case 6:
                cash = 35;
                break;
        }

        FirebaseFirestore database = FirebaseFirestore.getInstance();

        database
                .collection("users")
                .document(FirebaseAuth.getInstance().getUid())
                .update("coins", FieldValue.increment(cash)).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(SpinnerActivity.this, "Coins added in account.", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }
}