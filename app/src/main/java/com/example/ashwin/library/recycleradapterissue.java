package com.example.ashwin.library;


import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


class recycleradapterissue extends RecyclerView.Adapter<recycleradapterissue.ViewHolder> {
    String uid;
    String u;
    DatabaseReference ref;
    int g = 0;
    int o = 0;
    addreqlistdata d = new addreqlistdata();
    private ArrayList<BookInfo> values;

    recycleradapterissue(ArrayList<BookInfo> values) {
        this.values = values;
    }

    public recycleradapterissue getThis() {
        return this;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.reqcard, parent, false));
    }

    public void callme(final int position, final addreqlistdata d) {
        g = 0;
        ref = FirebaseDatabase.getInstance().getReference("RequestList/" + uid);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ref = FirebaseDatabase.getInstance().getReference("RequestList/" + uid);
                if (g == 0) {
                    Log.d("issuebtn", "HERE4");

                    ref.child("").push().setValue(d);

                    g = 1;

                    /*values.remove(position);
                    getThis().notifyItemRemoved(position);*/
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.name.setText(values.get(position).getTitle());
        holder.athr.setText(values.get(position).getAuthor());
        holder.genre.setText(values.get(position).getGenre());
        holder.publisher.setText(values.get(position).getPublisher());
        holder.qty.setText(values.get(position).getQty());
        holder.issue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String x = values.get(position).getTitle();
                final addreqlistdata d = new addreqlistdata();

                //String name = x;
                final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                uid = user.getUid();
                Log.d("Log message", "uid = " + uid);
                DatabaseReference sref = FirebaseDatabase.getInstance().getReference("student/" + uid + "/uid");
                sref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        addreqlistdata d = new addreqlistdata();
                        Log.d("stuid", dataSnapshot.getValue().toString());
                        //int y;
                        u = dataSnapshot.getValue(Integer.class).toString();
                        //u = Intent.toString(y);
                         d.setBname(values.get(position).getTitle());
                         d.setStid(u);
                         d.setUuid(uid);
                         boolean c = false;
                         Log.d("issuebtn", "HERE2");
                         d.setAccepted(c);
                         callme(position, d);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


                Log.d("issuebtn", "HERE");

                Toast.makeText(view.getContext(), "Request for the book has been sent", Toast.LENGTH_LONG).show();

                Log.d("issuebtn", String.valueOf(u));


            }
        });
    }

    @Override
    public int getItemCount() {
        return values.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        String p;
        Button issue;
        private TextView name, athr, genre, publisher, qty;

        ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.bname);
            // p = name.get
            issue = itemView.findViewById(R.id.issuebtn);
            athr = itemView.findViewById(R.id.athr);
            genre = itemView.findViewById(R.id.genre_id);
            publisher = itemView.findViewById(R.id.publisher_id);
            qty = itemView.findViewById(R.id.qty_id);
        }
    }
}
