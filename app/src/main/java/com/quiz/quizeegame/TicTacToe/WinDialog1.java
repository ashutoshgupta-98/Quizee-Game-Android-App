package com.quiz.quizeegame.TicTacToe;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.quiz.quizeegame.R;
import com.quiz.quizeegame.TicTacToe.OnlineGamePlayActivity;
import com.quiz.quizeegame.TicTacToe.OnlinePlayerNameGenerator;

public class WinDialog1 extends Dialog {

    private final String message;
    private final OnlineGamePlayActivity onlineGamePlayActivity;

    public WinDialog1(Context context, String message) {
        super(context);
        this.message = message;
        this.onlineGamePlayActivity = ((OnlineGamePlayActivity) context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.win_dialog1_layout);

        final TextView messageTV = findViewById(R.id.messageTV);
        final Button startBtn = findViewById(R.id.startNewBtn);

        messageTV.setText(message);
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                getContext().startActivity(new Intent(getContext(), OnlinePlayerNameGenerator.class));
                onlineGamePlayActivity.finish();
            }
        });
    }
}
