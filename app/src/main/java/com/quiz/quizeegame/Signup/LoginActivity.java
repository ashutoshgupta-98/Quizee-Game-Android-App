package com.quiz.quizeegame.Signup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.quiz.quizeegame.MainActivity;
import com.quiz.quizeegame.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.quiz.quizeegame.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding binding;
    FirebaseAuth auth;
    ProgressDialog dialog;
    private TextView forgotPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        forgotPassword = (TextView) findViewById(R.id.privacypolicy);
        auth = FirebaseAuth.getInstance();

        dialog = new ProgressDialog(this);
        dialog.setMessage("Logging in...");

        if(auth.getCurrentUser() != null){
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }

       binding.loginBtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               String email, pass;
               email = binding.emailBox.getText().toString();
               pass = binding.passwordBox.getText().toString();

               dialog.show();

               auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                   @Override
                   public void onComplete(@NonNull Task<AuthResult> task) {
                       dialog.dismiss();
                       if(task.isSuccessful()){
                           startActivity(new Intent(LoginActivity.this, MainActivity.class));
                           finish();
                       }else{
                           Toast.makeText(LoginActivity.this, task.getException().getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                       }
                   }
               });
           }
       });

       binding.submitBtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               startActivity(new Intent(LoginActivity.this, SignupActivity.class));
               finish();
           }
       });

       forgotPassword.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               startActivity(new Intent(LoginActivity.this, PasswordActivity.class));
           }
       });
    }
}