package com.quiz.quizeegame;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.quiz.quizeegame.databinding.ActivityQuizeeBinding;

import java.util.ArrayList;
import java.util.Random;

public class QuizeeActivity extends AppCompatActivity {

    ActivityQuizeeBinding binding;

    ArrayList<Question> questions;

    int index = 0;
    Question question;
    CountDownTimer timer;
    FirebaseFirestore database;
    int correctAnswers = 0;
    MediaPlayer mediaPlayer;
    MediaPlayer writeAnswer;
    MediaPlayer wrongAnswer;
    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQuizeeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        AdRequest adRequest = new AdRequest.Builder().build();

        InterstitialAd.load(this,"ca-app-pub-6301220023928445/3012615804", adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        // The mInterstitialAd reference will be null until
                        // an ad is loaded.
                        mInterstitialAd = interstitialAd;
                        if (mInterstitialAd != null){
                            mInterstitialAd.show(QuizeeActivity.this);
                        }
//                        Log.i(TAG, "onAdLoaded");
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error
//                        Log.i(TAG, loadAdError.getMessage());
                        mInterstitialAd = null;
                    }
                });

 //       AdRequest adRequest = new AdRequest.Builder().build();

        binding.adView2.loadAd(adRequest);

        binding.adView2.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                Toast.makeText(QuizeeActivity.this, "Ad is loaded.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdClicked() {
                super.onAdClicked();
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
                binding.adView2.loadAd(adRequest);
            }
        });

        questions = new ArrayList<>();
        database = FirebaseFirestore.getInstance();

      final String catId = getIntent().getStringExtra("catId");

        Random random = new Random();
      final int rand = random.nextInt(200);

        database.collection("categories")
                .document(catId)
                .collection("questions")
                .whereGreaterThanOrEqualTo("index", rand)
                .orderBy("index")
                .limit(10).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
               if(queryDocumentSnapshots.getDocuments().size() < 10){
                   database.collection("categories")
                           .document(catId)
                           .collection("questions")
                           .whereLessThanOrEqualTo("index", rand)
                           .orderBy("index")
                           .limit(10).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                       @Override
                       public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                               for (DocumentSnapshot snapshot : queryDocumentSnapshots){
                                   Question question = snapshot.toObject(Question.class);
                                   questions.add(question);
                               }
                           setNextQuestion();
                       }
                   });
               }else {
                   for(DocumentSnapshot snapshot : queryDocumentSnapshots){
                       Question question = snapshot.toObject(Question.class);
                       questions.add(question);
                   }
                   setNextQuestion();
               }
            }
        });
        resetTimer();
        sound();
    }

    void resetTimer(){
        timer = new CountDownTimer(40000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
              binding.timer.setText(String.valueOf(millisUntilFinished/1000));
              if (mediaPlayer != null)
              mediaPlayer.start();
            }

            @Override
            public void onFinish() {
                mediaPlayer.pause();
                finish();

            }
        };
    }

    void showAnswer(){
        if(question.getAnswer().equals(binding.option1.getText().toString()))
            binding.option1.setBackground(getResources().getDrawable(R.drawable.option_right));
         else if(question.getAnswer().equals(binding.option2.getText().toString()))
            binding.option2.setBackground(getResources().getDrawable(R.drawable.option_right));
         else if(question.getAnswer().equals(binding.option3.getText().toString()))
            binding.option3.setBackground(getResources().getDrawable(R.drawable.option_right));
         else if(question.getAnswer().equals(binding.option4.getText().toString()))
            binding.option4.setBackground(getResources().getDrawable(R.drawable.option_right));

    }

    void setNextQuestion(){
        if(timer != null)
            timer.cancel();
           timer.start();
            mediaPlayer.start();
        if (index < questions.size()) {
            binding.questionCounter.setText(String.format("%d/%d", (index+1), questions.size()));
            question = questions.get(index);
            binding.question.setText(question.getQuestion());
            binding.option1.setText(question.getOption1());
            binding.option2.setText(question.getOption2());
            binding.option3.setText(question.getOption3());
            binding.option4.setText(question.getOption4());
        }
    }
    void checkAnswer(TextView textView){
        writeAnswer = MediaPlayer.create(QuizeeActivity.this,R.raw.rightanswer);
        wrongAnswer = MediaPlayer.create(QuizeeActivity.this,R.raw.wronganswer);
        String selectedAnswer = textView.getText().toString();
        if(selectedAnswer.equals(question.getAnswer())){
            correctAnswers++;
            writeAnswer.start();
            textView.setBackground(getResources().getDrawable(R.drawable.option_right));
        }else{
            showAnswer();
            wrongAnswer.start();
            textView.setBackground(getResources().getDrawable(R.drawable.option_wrong));
        }
    }

    void enableButton(){
        binding.option1.setClickable(true);
        binding.option2.setClickable(true);
        binding.option3.setClickable(true);
        binding.option4.setClickable(true);
    }
    void disableButton(){
        binding.option1.setClickable(false);
        binding.option2.setClickable(false);
        binding.option3.setClickable(false);
        binding.option4.setClickable(false);
    }

    void reset(){
        enableButton();
        binding.option1.setBackground(getResources().getDrawable(R.drawable.option_unselected));
        binding.option2.setBackground(getResources().getDrawable(R.drawable.option_unselected));
        binding.option3.setBackground(getResources().getDrawable(R.drawable.option_unselected));
        binding.option4.setBackground(getResources().getDrawable(R.drawable.option_unselected));
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.option_1:
            case R.id.option_2:
            case R.id.option_3:
            case R.id.option_4:
                if(timer != null)
                    timer.cancel();
                if (mediaPlayer != null)
                    mediaPlayer.pause();
                TextView selected = (TextView) view;
                checkAnswer(selected);
                Toast.makeText(this, "Option Clicked.", Toast.LENGTH_SHORT).show();
                disableButton();
                break;
            case R.id.nextBtn:
                reset();
                if(index < questions.size() -1) {
                    index++;
                    setNextQuestion();
                }else{
                    Intent intent = new Intent(QuizeeActivity.this, ResultActivity.class);
                    intent.putExtra("correct", correctAnswers);
                    intent.putExtra("total",questions.size());
                    startActivity(intent);
                    finish();
                    mediaPlayer.stop();
                   // Toast.makeText(this, "Quiz Finished.", Toast.LENGTH_SHORT).show();
                }
                break;
        }
        binding.quizBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                mediaPlayer.stop();
            }
        });
    }

    void sound(){
        mediaPlayer = MediaPlayer.create(QuizeeActivity.this,R.raw.timertiktik);
    }

    @Override
    public void onBackPressed() {
      
    }
}