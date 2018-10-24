package com.example.ashwin.library;


import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;


public class RecyclerViewAdapterIssue extends RecyclerView.Adapter<RecyclerViewAdapterIssue.ViewHolder> {
    private ArrayList<books> values;
    int flag = 0;
    String uuid;
    int f = 0, z = 0, x = 0;
    private HashMap<books, String> out;
    private ArrayList<Boolean> Amey;

    RecyclerViewAdapterIssue(ArrayList<books> values, HashMap<books, String> out) {
        this.values = values;
        this.out = out;
        for (books name : out.keySet()) {

            String key = name.toString();
            String value = out.get(name).toString();
            System.out.println(key + " " + value);


        }
        Amey = new ArrayList<>();
        for (int i = 0; i < values.size(); i++) {
            Amey.add(true);
        }

    }

    public RecyclerViewAdapterIssue getThis() {
        return this;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.issued_item, parent, false));
    }


    public int getOriginalPos(int position) {
        int count = 0;
        for (int i = 0; i < position; i++) {
            if (Amey.get(i)) {
                count++;
            }
        }

        Amey.set(position, false);
        flag = 1;


        return count;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.bkname.setText(values.get(position).getBKname());
        Log.d("issuedlist", "" + values.get(position).getBKname());
        holder.doi.setText(values.get(position).getDoi());
        holder.dor.setText(values.get(position).getDor());
        holder.Retuenbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = getOriginalPos(position);
                uuid = values.get(pos).getUserid();

                Log.d("tp", values.get(pos).toString());
                String test = "student/" + uuid + "/blist/" + out.get(values.get(position));
                Log.d("why", "" + test);
                returnbook(test);
                String down = "student/" + uuid + "/bookqty";
                countdown(down);
                String bk = values.get(pos).getBKname().replaceAll("\\s+", "").toUpperCase();
                String up = "Books/" + bk + "/qty";
                countup(up);
                callme(pos);
            }
        });

    }

    private void callme(int position) {
        values.remove(position);
        getThis().notifyItemRemoved(position);
    }

    private void countup(String up) {
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(up);
        x = 0;
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (x == 0) {
                    String bqty = dataSnapshot.getValue(String.class);
                    int bqt = Integer.parseInt(bqty);
                    ++bqt;
                    bqty = Integer.toString(bqt);
                    databaseReference.setValue(bqty);
                    x = 1;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void countdown(String down) {
        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference(down);
        z = 0;
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (z == 0) {
                    int booksquantityofstudent = dataSnapshot.getValue(Integer.class);
                    --booksquantityofstudent;
                    reference.setValue(booksquantityofstudent);
                    z = 1;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void returnbook(String test) {
        final DatabaseReference ref = FirebaseDatabase.getInstance().getReference(test);
        Log.d("return_book", test);
        ref.removeValue();
    }

    @Override
    public int getItemCount() {
        return values.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView bkname, doi, dor;
        private Button Retuenbtn;

        ViewHolder(View itemView) {
            super(itemView);
            bkname = (TextView) itemView.findViewById(R.id.issued_book_name);
            doi = (TextView) itemView.findViewById(R.id.issue_id);
            dor = (TextView) itemView.findViewById(R.id.return_id);
            Retuenbtn = (Button) itemView.findViewById(R.id.return_btn_id);
        }
    }
}
