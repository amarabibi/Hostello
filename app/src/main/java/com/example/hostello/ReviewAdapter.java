package com.example.hostello;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {

    private Context context;
    private List<ReviewModel> reviews;

    public ReviewAdapter(Context context, List<ReviewModel> reviews) {
        this.context = context;
        this.reviews = reviews;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_review, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ReviewModel review = reviews.get(position);
        holder.name.setText(review.getName());
        holder.date.setText(review.getDate());
        holder.rating.setText("⭐ " + review.getRating());
        holder.comment.setText(review.getComment());
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, date, rating, comment;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.reviewerName);
            date = itemView.findViewById(R.id.reviewDate);
            rating = itemView.findViewById(R.id.reviewRating);
            comment = itemView.findViewById(R.id.reviewComment);
        }
    }
}
