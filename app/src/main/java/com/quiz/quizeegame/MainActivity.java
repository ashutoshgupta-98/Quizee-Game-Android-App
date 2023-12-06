package com.quiz.quizeegame;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.InstallStateUpdatedListener;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.InstallStatus;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.android.play.core.tasks.OnSuccessListener;
import com.google.android.play.core.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.material.navigation.NavigationBarView;
import com.quiz.quizeegame.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    public static int UPDATE_CODE = 10;
    AppUpdateManager appUpdateManager;
    private BroadcastReceiver broadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        broadcastReceiver = new NetworkBroadcast();
        registerReceiver(broadcastReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));

        FirebaseMessaging.getInstance().subscribeToTopic("weather")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull com.google.android.gms.tasks.Task<Void> task) {
                        String msg = "Subscribed";
                        if (!task.isSuccessful()) {
                            msg = "Subscribe failed";
                        }

                    }
                });

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(@NonNull InitializationStatus initializationStatus) {
            }
        });

        AdRequest adRequest = new AdRequest.Builder().build();
        binding.adView.loadAd(adRequest);

        setSupportActionBar(binding.toolbar);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content, new HomeFragment());
        transaction.commit();

        binding.bottomNav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                switch (item.getItemId()) {
                    case R.id.home:
                        transaction.replace(R.id.content, new HomeFragment());
                        transaction.commit();
                        break;
                    case R.id.rank:
                        transaction.replace(R.id.content, new LeaderboardsFragment());
                        transaction.commit();
                        break;
                    case R.id.wallet:
                        transaction.replace(R.id.content, new WalletFragment());
                        transaction.commit();
                        break;
                    case R.id.profile:
                        transaction.replace(R.id.content, new ProfileFragment());
                        transaction.commit();
                        break;
                }
                return true;
            }
        });

         // update app
        inAppUp();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.game) {
                Toast.makeText(this, "game section is clicked.", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this, GameActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }
    //update app
    private void inAppUp(){
        appUpdateManager = AppUpdateManagerFactory.create(this);
        Task<AppUpdateInfo> task = appUpdateManager.getAppUpdateInfo();
        task.addOnSuccessListener(new OnSuccessListener<AppUpdateInfo>() {
            @Override
            public void onSuccess(AppUpdateInfo appUpdateInfo) {
                if(appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                   && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)
                ){
                    try {
                        appUpdateManager.startUpdateFlowForResult(appUpdateInfo,AppUpdateType.FLEXIBLE,
                                MainActivity.this,
                                UPDATE_CODE
                        );
                    } catch (IntentSender.SendIntentException e) {
                        e.printStackTrace();
                        Log.d("Updateerror", "onSuccess: "+e.toString());
                    }
                }
            }
        });

        appUpdateManager.registerListener(listener);
    }
    InstallStateUpdatedListener listener = installState -> {
        if (installState.installStatus() == InstallStatus.DOWNLOADED){
            popUp();
        }
    };

    private void popUp(){
        Snackbar snackbar = Snackbar.make(
                findViewById(android.R.id.content),
                "App Update Almost Done.",
                Snackbar.LENGTH_INDEFINITE
        );
        snackbar.setAction("Reload", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appUpdateManager.completeUpdate();
            }
        });
        snackbar.setTextColor(Color.parseColor("#FFFFFFFF"));
        snackbar.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == UPDATE_CODE){
            if (resultCode != RESULT_OK){

            }
        }
    }
    //server connection
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
    }
}