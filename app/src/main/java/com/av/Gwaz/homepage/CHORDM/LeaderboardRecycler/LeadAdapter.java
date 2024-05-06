package com.av.Gwaz.homepage.CHORDM.LeaderboardRecycler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.av.Gwaz.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class LeadAdapter extends RecyclerView.Adapter<LeadAdapter.LeadViewHolder> {

    private List<LeadGet> leadList;

    public LeadAdapter(List<LeadGet> leadList) {
        this.leadList = leadList;
    }

    @NonNull
    @Override
    public LeadAdapter.LeadViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.clas, parent, false);
        return new LeadViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LeadViewHolder holder, int position) {
        LeadGet lead = leadList.get(position);
        holder.bind(lead);
    }

    @Override
    public int getItemCount() {
        return leadList.size();
    }

    public static class LeadViewHolder extends RecyclerView.ViewHolder {

        private TextView username;
        private TextView score;
        private ImageView profilePic;

        public LeadViewHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.username);
            score = itemView.findViewById(R.id.score);
            profilePic = itemView.findViewById(R.id.profilePic);
        }

        public void bind(LeadGet lead) {
            username.setText(lead.getUserName());
            score.setText(String.valueOf(lead.getScore()) + " pts");


            Picasso.get().load(lead.getProfilepic()).into(profilePic);
        }
    }
}
