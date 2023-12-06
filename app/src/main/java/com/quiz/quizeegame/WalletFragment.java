package com.quiz.quizeegame;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentReference;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.quiz.quizeegame.databinding.FragmentWalletBinding;

import java.util.Objects;

public class WalletFragment extends Fragment {

    FragmentWalletBinding binding;
    FirebaseAuth auth;
    FirebaseFirestore database;
    User user;
    TextView tv;
    TextView Upi;
    TextView ts;

    public WalletFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = FragmentWalletBinding.inflate(inflater, container, false);
        auth = FirebaseAuth.getInstance();
        database = FirebaseFirestore.getInstance();

        Upi = binding.upiBox.findViewById(R.id.upiBox);
        tv = binding.noticeBoard.findViewById(R.id.noticeBoard);
        ts = binding.rules.findViewById(R.id.rules);


        database.collection("users")
                .document(Objects.requireNonNull(FirebaseAuth.getInstance().getUid()))
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                user = documentSnapshot.toObject(User.class);
                binding.currentCoins.setText(String.valueOf(user.getCoins()));
            }
        });

        // notice box////
         DocumentReference document =  database.collection("notices").document("vGeoypmAAWTlLOLVr3FQ");
          document.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
              @Override
              public void onSuccess(DocumentSnapshot documentSnapshot) {
                  if (documentSnapshot.exists())
                  {
                      tv.setText(documentSnapshot.getString("notice"));
                      ts.setText(documentSnapshot.getString("rule"));
                  }else{
                      Toast.makeText(getContext(), "Failed to fetch data", Toast.LENGTH_SHORT).show();
                  }
              }
          });


       binding.transaction.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               startActivity(new Intent(getContext(), TransactionActivity.class));
           }
       });

       // request money
        binding.sendRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(Upi.getText().toString())) {
                    Upi.setError("Please enter Upi ID");
                    return;
                }else {
                    if (user.getCoins() > 1000) {
                        String uid = FirebaseAuth.getInstance().getUid();
                        String upi = binding.upiBox.getText().toString().trim();
                   //     String id = String.valueOf(System.currentTimeMillis());
                        WithdrawRequest request = new WithdrawRequest(uid, upi, user.getName(), "Pending","₹10", "");
                        database
                                .collection("withdraws")
                                .document("all")
                                .collection("Recipes")
                                .document()
                                .set(request).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                database.collection("users")
                                        .document(uid)
                                        .update("coins", FieldValue.increment(-1000));

                                startActivity(new Intent(getContext(), MainActivity.class));
                                Toast.makeText(getContext(), "Request sent successfully.", Toast.LENGTH_SHORT).show();
                            }
                        });
                        binding.upiBox.getText().clear();
                          database.collection("withdraws")
                                  .document(uid)
                                  .collection("own")
                                  .document()
                                  .set(request).addOnSuccessListener(new OnSuccessListener<Void>() {
                              @Override
                              public void onSuccess(Void unused) {

                              }
                          });
                    } else {
                        Toast.makeText(getContext(), "You need more coins to get withdraw.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        return binding.getRoot();
    }
}