package com.quiz.quizeegame;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.DocumentsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.quiz.quizeegame.databinding.FragmentAllUserBinding;
import com.quiz.quizeegame.databinding.FragmentUserTransactionBinding;

import java.util.ArrayList;
import java.util.List;


public class UserTransactionFragment extends Fragment {

    public UserTransactionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    FragmentUserTransactionBinding binding;
    FirebaseAuth auth;
    FirebaseFirestore database;
    RecyclerView recyclerView;
    TransactionUserAdapter transactionUserAdapter;
    ArrayList<WithdrawRequest> withdrawRequests;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       binding = FragmentUserTransactionBinding.inflate(inflater,container,false);
        recyclerView = binding.recyclerview1.findViewById(R.id.recyclerview1);
//        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        withdrawRequests = new ArrayList<>();
        transactionUserAdapter = new TransactionUserAdapter(withdrawRequests);
        recyclerView.setAdapter(transactionUserAdapter);
        auth = FirebaseAuth.getInstance();
        database = FirebaseFirestore.getInstance();
        String uid = FirebaseAuth.getInstance().getUid();
        database.collection("withdraws")
                .document(uid)
                .collection("own")
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
                transactionUserAdapter.notifyDataSetChanged();
            }
        });

        return binding.getRoot();
    }
}