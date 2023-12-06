package com.quiz.quizeegame;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.quiz.quizeegame.databinding.ActivityInviteBinding;

public class InviteActivity extends AppCompatActivity {

    ActivityInviteBinding binding;
    private FirebaseUser user;
    private  String oppositeUID;
    private TextView referCodeTv;
    private Button sharebtn, redeemBtn;
    private Uri imageUri;
    DatabaseReference reference;
    DocumentReference documentReference;
    FirebaseAuth auth;
    FirebaseFirestore database;
    int coin =50;
    User user1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityInviteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        AdRequest adRequest = new AdRequest.Builder().build();
        binding.adView3.loadAd(adRequest);

        binding.adView3.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
//                Toast.makeText(InviteActivity.this, "Ad is loaded.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdClicked() {
                super.onAdClicked();
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
                binding.adView3.loadAd(adRequest);
            }
        });

        auth = FirebaseAuth.getInstance();
        database = FirebaseFirestore.getInstance();

        init();
        reference = FirebaseDatabase.getInstance().getReference().child("Users");
//        redeemAvailability();
        clickListener();



//        database.collection("users")
//                .document(FirebaseAuth.getInstance().getCurrentUser().getUid())
//                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//            @Override
//            public void onSuccess(DocumentSnapshot documentSnapshot) {
//                user1 = documentSnapshot.toObject(User.class);
//                binding.referCodeTv.setText(String.valueOf(R.id.referCodeTv));
//                redeemAvailability();
//            }
//        });

        documentReference =  database.collection("users").document(user.getUid());
        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists())
                {
                    binding.referCodeTv.setText(documentSnapshot.getString("referCode"));
                }else{
                    Toast.makeText(InviteActivity.this, "Failed to fetch data", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
//    private void redeemAvailability() {
//                     if (){
//                         redeemBtn.setEnabled(false);
//                     }


//                redeemBtn.setEnabled(true);
//           redeemBtn.setVisibility(View.VISIBLE);
//        redeemBtn.setVisibility(View.GONE);

//

//    }
    private  void init(){
        referCodeTv = findViewById(R.id.referCodeTv);
        sharebtn = findViewById(R.id.sharebtn);
//        redeemBtn =findViewById(R.id.redeembtn);

        FirebaseAuth auth  = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
    }


    private void clickListener(){
        sharebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String referCode = binding.referCodeTv.getText().toString();
                String shareBody = "Hey, I am using the best earning app. Join using my invite code to get instant 50"
                        +" coins. My Share Code is "+referCode+"\n"+
                        "Download from Play Store\n"+
                        "https://play.google.com/store/apps/details?id="+
                        getPackageName();

                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, shareBody);
                startActivity(intent);
                coin();
                finish();
            }
        });


//        redeemBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                EditText editText = new EditText(InviteActivity.this);
//                editText.setHint("abc12");
//
//                LinearLayout.LayoutParams layoutParams= new LinearLayout.LayoutParams(
//                        LinearLayout.LayoutParams.MATCH_PARENT,
//                        LinearLayout.LayoutParams.MATCH_PARENT);
//                editText.setLayoutParams(layoutParams);
//                AlertDialog.Builder alertDialog = new AlertDialog.Builder(InviteActivity.this);
//                alertDialog.setTitle("Redeem Code");
//                alertDialog.setView(editText);
//                alertDialog.setPositiveButton("Redeem", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                       String inputCode = editText.getText().toString();
//                       if (TextUtils.isEmpty(inputCode)){
//                           Toast.makeText(InviteActivity.this, "Input valid code", Toast.LENGTH_SHORT).show();
//                           return;
//                       }
//                       if (inputCode.equals(binding.referCodeTv.getText().toString())){
//                           Toast.makeText(InviteActivity.this, "You can not input your own code", Toast.LENGTH_SHORT).show();
//                       }
//                       redeemQuery(inputCode, dialogInterface);
//                    }
//                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        dialogInterface.dismiss();
//                    }
//                });
//                alertDialog.show();
//            }
//        });
    }
//    private void redeemQuery(String inputCode, final DialogInterface dialogInterface){
//                database.collection("users")
//                .document(FirebaseAuth.getInstance().getCurrentUser().getUid())
//                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//            @Override
//            public void onSuccess(DocumentSnapshot documentSnapshot) {
//               user1 =  documentSnapshot.toObject(User.class);
//                binding.referCodeTv.setText(String.valueOf(R.id.referCodeTv));
//                coin();
//                dialogInterface.dismiss();
//                Toast.makeText(InviteActivity.this, "congrats and coin added", Toast.LENGTH_SHORT).show();
//                redeemAvailability();
//            }
//        });
//    }


    public void coin(){
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        database.collection("users")
                .document(FirebaseAuth.getInstance().getUid())
                .update("coins", FieldValue.increment(coin));
    }
}
