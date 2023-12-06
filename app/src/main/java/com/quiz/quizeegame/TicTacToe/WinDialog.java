package com.quiz.quizeegame.TicTacToe;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.quiz.quizeegame.R;
import com.quiz.quizeegame.TicTacToe.GamePlayActivity;

public class WinDialog extends Dialog {

    private final String message;
    private final GamePlayActivity gamePlayActivity;

    public WinDialog(@NonNull Context context, String message, GamePlayActivity gamePlayActivity) {
        super(context);
        this.message = message;
        this.gamePlayActivity = gamePlayActivity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.win_dialog_layout);

        final TextView messageTxt = findViewById(R.id.messageTxt);
        final Button startAgainBtn = findViewById(R.id.startAgainBtn);



        messageTxt.setText(message);

        startAgainBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gamePlayActivity.restartMatch();
                dismiss();
            }
        });
    }
}
