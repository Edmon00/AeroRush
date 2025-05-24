package com.EdmonAntonyan.aerorush;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class LeaderboardAdapter extends RecyclerView.Adapter<LeaderboardAdapter.ViewHolder> {

    private List<UserScore> userScores;

    public LeaderboardAdapter(List<UserScore> userScores) {
        this.userScores = userScores;
    }

    public void setScores(List<UserScore> userScores) {
        this.userScores = userScores;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.score_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        UserScore score = userScores.get(position);
        holder.nameTextView.setText(score.getName());
        holder.pointsTextView.setText(String.valueOf(score.getPoints()));
    }

    @Override
    public int getItemCount() {
        return userScores.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView, pointsTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.score_name);
            pointsTextView = itemView.findViewById(R.id.score_points);
        }
    }
}