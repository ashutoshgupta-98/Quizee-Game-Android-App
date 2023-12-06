package com.quiz.quizeegame.Signup;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.quiz.quizeegame.MainActivity;
import com.quiz.quizeegame.PrivacyPolicyActivity;
import com.quiz.quizeegame.R;
import com.quiz.quizeegame.User;
import com.quiz.quizeegame.databinding.ActivitySignupBinding;

public class SignupActivity extends AppCompatActivity {


    ActivitySignupBinding binding;
    FirebaseAuth auth;
    FirebaseFirestore database;
    ProgressDialog dialog;
    TextView textView1;
    TextView textView2;
    TextView textView3;
    TextView textView4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        auth = FirebaseAuth.getInstance();
        database = FirebaseFirestore.getInstance();

        textView1 = binding.emailBox.findViewById(R.id.emailBox);
        textView2 = binding.passwordBox.findViewById(R.id.passwordBox);
        textView3 = binding.nameBox.findViewById(R.id.nameBox);
//        textView4 = binding.referBox.findViewById(R.id.referBox);

        dialog = new ProgressDialog(this);
        dialog.setMessage("We're creating new account...");

        binding.submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email, pass, name, referCode;

                email = binding.emailBox.getText().toString();
                if (TextUtils.isEmpty(email)){
                    textView1.setError("Enter your email");
                    return;
                }

                pass = binding.passwordBox.getText().toString();
                if (TextUtils.isEmpty(pass)){
                    textView2.setError("Enter your password");
                    return;
                }
                name = binding.nameBox.getText().toString();
                if (TextUtils.isEmpty(name)){
                    textView3.setError("Enter your name");
                    return;
                }

                String refer = name.substring(0,4);
                referCode = refer.replace(".","");

//                referCode = binding.referBox.getText().toString();
//                if (TextUtils.isEmpty(binding.referBox.getText().toString())){
//                    textView4.setError("Enter your Code");
//                    return;
//                }
//                if (referCode.equals(binding.referBox.getText().toString())) {
//                    Toast.makeText(SignupActivity.this, "You can not input your own code", Toast.LENGTH_SHORT).show();
//                    return;
//                }

                User user = new User(name, email, pass, referCode);
                dialog.show();
                auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            String uid = task.getResult().getUser().getUid();
                            database
                                    .collection("users")
                                    .document(uid)
                                    .set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                   if(task.isSuccessful()){
                                       dialog.dismiss();
                                       startActivity(new Intent(SignupActivity.this, MainActivity.class));
                                       finish();
                                   }else {
                                       Toast.makeText(SignupActivity.this,task.getException().getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                                   }
                                }
                            });
                        }else {
                            dialog.dismiss();
                            Toast.makeText(SignupActivity.this,task.getException().getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

          binding.loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                finish();
            }
        });

          binding.privacypolicy.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                  startActivity(new Intent(SignupActivity.this, PrivacyPolicyActivity.class));
              }
          });
    }
}