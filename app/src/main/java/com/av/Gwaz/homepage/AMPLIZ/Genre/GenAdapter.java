package com.av.Gwaz.homepage.AMPLIZ.Genre;

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


public class GenAdapter extends RecyclerView.Adapter<GenAdapter.AllViewHolder>{

    private List<GenGet> genList;
    private DatabaseReference databaseReference;
    private StorageReference storageReference;
    private OnItemClickListener listener;

    public GenAdapter(List<GenGet> genList, DatabaseReference databaseReference, StorageReference storageReference, OnItemClickListener listener) {
        this.genList = genList;
        this.databaseReference = databaseReference;
        this.storageReference = storageReference;
        this.listener = listener;
    }

    @NonNull
    @Override
    public AllViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gen, parent, false);
        return new AllViewHolder(view);
    }

    public interface OnItemClickListener {
        void onItemClick(GenGet item);
    }

    public void filterList(List<GenGet> filteredList) {
        genList = filteredList;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull AllViewHolder holder, int position) {
        GenGet genGet = genList.get(position);

        holder.setName.setText(genGet.getSetName());
        holder.userN.setText("By: " + genGet.getBy());

        Picasso.get().load(genGet.getImageUrl()) // Pass the image URL from step.getT2()
                .into(holder.playPic); // Load into the ImageView

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(genGet);
            }
        });

    }

    @Override
    public int getItemCount() {
        return genList.size();
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


        }


    }
}
