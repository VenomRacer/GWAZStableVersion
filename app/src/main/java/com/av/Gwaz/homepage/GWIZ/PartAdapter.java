package com.av.Gwaz.homepage.GWIZ;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.av.Gwaz.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PartAdapter extends RecyclerView.Adapter<PartAdapter.StepViewHolder>{


    private List<PartGet> stepList;
    private DatabaseReference databaseReference; // Add this field
    private StorageReference storageReference;


    public PartAdapter(List<PartGet> stepList, DatabaseReference databaseReference, StorageReference storageReference) {
        this.stepList = stepList;
        this.databaseReference = databaseReference;
        this.storageReference = storageReference;
    }

    @NonNull
    @Override
    public StepViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.part, parent, false);
        return new StepViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StepViewHolder holder, int position) {
        PartGet step = stepList.get(position);
        holder.textm.setText(step.getT1());
        holder.textStep.setText(step.getT3());
        // Load image using Picasso
        Picasso.get().load(step.getT2()) // Pass the image URL from step.getT2()
                .into(holder.digiPic); // Load into the ImageView

    }

    @Override
    public int getItemCount() {
        return stepList.size();
    }

    static class StepViewHolder extends RecyclerView.ViewHolder {
        TextView textStep,textm;
        ImageView digiPic,deleteIcon, editIcon;

        public StepViewHolder(@NonNull View itemView) {
            super(itemView);
            textStep = itemView.findViewById(R.id.step);
            textm = itemView.findViewById(R.id.mtext);
            digiPic = itemView.findViewById(R.id.digiPic);

        }
    }
}
