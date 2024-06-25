package com.av.Gwaz.homepage.GWIZ;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.av.Gwaz.R;
import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.StorageReference;

import java.util.List;

public class PartAdapter extends RecyclerView.Adapter<PartAdapter.StepViewHolder>{


    private List<PartGet> stepList;
    private DatabaseReference databaseReference; // Add this field
    private StorageReference storageReference;
    private Context context;



    public PartAdapter(List<PartGet> stepList, DatabaseReference databaseReference, StorageReference storageReference, Context context) {
        this.stepList = stepList;
        this.databaseReference = databaseReference;
        this.storageReference = storageReference;
        this.context = context;
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
        holder.creditLink.setText(step.getT4());
        // using Glide not picasso
        Glide.with(holder.itemView.getContext())
                .load(step.getT2()) // Pass the GIF URL from step.getT2()
                .into(holder.digiPic); // Load into the ImageView

        // Set long click listener on creditLink
        holder.creditLink.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                // Get the text from creditLink
                String link = holder.creditLink.getText().toString();
                // Copy the text to clipboard
                ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Credit Link", link);
                clipboard.setPrimaryClip(clip);
                // Show a toast message indicating that the link has been copied
                Toast.makeText(context, "Credit link copied to clipboard", Toast.LENGTH_SHORT).show();
                return true;
            }
        });

    }

    @Override
    public int getItemCount() {
        return stepList.size();
    }

    static class StepViewHolder extends RecyclerView.ViewHolder {
        TextView textStep,textm,creditLink;
        ImageView digiPic,deleteIcon, editIcon;

        public StepViewHolder(@NonNull View itemView) {
            super(itemView);
            textStep = itemView.findViewById(R.id.step);
            textm = itemView.findViewById(R.id.mtext);
            digiPic = itemView.findViewById(R.id.digiPic);
            creditLink = itemView.findViewById(R.id.creditLink);

        }
    }
}
