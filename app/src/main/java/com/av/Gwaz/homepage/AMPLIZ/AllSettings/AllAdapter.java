package com.av.Gwaz.homepage.AMPLIZ.AllSettings;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.av.Gwaz.R;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AllAdapter extends RecyclerView.Adapter<AllAdapter.AllViewHolder> {

    private List<AllGet> ampList;
    private DatabaseReference databaseReference;
    private StorageReference storageReference;
    private OnItemClickListener listener;
    private static MediaPlayer currentMediaPlayer = null;
    private static AllViewHolder currentViewHolder = null;

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
        holder.userN.setText(allGet.getBy());

        Float rating = allGet.getRating(); // Note that the type is Float, not float
        if (rating != null) {
            holder.ratingBar.setRating(rating);
        } else {
            holder.ratingBar.setRating(0); // Set a default rating value, e.g., 0
        }

        // Load the placeholder GIF with Glide
        Glide.with(holder.itemView.getContext())
                .asGif()
                .load(R.drawable.loading_yellow) // Resource ID of the GIF
                .into(holder.postprof);
        // Load the actual image with Picasso
        Picasso.get()
                .load(R.drawable.default_prof1)
                .into(holder.postprof, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {
                        // Clear the placeholder loaded by Glide
                        Glide.with(holder.itemView.getContext()).clear(holder.postprof);
                        FirebaseStorage storage = FirebaseStorage.getInstance();
                        StorageReference storageRef = storage.getReference();
                        StorageReference imageRef = storageRef.child("upload/" + allGet.getUid());
                        imageRef.getBytes(Long.MAX_VALUE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                            @Override
                            public void onSuccess(byte[] bytes) {
                                // Use BitmapFactory to decode the byte array to a bitmap
                                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                holder.postprof.setImageBitmap(bitmap);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                // Handle any errors that occurred while fetching the image
                            }
                        });
                    }

                    @Override
                    public void onError(Exception e) {
                        // Handle the error if needed
                    }
                });

        // Load the placeholder GIF with Glide
        Glide.with(holder.itemView.getContext())
                .asGif()
                .load(R.drawable.loading_yellow) // Resource ID of the GIF
                .into(holder.playPic);

        // Load the actual image with Picasso
        Picasso.get()
                .load(allGet.getImageUrl())
                .into(holder.playPic, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {
                        // Clear the placeholder loaded by Glide
                        Glide.with(holder.itemView.getContext()).clear(holder.playPic);
                        Picasso.get().load(allGet.getImageUrl()) // Pass the image URL from step.getT2()
                                .into(holder.playPic); // Load into the ImageViewd
                    }

                    @Override
                    public void onError(Exception e) {
                        // Handle the error if needed
                    }
                });

        holder.playaud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.toggleAudio(allGet.getAudioUrl());

            }
        });

        holder.menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(holder.itemView.getContext(), v);
                MenuInflater inflater = popup.getMenuInflater();
                inflater.inflate(R.menu.all_menu, popup.getMenu());

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.favorite:
                                String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

                                // Get a reference to the user's favorites in the database
                                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("user").child(userId).child("Favorites")
                                        .child(allGet.getKey());

                                Map<String, Object> favorite = new HashMap<>();
                                favorite.put("key", allGet.getKey());


                                // Store the key value in the database
                                // Store the key value in the database and set listeners for success and failure
                                databaseReference.updateChildren(favorite).addOnCompleteListener(task -> {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(holder.itemView.getContext(), "Added to Favorites", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(holder.itemView.getContext(), "Failed to add to Favorites", Toast.LENGTH_SHORT).show();
                                    }
                                });

                                return true;
                            default:
                                return false;
                        }
                    }
                });

                popup.show();
            }
        });

        holder.itemView.setOnTouchListener(new View.OnTouchListener() {
            GestureDetector gestureDetector = new GestureDetector(holder.itemView.getContext(), new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onDoubleTap(MotionEvent e) {
                    // Handle the double-tap event
                    holder.stopCurrentAudio(); // Stop the currently playing audio
                    listener.onItemClick(allGet);
                    Log.d("Double tap engaged" ,"Double Tap Activated");
                    return true;
                }

                @Override
                public boolean onSingleTapConfirmed(MotionEvent e) {
                    // Handle the single tap event if needed
                    return true;
                }

                @Override
                public boolean onDown(MotionEvent e) {
                    return true;
                }
            });

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return gestureDetector.onTouchEvent(event);
            }
        });



    }

    @Override
    public int getItemCount() {
        return ampList.size();
    }



    public class AllViewHolder extends RecyclerView.ViewHolder {

        TextView setName, userN;
        ImageView menu, playaud;
        ImageView playPic,editIcon,deleteIcon, postprof;
        RatingBar ratingBar;
        MediaPlayer mediaPlayer;
        boolean isPlaying = false;
        public AllViewHolder(@NonNull View itemView) {
            super(itemView);
            setName = itemView.findViewById(R.id.setName);
            userN = itemView.findViewById(R.id.userN);
            playPic = itemView.findViewById(R.id.playPic);
            editIcon = itemView.findViewById(R.id.editIcon);
            deleteIcon = itemView.findViewById(R.id.deleteIcon);
            postprof = itemView.findViewById(R.id.postprof);
            menu = itemView.findViewById(R.id.menu);
            ratingBar = itemView.findViewById(R.id.ratingBar);
            playaud = itemView.findViewById(R.id.playaud);

            mediaPlayer = new MediaPlayer();



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

        public void toggleAudio(String audioUrl) {
            if (isPlaying) {
                stopAudio();
            } else {
                if (currentMediaPlayer != null && currentMediaPlayer.isPlaying()) {
                    if (currentViewHolder != null) {
                        currentViewHolder.stopAudio();
                    }
                }
                playAudio(audioUrl);
                currentMediaPlayer = mediaPlayer;
                currentViewHolder = this;
            }
        }

        private void playAudio(String audioUrl) {
            if (mediaPlayer == null) {
                mediaPlayer = new MediaPlayer();
            }

            try {
                mediaPlayer.reset(); // Reset the MediaPlayer before setting data source
                mediaPlayer.setDataSource(audioUrl);
                mediaPlayer.prepare();
                mediaPlayer.start();
                isPlaying = true;
                playaud.setImageResource(R.drawable.stopaud); // Set image to stop icon when playing
            } catch (IOException e) {
                e.printStackTrace();
            }

            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    stopAudio();
                }
            });
        }

        private void stopAudio() {
            if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
                mediaPlayer = null;
                isPlaying = false;
                playaud.setImageResource(R.drawable.playaud); // Set image to play icon when stopped
            }
        }

        private void stopCurrentAudio() {
            if (currentMediaPlayer != null && currentMediaPlayer.isPlaying()) {
                currentMediaPlayer.stop();
                currentMediaPlayer.release();
                currentMediaPlayer = null;
                if (currentViewHolder != null) {
                    currentViewHolder.isPlaying = false;
                    currentViewHolder.playaud.setImageResource(R.drawable.playaud); // Set image to play icon
                }
            }
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

    // Define the method that handles the favorite click
    private void handleFavoriteClick(Context context) {
        // Your logic for when the favorite item is clicked

    }
}