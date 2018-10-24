package com.example.ashwin.library;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Menu4 extends Fragment {

    View view;
    DatabaseReference ref;
    RecyclerView recyclerView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.menu4, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.admin_issued_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        ref = FirebaseDatabase.getInstance().getReference("student");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<UserDetails> User = new ArrayList<>();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    data d = ds.getValue(data.class);

                    if (!d.isIsadmin()) {
                        if (d.getBookqty() > 0) {

                            DataSnapshot ds2 = ds.child("blist");
                            for (DataSnapshot ds3 : ds2.getChildren()) {
                                books b = ds3.getValue(books.class);
                                User.add(new UserDetails(d.getEmail(), d.getName(), d.getUid(), b.getBKname(), b.getDoi(), b.getDor()));
                                for (int i = 0; i < User.size(); i++) {
                                    Log.d("Log message (List view)", User.get(i).getUser() + ", " + User.get(i).getEmail() + ", " + User.get(i).getUid() + ", " + User.get(i).getBKname() + ", " + User.get(i).getDoi() + ", " + User.get(i).getDor());
                                }
                            }
                        }
                    }
                }
                recyclerView.setAdapter(new RecyclerAdminIssue(User));
                Log.d("Log message:", "Is it displaying?");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle("Issued Books");
    }
}