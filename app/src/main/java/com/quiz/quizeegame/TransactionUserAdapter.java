package com.quiz.quizeegame;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TransactionUserAdapter extends  RecyclerView.Adapter<TransactionUserAdapter.myviewholder> {

    ArrayList<WithdrawRequest> withdrawRequests;

    public TransactionUserAdapter(ArrayList<WithdrawRequest> withdrawRequests) {
        this.withdrawRequests = withdrawRequests;
    }

    @NonNull
    @Override
    public TransactionUserAdapter.myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_transaction, parent, false);
        return new TransactionUserAdapter.myviewholder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull TransactionUserAdapter.myviewholder holder, int position) {
        holder.t1.setText(withdrawRequests.get(position).getRequestedBy());
        holder.t2.setText(withdrawRequests.get(position).getCreatedAt().toString());
        holder.t3.setText(withdrawRequests.get(position).getStatusBy());
        holder.t4.setText(withdrawRequests.get(position).getCoinBy());
        holder.t5.setText(withdrawRequests.get(position).getStatusReject());
    }

    @Override
    public int getItemCount() {
        return withdrawRequests.size();
    }

    static class myviewholder extends RecyclerView.ViewHolder {
        TextView t1, t2, t3, t4, t5;

        public myviewholder(@NonNull View itemView) {
            super(itemView);
            t1 = itemView.findViewById(R.id.names);
            t2 = itemView.findViewById(R.id.time);
            t3 = itemView.findViewById(R.id.status);
            t4 = itemView.findViewById(R.id.coins);
            t5 = itemView.findViewById(R.id.statusReject);
        }
    }
}