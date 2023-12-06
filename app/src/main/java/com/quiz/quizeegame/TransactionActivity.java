package com.quiz.quizeegame;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.ServerTimestamp;
import com.quiz.quizeegame.databinding.ActivityMainBinding;
import com.quiz.quizeegame.databinding.ActivityTransactionBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TransactionActivity extends AppCompatActivity {

    ActivityTransactionBinding binding;
    TabLayout tabLayout;
    ViewPager2 viewPager2;
    MyViewPageAdapter myViewPageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTransactionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

          tabLayout = findViewById(R.id.tab_layout);
          viewPager2 = findViewById(R.id.view_pager);
          myViewPageAdapter = new MyViewPageAdapter(this);
          viewPager2.setAdapter(myViewPageAdapter);

          tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
              @Override
              public void onTabSelected(TabLayout.Tab tab) {
                  viewPager2.setCurrentItem(tab.getPosition());
              }

              @Override
              public void onTabUnselected(TabLayout.Tab tab) {

              }

              @Override
              public void onTabReselected(TabLayout.Tab tab) {

              }
          });

          viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
              @Override
              public void onPageSelected(int position) {
                  super.onPageSelected(position);
                  tabLayout.getTabAt(position).select();
              }
          });
    }
}