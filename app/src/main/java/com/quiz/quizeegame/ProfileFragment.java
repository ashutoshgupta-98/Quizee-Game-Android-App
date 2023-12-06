package com.quiz.quizeegame;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.quiz.quizeegame.Signup.LoginActivity;
import com.quiz.quizeegame.databinding.FragmentProfileBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


public class ProfileFragment extends Fragment {

    FragmentProfileBinding binding;
    FirebaseAuth auth;
    FirebaseFirestore database;
    User user;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_profile, container, false);
        binding = FragmentProfileBinding.inflate(inflater, container, false);

        auth = FirebaseAuth.getInstance();
        database = FirebaseFirestore.getInstance();
//        databaseReference = firebaseDatabase.getInstance().getReference("Users");
//        storageReference = FirebaseStorage.getInstance().getReference();


        database.collection("users")
                .document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                user = documentSnapshot.toObject(User.class);
                binding.emailbox.setText(String.valueOf(user.getEmail()));
                binding.namebox.setText(String.valueOf(user.getName()));
            }
        });

        binding.policy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), PrivacyPolicyActivity.class));
            }
        });

         binding.logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auth = FirebaseAuth.getInstance();
                auth.signOut();
                startActivity(new Intent(getContext(), LoginActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            }
        });

         binding.queryBtn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 startActivity(new Intent(getContext(), QueryActivity.class));
             }
         });

        return binding.getRoot();
    }
}