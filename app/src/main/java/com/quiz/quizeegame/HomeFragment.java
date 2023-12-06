package com.quiz.quizeegame;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.quiz.quizeegame.databinding.FragmentHomeBinding;

import java.util.ArrayList;


public class HomeFragment extends Fragment {


    public HomeFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    FragmentHomeBinding binding;
    FirebaseFirestore database;
    private RewardedAd mRewardedAd;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
          rewAds();
        database = FirebaseFirestore.getInstance();

      final ArrayList<CategoryModel> categories = new ArrayList<>();

      final CategoryAdapter adapter = new CategoryAdapter(getContext(), categories);

        database.collection("categories")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        categories.clear();
                        assert value != null;
                        for(DocumentSnapshot snapshot : value.getDocuments()){
                            CategoryModel model = snapshot.toObject(CategoryModel.class);
                            assert model != null;
                            model.setCategoryId(snapshot.getId());
                            categories.add(model);
                        }
                       adapter.notifyDataSetChanged();
                    }
                });

        binding.categoryList.setLayoutManager(new GridLayoutManager(getContext(), 2));
        binding.categoryList.setAdapter(adapter);

        binding.spinwheel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mRewardedAd != null) {
//                    startActivity(new Intent(getContext(), SpinnerActivity.class));
                    rewAds();
                    mRewardedAd.show(requireActivity(), new OnUserEarnedRewardListener() {
                        @Override
                        public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                            rewardItem.getAmount();
                            startActivity(new Intent(getContext(), SpinnerActivity.class));
 //                           int rewardAmount = rewardItem.getAmount();
//                            String rewardType = rewardItem.getType();
                            mRewardedAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                                @Override
                                public void onAdDismissedFullScreenContent() {
                                    super.onAdDismissedFullScreenContent();
                                    mRewardedAd = null;
//                                binding.spinwheel.getContext().startActivity(new Intent(binding.spinwheel.getContext(),MainActivity.class));
                               rewAds();
                                }
                            });
                        }
                    });
                } else {
                    Toast.makeText(getContext(), "Sorry,No Ad is available", Toast.LENGTH_SHORT).show();
//                    binding.spinwheel.getContext().startActivity(new Intent(binding.spinwheel.getContext(),MainActivity.class));
                }
            }
        });

        binding.invitefriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    startActivity(new Intent(getContext(), InviteActivity.class));
            }
        });

        // Inflate the layout for this fragment
        return binding.getRoot();

    }
    private void rewAds(){
        AdRequest adRequest = new AdRequest.Builder().build();

        RewardedAd.load(requireContext(), "ca-app-pub-6301220023928445/2156212593",
                adRequest, new RewardedAdLoadCallback() {
                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error.
                        mRewardedAd = null;
                    }

                    @Override
                    public void onAdLoaded(@NonNull RewardedAd rewardedAd) {
                        mRewardedAd = rewardedAd;
                    }
                });
    }
}
//ca-app-pub-6301220023928445/2156212593