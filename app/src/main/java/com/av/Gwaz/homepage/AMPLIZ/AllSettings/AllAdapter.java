package com.av.Gwaz.homepage.AMPLIZ.AllSettings;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.av.Gwaz.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AllAdapter extends RecyclerView.Adapter<AllAdapter.AllViewHolder> {

    private List<AllGet> ampList;
    private DatabaseReference databaseReference;
    private StorageReference storageReference;
    private OnItemClickListener listener;

    public AllAdapter(List<AllGet> ampList, DatabaseReference databaseReference, StorageReference storageReference, OnItemClickListener listener) {
        this.ampList = ampList;
        this.databaseReference = databaseReference;
        this.storageReference = storageReference;
        this.listener = listener;
    }

    @NonNull
    @Override
    public AllViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.all, parent, false);
        return new AllViewHolder(view);
    }

    public interface OnItemClickListener {
        void onItemClick(AllGet item);
    }

    public void filterList(List<AllGet> filteredList) {
        ampList = filteredList;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull AllViewHolder holder, int position) {
        AllGet allGet = ampList.get(position);

        holder.setName.setText(allGet.getSetName());
        holder.userN.setText("By: " + allGet.getBy());

        Picasso.get().load(allGet.getImageUrl()) // Pass the image URL from step.getT2()
                .into(holder.playPic); // Load into the ImageView

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(allGet);
            }
        });

    }

    @Override
    public int getItemCount() {
        return ampList.size();
    }



    public class AllViewHolder extends RecyclerView.ViewHolder {

        TextView setName, userN;
        ImageView playPic,editIcon,deleteIcon;
        public AllViewHolder(@NonNull View itemView) {
            super(itemView);
            setName = itemView.findViewById(R.id.setName);
            userN = itemView.findViewById(R.id.userN);
            playPic = itemView.findViewById(R.id.playPic);
            editIcon = itemView.findViewById(R.id.editIcon);
            deleteIcon = itemView.findViewById(R.id.deleteIcon);

            deleteIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        // Get the item at the clicked position
                        AllGet clickedItem = ampList.get(position);
                        // Assuming you have a method to get the unique identifier of the item (e.g., getKey())
                        String itemId = clickedItem.getSetName();

                        // Create and show an AlertDialog to confirm deletion
                        AlertDialog.Builder builder = new AlertDialog.Builder(itemView.getContext());
                        builder.setTitle("Confirm Deletion");
                        builder.setMessage("Are you sure you want to delete this item?");
                        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // Remove the item from the database
                                // Assuming databaseReference is your Firebase Realtime Database reference
                                databaseReference.child("Key"+itemId).removeValue();
                                // Get a reference to the folder in Firebase Storage
                                StorageReference folderRef = storageReference.child("Key"+itemId); // Update the path to your folder

                                // Delete the folder and its contents
                                deleteFolderContents(folderRef);

                                // Optionally, remove the item from the list displayed in the RecyclerView
                                ampList.remove(position);
                                notifyItemRemoved(position);
                            }
                        });
                        builder.setNegativeButton("Cancel", null);
                        builder.show();
                    }
                }
            });





            editIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        // Get the item at the clicked position
                        AllGet clickedItem = ampList.get(position);
                        // Assuming you have a method to get the unique identifier of the item (e.g., getKey())
                        String itemId = clickedItem.getKey();

                        ProgressDialog progressDialog = new ProgressDialog(view.getContext());
                        progressDialog.setMessage("Loading...");
                        progressDialog.show();

                        // Assuming AllSettings is the context where you are using AllAdapter
                        DatabaseReference itemRef = databaseReference.child(itemId);
                        itemRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                // Retrieve data from "Settings" and "Effects" folders
                                DataSnapshot settingsSnapshot = dataSnapshot.child("Settings");
                                DataSnapshot effectsSnapshot = dataSnapshot.child("Effects");

                                // Retrieve settings data
                                String treble = settingsSnapshot.child("treble").getValue(String.class);
                                String gain = settingsSnapshot.child("gain").getValue(String.class);
                                String bass = settingsSnapshot.child("bass").getValue(String.class);
                                String drive = settingsSnapshot.child("drive").getValue(String.class);
                                String mid = settingsSnapshot.child("mid").getValue(String.class);
                                String presence = settingsSnapshot.child("presence").getValue(String.class);
                                String reverb = settingsSnapshot.child("reverb").getValue(String.class);
                                String tone = settingsSnapshot.child("tone").getValue(String.class);
                                String gainstage = settingsSnapshot.child("gainstage").getValue(String.class);


                                // Retrieve effects data
                                String overdrive = String.valueOf(effectsSnapshot.child("overdrive").getValue(Boolean.class));
                                String distortion = String.valueOf(effectsSnapshot.child("distortion").getValue(Boolean.class));
                                String fuzz = String.valueOf(effectsSnapshot.child("fuzz").getValue(Boolean.class));
                                String delay = String.valueOf(effectsSnapshot.child("delay").getValue(Boolean.class));
                                String reverb1 = String.valueOf(effectsSnapshot.child("reverb1").getValue(Boolean.class));
                                String chorus = String.valueOf(effectsSnapshot.child("chorus").getValue(Boolean.class));
                                String flanger = String.valueOf(effectsSnapshot.child("flanger").getValue(Boolean.class));
                                String phaser = String.valueOf(effectsSnapshot.child("phaser").getValue(Boolean.class));
                                String tremolo = String.valueOf(effectsSnapshot.child("tremolo").getValue(Boolean.class));
                                String wah = String.valueOf(effectsSnapshot.child("wah").getValue(Boolean.class));
                                String compressor = String.valueOf(effectsSnapshot.child("compressor").getValue(Boolean.class));


                                // Start the AmpView activity with the retrieved data
                                Intent intent = new Intent(view.getContext(), AllEdit.class);
                                intent.putExtra("setName", clickedItem.getSetName());
                                intent.putExtra("by", clickedItem.getBy());
                                intent.putExtra("imageUrl", clickedItem.getImageUrl());
                                intent.putExtra("audioUrl", clickedItem.getAudioUrl());
                                intent.putExtra("ampUsed", clickedItem.getAmpUsed());
                                intent.putExtra("description", clickedItem.getDescription());
                                intent.putExtra("genre", clickedItem.getGenre());
                                intent.putExtra("key",clickedItem.getKey());

                                // Pass the retrieved settings and effects data to the intent
                                intent.putExtra("bass", bass);
                                intent.putExtra("drive", drive);
                                intent.putExtra("gain", gain);
                                intent.putExtra("gainstage", gainstage);
                                intent.putExtra("mid", mid);
                                intent.putExtra("presence", presence);
                                intent.putExtra("reverb", reverb);
                                intent.putExtra("tone", tone);
                                intent.putExtra("treble", treble);

                                // Pass other settings similarly
                                intent.putExtra("chorus", chorus);
                                intent.putExtra("compressor", compressor);
                                intent.putExtra("delay", delay);
                                intent.putExtra("distortion", distortion);
                                intent.putExtra("flanger", flanger);
                                intent.putExtra("fuzz", fuzz);
                                intent.putExtra("overdrive", overdrive);
                                intent.putExtra("phaser", phaser);
                                intent.putExtra("reverb1", reverb1);
                                intent.putExtra("tremolo", tremolo);
                                intent.putExtra("wah", wah);


                                view.getContext().startActivity(intent);
                                progressDialog.dismiss();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                Log.e(TAG, "Error fetching data: " + databaseError.getMessage());
                                progressDialog.dismiss();
                            }
                        });
                    }
                }
            });

        }


    }

    // Function to delete a folder and its contents recursively
    private void deleteFolderContents(StorageReference ref) {
        ref.listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {
            @Override
            public void onSuccess(ListResult listResult) {
                for (StorageReference item : listResult.getItems()) {
                    item.delete();
                }
                for (StorageReference prefix : listResult.getPrefixes()) {
                    deleteFolderContents(prefix);
                }
                ref.delete();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e(TAG, "Error deleting folder contents", e);
            }
        });
    }
}