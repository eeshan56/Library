package com.example.ashwin.library;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;


public class RecyclerAdminIssue extends RecyclerView.Adapter<RecyclerAdminIssue.ViewHolder> {
    private ArrayList<UserDetails> User;

    RecyclerAdminIssue(ArrayList<UserDetails> User) {
        this.User = User;
        Log.d("Log message:", "Inside constructor");
    }

    public RecyclerAdminIssue getThis() {
        return this;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d("Log message:", "Creating Card Layout");
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.issued_admin_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Log.d("Log message:", "Setting textviews");

        String book_name = User.get(position).getBKname();
        Log.d("Log message", "book name: " + book_name);
        holder.bkname.setText(book_name);

        String dissue = User.get(position).getDoi();
        Log.d("Log message", "doi: " + dissue);
        holder.doi.setText(dissue);


        String dreturn = User.get(position).getDor();
        Log.d("Log message", "dor: " + dreturn);
        holder.dor.setText(User.get(position).getDor());

        String userName = User.get(position).getUser();
        Log.d("Log message", "user name: " + userName);
        holder.user.setText(userName);

        String eMail = User.get(position).getEmail();
        Log.d("Log message", "email: " + eMail);
        holder.email.setText(User.get(position).getEmail());

        int i = User.get(position).getUid();
        Log.d("Log message", "uid: " + i);
        String I = Integer.toString(i);
        holder.uid.setText(I);

        Log.d("Log message:", "All textviews set");
    }



    @Override
    public int getItemCount() {
        Log.d("Log message:", "User size = " + User.size());
        return User.size();
    }



    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView bkname, doi, dor;
        private TextView user, email, uid;

        ViewHolder(View itemView) {
            super(itemView);
            bkname = (TextView) itemView.findViewById(R.id.admin_book_name);
            doi = (TextView) itemView.findViewById(R.id.admin_issue_id);
            dor = (TextView) itemView.findViewById(R.id.admin_return_id);
            uid = (TextView) itemView.findViewById(R.id.data_uid);
            email = (TextView) itemView.findViewById(R.id.data_email);
            user = (TextView) itemView.findViewById(R.id.data_user);
            Log.d("Log message:", "Setting holder variables");
        }
    }
}
