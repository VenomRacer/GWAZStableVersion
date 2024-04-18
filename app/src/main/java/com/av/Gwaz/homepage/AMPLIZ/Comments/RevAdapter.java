package com.av.Gwaz.homepage.AMPLIZ.Comments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.av.Gwaz.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RevAdapter extends RecyclerView.Adapter<RevAdapter.RevViewHolder> {

    private List<RegGet> reviewList;

    public RevAdapter(List<RegGet> reviewList) {
        this.reviewList = reviewList;
    }

    @NonNull
    @Override
    public RevViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rev, parent, false);
        return new RevViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RevViewHolder holder, int position) {
        RegGet review = reviewList.get(position);
        holder.bind(review);
    }

    @Override
    public int getItemCount() {
        return reviewList.size();
    }

    public static class RevViewHolder extends RecyclerView.ViewHolder {

        private TextView username;
        private TextView comment;
        private RatingBar ratingBar;
        private ImageView profilePic;

        public RevViewHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.username);
            comment = itemView.findViewById(R.id.comment);
            ratingBar = itemView.findViewById(R.id.ratingBar);
            profilePic = itemView.findViewById(R.id.profilePic);
        }

        public void bind(RegGet review) {
            username.setText(review.getUser());
            comment.setText("\"" + review.getComment() + "\"");
            ratingBar.setRating(review.getRating());

            Picasso.get().load(review.getProfilepic()).into(profilePic);
        }
    }
}