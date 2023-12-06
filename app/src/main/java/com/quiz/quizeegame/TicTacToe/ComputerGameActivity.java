package com.quiz.quizeegame.TicTacToe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.quiz.quizeegame.R;
import com.quiz.quizeegame.databinding.ActivityComputerGameBinding;

import java.util.ArrayList;
import java.util.Random;

public class ComputerGameActivity extends AppCompatActivity {

    ActivityComputerGameBinding binding;

    int activePlayer = 0;
    int[] gameState = {2, 2, 2, 2, 2, 2, 2, 2, 2};
    int [][] winningPositions = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6}};
    boolean gameActive = true;
//   private final List<int[]> emptySquares = new ArrayList<>();
   ArrayList <String> emptySquares = new ArrayList<>();
    private TextView playerOneName, playerTwoName;
    private LinearLayout player1Layout, player2Layout;
    private ImageView image1, image2, image3, image4, image5, image6, image7, image8, image9;
    private int totalSelectedBoxes = 1;

    public void computer() {
        if (gameActive) {
            int select = emptySquares.size();
            int selected = new Random().nextInt(select);
            String selectedSquare = emptySquares.get(selected);
            switch (selectedSquare){
                case "0":
                    ImageView imageView1 = findViewById(R.id.image1);
                    imageView1.setImageResource(R.drawable.cross);
                    activePlayer = 0;
                    emptySquares.remove(selectedSquare);
                    gameState[Integer.parseInt(selectedSquare)] = activePlayer;
                    performAction(imageView1,0);

                    break;

                case "1":
                    ImageView imageView2 = findViewById(R.id.image2);
                    imageView2.setImageResource(R.drawable.cross);
                    activePlayer = 0;
                    emptySquares.remove(selectedSquare);
                    gameState[Integer.parseInt(selectedSquare)] = activePlayer;
                    performAction(imageView2,1);
                    break;

                case "2":
                    ImageView imageView3 = findViewById(R.id.image3);
                    imageView3.setImageResource(R.drawable.cross);
                    activePlayer = 0;
                    emptySquares.remove(selectedSquare);
                    gameState[Integer.parseInt(selectedSquare)] = activePlayer;
                    break;

                case "3":
                    ImageView imageView4 = findViewById(R.id.image4);
                    imageView4.setImageResource(R.drawable.cross);
                    activePlayer = 0;
                    emptySquares.remove(selectedSquare);
                    gameState[Integer.parseInt(selectedSquare)] = activePlayer;
                    break;

                case "4":
                    ImageView imageView5 = findViewById(R.id.image5);
                    imageView5.setImageResource(R.drawable.cross);
                    activePlayer = 0;
                    emptySquares.remove(selectedSquare);
                    gameState[Integer.parseInt(selectedSquare)] = activePlayer;
                    break;

                case "5":
                    ImageView imageView6 = findViewById(R.id.image6);
                    imageView6.setImageResource(R.drawable.cross);
                    activePlayer = 0;
                    emptySquares.remove(selectedSquare);
                    gameState[Integer.parseInt(selectedSquare)] = activePlayer;
                    break;

                case "6":
                    ImageView imageView7 = findViewById(R.id.image7);
                    imageView7.setImageResource(R.drawable.cross);
                    activePlayer = 0;
                    emptySquares.remove(selectedSquare);
                    gameState[Integer.parseInt(selectedSquare)] = activePlayer;
                    break;

                case "7":
                    ImageView imageView8 = findViewById(R.id.image8);
                    imageView8.setImageResource(R.drawable.cross);
                    activePlayer = 0;
                    emptySquares.remove(selectedSquare);
                    gameState[Integer.parseInt(selectedSquare)] = activePlayer;
                    break;

                case "8":
                    ImageView imageView9 = findViewById(R.id.image9);
                    imageView9.setImageResource(R.drawable.cross);
                    activePlayer = 0;
                    emptySquares.remove(selectedSquare);
                    gameState[Integer.parseInt(selectedSquare)] = activePlayer;
                    break;
            }
            checkPlayerWin();
        }
    }

    public void playerTap (View view) {
        ImageView counter = (ImageView) view;
        int tappedCounter = Integer.parseInt(counter.getTag().toString());

        if (gameState[tappedCounter] == 2 && gameActive && emptySquares.contains(Integer.toString(tappedCounter))) {
            gameState[tappedCounter] = activePlayer;
            counter.setTranslationY(-1500);


            if (activePlayer == 0) {
                counter.setImageResource(R.drawable.oi);

                activePlayer = 1;

                emptySquares.remove(Integer.toString(tappedCounter));

                counter.animate().translationYBy(1500).rotation(3600).setDuration(300);

                checkPlayerWin();
                computer();
            }
        }
    }

    private void performAction(ImageView imageView, int selectedBoxPosition ){

        gameState[selectedBoxPosition] = activePlayer;

        if (activePlayer == 1){
            imageView.setImageResource(R.drawable.cross);

            if (checkPlayerWin()){

                WinDialog2 winDialog2 = new WinDialog2(ComputerGameActivity.this, playerOneName.getText().toString() + " has won the match",ComputerGameActivity.this);
                winDialog2.setCancelable(false);
                winDialog2.show();


            }
            else if(totalSelectedBoxes == 9){
                WinDialog2 winDialog2 = new WinDialog2(ComputerGameActivity.this, "It is a draw!",ComputerGameActivity.this);
                winDialog2.setCancelable(false);
                winDialog2.show();
            }
            else{
                changePlayerTrun(2);

                totalSelectedBoxes++;
            }
        }
        else{
            imageView.setImageResource(R.drawable.o);

            if (checkPlayerWin()){
                WinDialog2 winDialog2 = new WinDialog2(ComputerGameActivity.this, playerTwoName.getText().toString() + " has won the match",ComputerGameActivity.this);
                winDialog2.setCancelable(false);
                winDialog2.show();

            }
            else if(selectedBoxPosition == 9){
                WinDialog2 winDialog2 = new WinDialog2(ComputerGameActivity.this, "It is a draw!",ComputerGameActivity.this);
                winDialog2.setCancelable(false);
                winDialog2.show();
            }
            else{
                changePlayerTrun(1);

                totalSelectedBoxes++;
            }
        }
    }



    private void changePlayerTrun(int currentPlayerTrun){
        activePlayer = currentPlayerTrun;

        if (activePlayer == 1){
            player1Layout.setBackgroundResource(R.drawable.round_back_blue_border);
            player2Layout.setBackgroundResource(R.drawable.round_back_dark_blue);
        }else{
            player2Layout.setBackgroundResource(R.drawable.round_back_blue_border);
            player1Layout.setBackgroundResource(R.drawable.round_back_dark_blue);
        }
    }

    private boolean checkPlayerWin(){
        boolean response = false;

        for (int i=0; i<emptySquares.size(); i++){
//            final int [] winningPosition = emptySquares.get(i);
            for (int[] winningPosition : winningPositions)

                if (gameState[winningPosition[0]] == gameState[winningPosition[1]] &&
                        gameState[winningPosition[1]] == gameState[winningPosition[2]] &&
                        gameState[winningPosition[0]] != 2)

//            if (gameState[winningPosition[0]] == activePlayer &&
//                    gameState[winningPosition[1]] == activePlayer &&
//                    gameState[winningPosition[2]] == activePlayer)
            {
                gameActive = false;
                String winner = "";

                if (activePlayer == 1) {
                    winner = "0";
                    checkPlayerWin();
                } else {
                    winner = "x";
                    checkPlayerWin();
                }
                TextView winnerTextView = findViewById(R.id.textView16);

                winnerTextView.setText("Winner : "+ winner);

                winnerTextView.setVisibility(View.VISIBLE);

                response = true;
            }
        }
        return response;

    }

    private boolean isBoxSelectable(int gameStates) {

        boolean response = false;

        if (gameState[gameStates] == 0){
            response = true;
        }
        return response;
    }

    public void restartMatch() {
//        gameState = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};

//        activePlayer = 1;

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

        for (int i = 0; i < gameState.length; i++) {
            gameState[i] = 2;
        }

        activePlayer = 0;
        gameActive = true;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityComputerGameBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        playerOneName = findViewById(R.id.player1TV);
        playerTwoName = findViewById(R.id.player2TV);

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

//        emptySquares.add(new int[]{0, 1, 2});
//        emptySquares.add(new int[]{3, 4, 5});
//        emptySquares.add(new int[]{6, 7, 8});
//        emptySquares.add(new int[]{0, 3, 6});
//        emptySquares.add(new int[]{1, 4, 7});
//        emptySquares.add(new int[]{2, 5, 8});
//        emptySquares.add(new int[]{2, 4, 6});
//        emptySquares.add(new int[]{0, 4, 8});

        emptySquares.add("0");
        emptySquares.add("1");
        emptySquares.add("2");
        emptySquares.add("3");
        emptySquares.add("4");
        emptySquares.add("5");
        emptySquares.add("6");
        emptySquares.add("7");
        emptySquares.add("8");

//        if (gameActive) {
//            int select = emptySquares.size();
//            int selected = new Random().nextInt(select);
//            int[] selectedSquare = emptySquares.get(selected);
//        }
//            image1.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    if (isBoxSelectable(0)) {
//                        performAction((ImageView) view, 0);
//                    }
//                    playerTap(view);
//                }
//            });
//
//            image2.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    if (isBoxSelectable(1)) {
//                        performAction((ImageView) view, 1);
//                    }
//                }
//            });
//
//            image3.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    if (isBoxSelectable(2)) {
//                        performAction((ImageView) view, 2);
//                    }
//                }
//            });
//
//            image4.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    if (isBoxSelectable(3)) {
//                        performAction((ImageView) view, 3);
//                    }
//                }
//            });
//
//            image5.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    if (isBoxSelectable(4)) {
//                        performAction((ImageView) view, 4);
//                    }
//                }
//            });
//
//            image6.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    if (isBoxSelectable(5)) {
//                        performAction((ImageView) view, 5);
//
//                    }
//                }
//            });
//
//            image7.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    if (isBoxSelectable(6)) {
//                        performAction((ImageView) view, 6);
//                    }
//                }
//            });
//
//            image8.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    if (isBoxSelectable(7)) {
//                        performAction((ImageView) view, 7);
//                    }
//                }
//            });
//
//            image9.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    if (isBoxSelectable(8)) {
//                        performAction((ImageView) view, 8);
//                    }
//                }
//            });

    }
}

//     int activePlayer = 0;
//
//     int[] gameState = {2, 2, 2, 2, 2, 2, 2, 2, 2};
//
//    int [][] winningPositions = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, {2, 4, 6}, {0, 4, 8}};
//    boolean gameActive = true;
//    ArrayList<String> emptySquares = new ArrayList<>();
////    public static int counter = 0;
//
//    public void computer() {
//        if (gameActive) {
//            int select = emptySquares.size();
//            int selected = new Random().nextInt(select);
//            String selectedSquare = emptySquares.get(selected);
//            switch (selectedSquare){
//                case "0":
//                    ImageView imageView1 = findViewById(R.id.imageView1);
//                    imageView1.setImageResource(R.drawable.cross);
//                    activePlayer = 0;
//                    emptySquares.remove(selectedSquare);
//                    gameState[Integer.parseInt(selectedSquare)] = activePlayer;
//                    break;
//
//                case "1":
//                    ImageView imageView2 = findViewById(R.id.imageView2);
//                    imageView2.setImageResource(R.drawable.cross);
//                    activePlayer = 0;
//                    emptySquares.remove(selectedSquare);
//                    gameState[Integer.parseInt(selectedSquare)] = activePlayer;
//                    break;
//
//                case "2":
//                    ImageView imageView3 = findViewById(R.id.imageView3);
//                    imageView3.setImageResource(R.drawable.cross);
//                    activePlayer = 0;
//                    emptySquares.remove(selectedSquare);
//                    gameState[Integer.parseInt(selectedSquare)] = activePlayer;
//                    break;
//
//                case "3":
//                    ImageView imageView4 = findViewById(R.id.imageView4);
//                    imageView4.setImageResource(R.drawable.cross);
//                    activePlayer = 0;
//                    emptySquares.remove(selectedSquare);
//                    gameState[Integer.parseInt(selectedSquare)] = activePlayer;
//                    break;
//
//                case "4":
//                    ImageView imageView5 = findViewById(R.id.imageView5);
//                    imageView5.setImageResource(R.drawable.cross);
//                    activePlayer = 0;
//                    emptySquares.remove(selectedSquare);
//                    gameState[Integer.parseInt(selectedSquare)] = activePlayer;
//                    break;
//
//                case "5":
//                    ImageView imageView6 = findViewById(R.id.imageView6);
//                    imageView6.setImageResource(R.drawable.cross);
//                    activePlayer = 0;
//                    emptySquares.remove(selectedSquare);
//                    gameState[Integer.parseInt(selectedSquare)] = activePlayer;
//                    break;
//
//                case "6":
//                    ImageView imageView7 = findViewById(R.id.imageView7);
//                    imageView7.setImageResource(R.drawable.cross);
//                    activePlayer = 0;
//                    emptySquares.remove(selectedSquare);
//                    gameState[Integer.parseInt(selectedSquare)] = activePlayer;
//                    break;
//
//                case "7":
//                    ImageView imageView8 = findViewById(R.id.imageView8);
//                    imageView8.setImageResource(R.drawable.cross);
//                    activePlayer = 0;
//                    emptySquares.remove(selectedSquare);
//                    gameState[Integer.parseInt(selectedSquare)] = activePlayer;
//                    break;
//
//                case "8":
//                    ImageView imageView9 = findViewById(R.id.imageView9);
//                    imageView9.setImageResource(R.drawable.cross);
//                    activePlayer = 0;
//                    emptySquares.remove(selectedSquare);
//                    gameState[Integer.parseInt(selectedSquare)] = activePlayer;
//                    break;
//            }
//            check();
//        }
//    }
//
//    public void playerTap (View view) {
//        ImageView counter = (ImageView) view;
//        int tappedCounter = Integer.parseInt(counter.getTag().toString());
//
//        if (gameState[tappedCounter] == 2 && gameActive && emptySquares.contains(Integer.toString(tappedCounter))) {
//            gameState[tappedCounter] = activePlayer;
//            counter.setTranslationY(-1500);
//
//            if (activePlayer == 0) {
//                counter.setImageResource(R.drawable.oi);
//
//                activePlayer = 1;
//
//                emptySquares.remove(Integer.toString(tappedCounter));
//
//                counter.animate().translationYBy(1500).rotation(3600).setDuration(300);
//
//                check();
//
//                computer();
//            }
//
//        }
//    }
//
//        public void check () {
//            for (int[] winningPosition : winningPositions) {
//                if (gameState[winningPosition[0]] == gameState[winningPosition[1]] && gameState[winningPosition[1]] == gameState[winningPosition[2]] && gameState[winningPosition[0]] != 2) {
//                    gameActive = false;
//                    String winner = "";
//
//                    if (activePlayer == 1) {
//                        winner = "0";
//                    } else {
//                        winner = "x";
//                    }
//                    Button playAgain = findViewById(R.id.playAgain);
//                    TextView winnerTextView = findViewById(R.id.textView16);
//
//                    winnerTextView.setText("Winner : "+ winner);
//
//                    playAgain.setVisibility(View.VISIBLE);
//                    winnerTextView.setVisibility(View.VISIBLE);
//
//                }else{
//                    Button playAgain = findViewById(R.id.playAgain);
//                    playAgain.setVisibility(View.VISIBLE);
//                }
//
//            }
//        }
//
// public void playAgain (View view){
//
//        Button playAgain = findViewById(R.id.playAgain);
//        TextView winnerTextView = findViewById(R.id.textView16);
//        GridLayout gridLayout = findViewById(R.id.gridLayout);
//
//        playAgain.setVisibility(View.INVISIBLE);
//        winnerTextView.setVisibility(View.INVISIBLE);
//
//        for (int i = 0; i < gridLayout.getChildCount(); i++){
//            ImageView counter = (ImageView) gridLayout.getChildAt(i);
//            counter.setImageDrawable(null);
//        }
//        for (int i = 0; i<gameState.length; i++){
//            gameState[i] = 2;
//        }
//
//     activePlayer = 0;
//     gameActive = true;
//
// }
//
//                @Override
//                protected void onCreate (Bundle savedInstanceState){
//                    super.onCreate(savedInstanceState);
//               binding = ActivityComputerGameBinding.inflate(getLayoutInflater());
//               setContentView(binding.getRoot());
//                    emptySquares.add("0");
//                    emptySquares.add("1");
//                    emptySquares.add("2");
//                    emptySquares.add("3");
//                    emptySquares.add("4");
//                    emptySquares.add("5");
//                    emptySquares.add("6");
//                    emptySquares.add("7");
//                    emptySquares.add("8");
//
//                }
//            }




