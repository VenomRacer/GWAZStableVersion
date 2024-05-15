package com.example.admincms.selection.GWIZ.Parts;

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

import com.bumptech.glide.Glide;
import com.example.admincms.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.StorageReference;

import java.util.List;


public class PartAdapter extends RecyclerView.Adapter<PartAdapter.StepViewHolder> {

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
        holder.link.setText(step.getT4());
        // Inside onBindViewHolder method
        Glide.with(holder.itemView.getContext())
                .load(step.getT2()) // Pass the GIF URL from step.getT2()
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
                                    DatabaseReference itemRef = databaseReference.child(step.getT3()); // Assuming t3 contains the key of the item in the database
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

                StorageReference imageRef = storageReference;
                DatabaseReference databref = databaseReference;

                // Start EditActivity
                Intent intent = new Intent(holder.itemView.getContext(), Edit.class);
                // Pass any necessary data to EditActivity using intent extras
                // For example, you can pass step.getT3() as the key to retrieve data in EditActivity
                intent.putExtra("StepName", step.getT3());
                intent.putExtra("Description", step.getT1());
                intent.putExtra("Link", step.getT4());
                intent.putExtra("Img", step.getT2());
                intent.putExtra("trastorage", imageRef.toString());
                intent.putExtra("trastring", databref.toString());
                holder.itemView.getContext().startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return stepList.size();
    }

    static class StepViewHolder extends RecyclerView.ViewHolder {
        TextView textStep,textm,link;
        ImageView digiPic,deleteIcon, editIcon;

        public StepViewHolder(@NonNull View itemView) {
            super(itemView);
            textStep = itemView.findViewById(R.id.step);
            textm = itemView.findViewById(R.id.mtext);
            digiPic = itemView.findViewById(R.id.digiPic);
            deleteIcon = itemView.findViewById(R.id.deleteIcon);
            editIcon = itemView.findViewById(R.id.editIcon);
            link = itemView.findViewById(R.id.link);
        }
    }
}