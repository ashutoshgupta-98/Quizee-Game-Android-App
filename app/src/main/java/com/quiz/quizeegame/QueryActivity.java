package com.quiz.quizeegame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.quiz.quizeegame.databinding.ActivityQueryBinding;

import java.util.HashMap;
import java.util.Map;

public class QueryActivity extends AppCompatActivity {

    ActivityQueryBinding binding;
    FirebaseAuth auth;
    FirebaseFirestore database;
    TextView msg;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       binding = ActivityQueryBinding.inflate(getLayoutInflater());
       setContentView(binding.getRoot());

        auth = FirebaseAuth.getInstance();
        database = FirebaseFirestore.getInstance();

        msg = binding.messageBox.findViewById(R.id.messageBox);

        binding.sendBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(msg.getText().toString())){
                    msg.setError("Please enter your Query");
                    return;
                }else {
                    String uid = FirebaseAuth.getInstance().getUid();
                    String msg = binding.messageBox.getText().toString();
                    QuerySend send = new QuerySend(uid, msg);
                    database
                            .collection("message")
                            .document(uid)
                            .set(send).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(QueryActivity.this, "Send Successfully", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    });
//                Map<String,String> items = new HashMap<>();
//                items.put("messageBox",t1.getText().toString().trim());
//                 items.put("nameBox","");
//                database
//                        .collection("message")
//                        .add(items)
//                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//                            @Override
//                            public void onSuccess(DocumentReference documentReference) {
//                              t1.setText("");
//
//                                Toast.makeText(QueryActivity.this, "Inserted Successfully", Toast.LENGTH_SHORT).show();
//                            }
//                        });
                }
            }
        });

    }
}