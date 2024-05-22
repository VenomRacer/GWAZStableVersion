package com.av.Gwaz.homepage;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.av.Gwaz.R;
import com.av.Gwaz.homepage.AMPLIZ.AllSettings.AllSettings;
import com.av.Gwaz.homepage.CHORDM.MainactChordm;
import com.av.Gwaz.homepage.GWIZ.MainactGwiz;
import com.av.Gwaz.homepage.TUNER.TunerTrainer;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {

    private int[] images;
    private Context context;

    public ImageAdapter(Context context, int[] images) {
        this.context = context;
        this.images = images;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_image, parent, false);
        return new ViewHolder(view);
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.imageView.setImageResource(images[position]);



        // Set OnClickListener based on position
        holder.imageView.setOnClickListener(v -> {
            // Play sound based on position
            switch (position) {
                case 0: // Guitarwiz
                    playSound(R.raw.strum); // Replace 'your_guitar_sound_file' with your actual guitar sound file
                    break;
                case 1: // Amplizone
                    playSound(R.raw.plug); // Replace 'your_amplizone_sound_file' with your actual amplizone sound file
                    break;
                case 2: // Chordmaster
                    playSound(R.raw.chord); // Replace 'your_chordmaster_sound_file' with your actual chordmaster sound file
                    break;
                case 3: // Tuner
                    playSound(R.raw.dchord); // Replace 'your_tuner_sound_file' with your actual tuner sound file
                    break;
                default:
                    break;
            }

            // Handle click actions here
            if (position == 0) { // Assuming the first image is the guitarwiz
                Intent intent = new Intent(context, MainactGwiz.class);
                context.startActivity(intent);
            } else if (position == 1) {
                Intent intent = new Intent(context, AllSettings.class);
                context.startActivity(intent);
            } else if (position == 2){
                Intent intent = new Intent(context, MainactChordm.class);
                context.startActivity(intent);
            } else if (position == 3){
                Intent intent = new Intent(context, TunerTrainer.class);
                context.startActivity(intent);
                ((Activity) context).finish();


            }
            // You can add more conditions here for other positions if needed
        });
    }

    @Override
    public int getItemCount() {
        return images.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }

    // Method to play sound
    private void playSound(int soundResource) {
        MediaPlayer mediaPlayer = MediaPlayer.create(context, soundResource);
        mediaPlayer.start();
        mediaPlayer.setOnCompletionListener(mp -> {
            mediaPlayer.release();
        });
    }
}