package com.quiz.quizeegame;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;

import com.quiz.quizeegame.databinding.ActivityInternetConnectionBinding;

public class NetworkBroadcast extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {

        if (!isNetworkConnected(context)){
            ActivityInternetConnectionBinding binding = ActivityInternetConnectionBinding
                    .inflate(LayoutInflater.from(context));
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setView(binding.getRoot());
            builder.setCancelable(false);
            Dialog dialog =  builder.create();
            dialog.show();
            dialog.getWindow().setGravity(Gravity.CENTER);

            binding.tvRetry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (isNetworkConnected(context))
                    {
                        dialog.dismiss();
                    }
                }
            });
        }


    }
    private boolean isNetworkConnected(Context context){
        try{
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            return networkInfo != null && networkInfo.isConnected();
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
