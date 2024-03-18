package com.example.admincms.selection.GWIZ.Parts.Sub.Traditional;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.admincms.R;
import com.example.admincms.selection.GWIZ.Parts.Edit;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.List;


public class TraAdapter extends RecyclerView.Adapter<TraAdapter.StepViewHolder> {

    private List<TraGet> stepList;

    public TraAdapter(List<TraGet> stepList) {
        this.stepList = stepList;
    }

    @NonNull
    @Override
    public StepViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tra, parent, false);
        return new StepViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StepViewHolder holder, int position) {
        TraGet step = stepList.get(position);
        holder.textm.setText(step.getT1());
        holder.textStep.setText(step.getT3());
        // Load image using Picasso
        Picasso.get().load(step.getT2()) // Pass the image URL from step.getT2()
                .into(holder.digiPic); // Load into the ImageView

        // Set onClickListener for delete icon
        holder.deleteIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show confirmation dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.itemView.getContext());
                builder.setTitle("Confirmation")
                        .setMessage("Are you sure you want to delete this item?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // User confirmed deletion
                                int adapterPosition = holder.getAdapterPosition();
                                if (adapterPosition != RecyclerView.NO_POSITION) {
                                    // Remove the item from the list
                                    stepList.remove(adapterPosition);
                                    // Notify adapter of the item removal
                                    notifyItemRemoved(adapterPosition);

                                    // Remove the item from the Firebase database
                                    DatabaseReference itemRef = FirebaseDatabase.getInstance().getReference()
                                            .child("Service").child("GWIZ").child("Strings").child("TraditionalStrings")
                                            .child(step.getT3()); // Assuming t3 contains the key of the item in the database
                                    itemRef.removeValue();
                                }
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // User canceled deletion, do nothing
                            }
                        })
                        .show();
            }
        });

        // Set onClickListener for edit icon
        holder.editIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start EditActivity
                Intent intent = new Intent(holder.itemView.getContext(), Edit.class);
                // Pass any necessary data to EditActivity using intent extras
                // For example, you can pass step.getT3() as the key to retrieve data in EditActivity
                intent.putExtra("StepName", step.getT3());
                intent.putExtra("Description", step.getT1());
                intent.putExtra("Img", step.getT2());
                holder.itemView.getContext().startActivity(intent);
            }
        });


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
            deleteIcon = itemView.findViewById(R.id.deleteIcon);
            editIcon = itemView.findViewById(R.id.editIcon);
        }
    }
}