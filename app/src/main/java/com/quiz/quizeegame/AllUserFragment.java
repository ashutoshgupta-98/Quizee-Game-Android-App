package com.quiz.quizeegame;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.quiz.quizeegame.databinding.FragmentAllUserBinding;
import com.quiz.quizeegame.databinding.FragmentLeaderboardsBinding;

import java.util.ArrayList;
import java.util.List;


public class AllUserFragment extends Fragment {


    public AllUserFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    FragmentAllUserBinding binding;
    FirebaseFirestore database;
    RecyclerView recyclerView;
    TransactionAdapter transactionAdapter;
    ArrayList<WithdrawRequest> withdrawRequests;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAllUserBinding.inflate(inflater, container,false);

        recyclerView = binding.recyclerview.findViewById(R.id.recyclerview);
//        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        withdrawRequests = new ArrayList<>();
        transactionAdapter = new TransactionAdapter(withdrawRequests);
        recyclerView.setAdapter(transactionAdapter);

        database = FirebaseFirestore.getInstance();
//        String uid = FirebaseAuth.getInstance().getUid();
//        String id = String.valueOf(System.currentTimeMillis());
        database.collection("withdraws")
                .document("all")
                .collection("Recipes")
                .orderBy("createdAt", Query.Direction.DESCENDING)
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                        for (DocumentSnapshot d:list)
                        {
                            WithdrawRequest obj = d.toObject(WithdrawRequest.class);
                            withdrawRequests.add(obj);
                        }
                        transactionAdapter.notifyDataSetChanged();
                    }
                });

        return binding.getRoot();
    }
}