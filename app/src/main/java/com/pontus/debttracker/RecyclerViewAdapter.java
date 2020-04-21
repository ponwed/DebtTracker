package com.pontus.debttracker;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>
{
    private static final String TAG = "RecyclerViewAdapter";
    private ArrayList<DebtCard> mDebtCards;
    private Context mContext;

    public RecyclerViewAdapter(ArrayList<DebtCard> mDebtCards, Context mContext)
    {
        this.mDebtCards = mDebtCards;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_listitem, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position)
    {
        Log.d(TAG, "onBindViewHolder: called");

        holder.debtor.setText(mDebtCards.get(position).debtor);
        holder.description.setText(mDebtCards.get(position).description);
        holder.debt.setText(mDebtCards.get(position).debt);
        holder.date.setText(mDebtCards.get(position).date);

        // Setting text and debt color depending on which direction the debt is
        if (mDebtCards.get(position).owed)
        {
            holder.owed.setText(mContext.getResources().getString(R.string.im_owed));
            holder.debt.setTextColor(ContextCompat.getColor(mContext, R.color.yourDebt));
        }
        else
        {
            holder.owed.setText(mContext.getResources().getString(R.string.i_owe));
            holder.debt.setTextColor(ContextCompat.getColor(mContext, R.color.myDebt));
        }
    }

    @Override
    public int getItemCount()
    {
        return mDebtCards.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView debtor;
        TextView description;
        TextView debt;
        TextView date;
        TextView owed;
        RelativeLayout parentLayout;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            debtor = itemView.findViewById(R.id.debtor);
            description = itemView.findViewById(R.id.desc);
            debt = itemView.findViewById(R.id.debt);
            date = itemView.findViewById(R.id.date);
            owed = itemView.findViewById(R.id.owed);
            parentLayout = itemView.findViewById(R.id.parent_layout);
        }
    }
}
