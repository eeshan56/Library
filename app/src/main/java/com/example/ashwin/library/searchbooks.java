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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class searchbooks extends Fragment {
    View view;
    int h = 0;
    EditText mSearchField;
    Button mSearchBtn;
    String uid;
    int u = 0;
    int a = 0;
    int flag = 0;
    //int qty;
    RecyclerView recyclerView;

    DatabaseReference ref;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.searchbooks, container, false);

        Log.d("SEARCHEXACT", "HERE");
        mSearchField = (EditText) view.findViewById(R.id.search_field);
        Log.d("SEARCHEXACT", "HERE4");
        mSearchBtn = (Button) view.findViewById(R.id.search_btn);
        Log.d("SEARCHEXACT", "HERE5");
        recyclerView = (RecyclerView) view.findViewById(R.id.studreq_list);
        Log.d("SEARCHEXACT", "HERE2");
        recyclerView.setHasFixedSize(true);
        Log.d("SEARCHEXACT", "HERE3");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        mSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search();
            }
        });

        return view;
    }


    public void search() {

        Log.d("SEARCHEXACT", "HERE69");
        //replaceAll("\\s+", "")
        final String searchText = mSearchField.getText().toString().toUpperCase();
        ref = FirebaseDatabase.getInstance().getReference("Books");
        Log.d("SEARCHEXACT", "HERE70");
        u = 0;
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (u == 0) {
                    Log.d("SEARCHEXACT", searchText);
                    //flag = 0;
                    final ArrayList<BookInfo> values = new ArrayList<>();
                    for (final DataSnapshot ds : dataSnapshot.getChildren()) {
                        DataSnapshot DS1 = ds.child("Title");
                        Log.d("Log message", searchText + ", DS1.getValue() -> " + DS1.getValue());
                        if (DS1.getValue().toString().contains(searchText.toString())) {
                            flag = 0;

                            String bookqtyavailable = ds.child("qty").getValue(String.class);
                            int availablebookqty;
                            availablebookqty = Integer.parseInt(bookqtyavailable);
                            if (availablebookqty > 0) {

                                flag = 1;

                                String tit = ds.child("Title").getValue(String.class);
                                String au = ds.child("Author").getValue(String.class);
                                String gen = ds.child("Genre").getValue(String.class);
                                String sub_gen = ds.child("SubGenre").getValue(String.class);
                                String pub = ds.child("Publisher").getValue(String.class);
                                String Q = ds.child("qty").getValue(String.class);

                                values.add(new BookInfo(tit, au, sub_gen, gen, Q, pub));

                                Log.d("righthere", "2nd log" + Integer.toString(flag));
                                Log.d("righthere", "3rd log did you come here");

                            } else {
                                // Log.d("righthere",""+Integer.toString(flag));

                                Toast.makeText(view.getContext(), "Sorry the book is out of stock", Toast.LENGTH_LONG).show();
                            }

                            Log.d("righthere", "4 th log" + Integer.toString(flag));

                            a = 1;

                            Log.d("righthere", "5th log " + "" + Integer.toString(flag));

                            Log.d("righthere", "6th log" + Integer.toString(flag));


                        }

                        if (flag == 1) {

                            Log.d("righthere", "7th " + Integer.toString(flag));
                            Log.d("righthere", "8th log" + Integer.toString(flag));

                            //values.add(ds.getValue(BookInfo.class));
                            Log.d("SEARCHEXACT", ds.getValue().toString());

                            Log.d("SEARCHEXACT", String.valueOf(values.size()));
                            flag = 0;
                        }


                    }
                    recyclerView.setAdapter(new recycleradapterissue(values));

                    u = 1;
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Search Books");
    }

}
