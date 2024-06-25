package com.av.Gwaz.chat;

import android.content.Context;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserAdpter extends RecyclerView.Adapter<UserAdpter.viewholder> {
    Context context;
    ArrayList<Users> usersArrayList;
    FirebaseDatabase seenref;
    FirebaseAuth firebaseAuth;
    public UserAdpter(Context context, ArrayList<Users> usersArrayList) {
        this.context = context;
        this.usersArrayList=usersArrayList;
    }

    @NonNull
    @Override
    public UserAdpter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.user_item,parent,false);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdpter.viewholder holder, int position) {


        Users users = usersArrayList.get(position);
        holder.username.setText(users.userName);
        holder.userstatus.setText(users.status);
        Picasso.get().load(users.profilepic).into(holder.userimg);
        firebaseAuth = FirebaseAuth.getInstance();
        String SenderUID =  firebaseAuth.getUid();

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("user").child(SenderUID).child("userCommunicated")
                .child(users.getUserId());
        Log.d("Adapter", "Setting notification indicator visible for user: " + SenderUID);
        holder.notify.setVisibility(View.VISIBLE);

        // Set up ValueEventListener to listen for changes in communication status
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Check if the 'seen' value exists
                if (dataSnapshot.exists()) {
                    Boolean seen = dataSnapshot.child("seen").getValue(Boolean.class);
                    // If 'seen' exists and it's false, set the notify to visible
                    if (seen != null && !seen) {
                        holder.notify.setVisibility(View.VISIBLE);
                    } else {
                        holder.notify.setVisibility(View.GONE);
                    }
                } else {
                    // If the 'seen' value doesn't exist, hide the notify indicator
                    holder.notify.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle errors here
            }
        });






        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                seenref = FirebaseDatabase.getInstance();
                // Save the 'seen' attribute under the receiver's node as well
                seenref.getReference().child("user")
                        .child(SenderUID) // Use receiver's UID here
                        .child("userCommunicated")
                        .child(users.getUserId()) // Assuming you want to save this under the receiver's node
                        .child("seen")
                        .setValue(true).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {



                                Intent intent = new Intent(context, chatwindo.class);
                                intent.putExtra("nameeee",users.getUserName());
                                intent.putExtra("reciverImg",users.getProfilepic());
                                intent.putExtra("uid",users.getUserId());
                                context.startActivity(intent);

                            }
                        });
            }
        });



    }

    @Override
    public int getItemCount() {
        return usersArrayList.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {
        CircleImageView userimg;
        ImageView notify;
        TextView username;
        TextView userstatus;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            userimg = itemView.findViewById(R.id.userimg);
            username = itemView.findViewById(R.id.username);
            userstatus = itemView.findViewById(R.id.userstatus);
            notify = itemView.findViewById(R.id.notify);


        }
    }
}
