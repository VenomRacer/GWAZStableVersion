package com.example.admincms.selection.AMPLIZONE.AllSettings;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.admincms.R;
import com.google.firebase.database.DatabaseReference;
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



    static class AllViewHolder extends RecyclerView.ViewHolder {

        TextView setName, userN;
        ImageView playPic,editIcon,deleteIcon;

        public AllViewHolder(@NonNull View itemView) {
            super(itemView);
            setName = itemView.findViewById(R.id.setName);
            userN = itemView.findViewById(R.id.userN);
            playPic = itemView.findViewById(R.id.playPic);
            editIcon = itemView.findViewById(R.id.editIcon);
            deleteIcon = itemView.findViewById(R.id.deleteIcon);

        }


    }
}