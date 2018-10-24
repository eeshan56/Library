package com.example.ashwin.library;


import android.content.Context;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class searchbook extends AppCompatActivity {

    EditText mSearchField;
    Button mSearchBtn;


    RecyclerView recyclerView;
    View view;

    DatabaseReference ref;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchbook);


        Log.d("SEARCHEXACT", "HERE");
        mSearchField = (EditText) findViewById(R.id.search_field);
        Log.d("SEARCHEXACT", "HERE4");
        mSearchBtn = (Button) findViewById(R.id.search_btn);
        Log.d("SEARCHEXACT", "HERE5");
        recyclerView = (RecyclerView) findViewById(R.id.result_list);
        Log.d("SEARCHEXACT", "HERE2");
        recyclerView.setHasFixedSize(true);
        Log.d("SEARCHEXACT", "HERE3");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        mSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search();
            }
        });

    }

    public void search() {
        Log.d("SEARCHEXACT", "HERE69");
        final String searchText = mSearchField.getText().toString().replaceAll("\\s+", "").toUpperCase();
        ref = FirebaseDatabase.getInstance().getReference("Books");
        Log.d("SEARCHEXACT", "HERE70");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Log.d("SEARCHEXACT", searchText);

                ArrayList<BookInfo> values = new ArrayList<>();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    if (ds.getKey().equals(searchText.toString())) {
                        values.add(ds.getValue(BookInfo.class));

                        Log.d("SEARCHEXACT", String.valueOf(values.size()));
                    }


                }
                recyclerView.setAdapter(new RecyclerViewAdapter(values));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    public void onClick(View view) {
        //calling register method on click
        // Log.d("email", editTextUid.toString());
        search();
    }
}

/*


    mSearchBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                String searchText = mSearchField.getText().toString().replaceAll("\\s+","").toUpperCase();
                ref = FirebaseDatabase.getInstance().getReference("Books"+searchText);
                if(ref == null){
                    Toast.makeText(searchbook.this,  "No Book Found", Toast.LENGTH_LONG).show();
                }
                else{
                    ref.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            ArrayList<BookInfo> values = new ArrayList<>();
                            for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                values.add(ds.getValue(BookInfo.class));
                            }
                            recyclerView.setAdapter(new RecyclerViewAdapter(values));
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }



            }
        });
 */
