package com.example.android.verticalnestedrecyclerview;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import static com.example.android.verticalnestedrecyclerview.MainActivity.LOG_TAG;

/*
 * By: M7md.zain@gamil.com
 * 04.July.2021
 * */
public class OuterRecyclerAdapter extends RecyclerView.Adapter<OuterRecyclerAdapter.OuterViewHolder> {

    private final List<Month> mMonths;

    // Tracking the currently loaded items in the RecyclerView
    private final ArrayList<Integer> currentLoadedPositions = new ArrayList<>();

    OuterRecyclerAdapter(List<Month> months) {
        this.mMonths = months;
    }

    @NonNull
    @Override
    public OuterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View listItem = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view_row_outer, parent, false);
        return new OuterViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull OuterViewHolder holder, int position) {
        Month month = mMonths.get(position);
        holder.tvMonth.setText(month.getName());
        holder.innerRecyclerAdapter.setDays(month.getDayCount());
        currentLoadedPositions.add(position);
        Log.d(LOG_TAG, "onViewRecycled: OUTER: " + currentLoadedPositions);
    }

    @Override
    public int getItemCount() {
        return mMonths.size();
    }

    static class OuterViewHolder extends RecyclerView.ViewHolder {

        private final TextView tvMonth;
        private final InnerRecyclerAdapter innerRecyclerAdapter;

        OuterViewHolder(@NonNull View listItem) {
            super(listItem);
            tvMonth = listItem.findViewById(R.id.tv_month);

            // Setting up the inner RecyclerView
            RecyclerView innerRecyclerView = listItem.findViewById(R.id.recycler_view_inner);
            innerRecyclerView.setLayoutManager(new LinearLayoutManager(listItem.getContext()));
            innerRecyclerAdapter = new InnerRecyclerAdapter();
            innerRecyclerView.setHasFixedSize(true);
            innerRecyclerView.setAdapter(innerRecyclerAdapter);

        }

    }

    @Override
    public void onViewRecycled(@NonNull OuterViewHolder holder) {
        super.onViewRecycled(holder);
        currentLoadedPositions.remove(Integer.valueOf(holder.getAdapterPosition()));
        Log.d(LOG_TAG, "onViewRecycled: OUTER: " + currentLoadedPositions);
    }


}