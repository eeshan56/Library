package com.example.ashwin.library;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Menu2 extends Fragment implements View.OnClickListener {

    EditText bookname, authorname, genrename, subgenrename, publishername, quantity;


    View v;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.menu2, container, false);
        bookname = (EditText) v.findViewById(R.id.bookname);
        authorname = (EditText) v.findViewById(R.id.authorname);
        genrename = (EditText) v.findViewById(R.id.genrename);
        subgenrename = (EditText) v.findViewById(R.id.subgenrename);
        publishername = (EditText) v.findViewById(R.id.publishername);
        quantity = (EditText) v.findViewById(R.id.quantity);


        Button addbtn = (Button) v.findViewById(R.id.addbtn);
        addbtn.setOnClickListener(this);


        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle("Add New Book");

    }

    @Override
    public void onClick(View view) {


        final String bname = bookname.getText().toString();
        final String finalbookname = bname.replaceAll("\\s+", "").toUpperCase();
        final String authname = authorname.getText().toString().trim();
        final String genre = genrename.getText().toString().trim();
        final String subgenre = subgenrename.getText().toString().trim();
        final String qty = quantity.getText().toString().trim();
        final String publisher = publishername.getText().toString().trim();
        // final String i = "Don";
        // String z = "NO";
        int x = 0;

        if (TextUtils.isEmpty(bname)) {
            Toast.makeText(v.getContext(), "Please enter book name", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(authname)) {
            Toast.makeText(v.getContext(), "Please enter author name", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(genre)) {
            Toast.makeText(v.getContext(), "Please enter genre", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(subgenre)) {
            Toast.makeText(v.getContext(), "Please enter genre", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(qty)) {
            Toast.makeText(v.getContext(), "Please enter genre", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(publisher)) {
            Toast.makeText(v.getContext(), "Please enter genre", Toast.LENGTH_LONG).show();
            return;
        }

        Log.d("EXACT", (x == 0) + "");

        if (x == 0) {
            Log.d("EXACT", "HALF SUCCESSFUL");
            DatabaseReference dref;
            final FirebaseDatabase database = FirebaseDatabase.getInstance();

            dref = database.getReference("Books/" + finalbookname);

            Log.d("EXACT", "After dref");
//            dref.addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(DataSnapshot dataSnapshot) {
//                    Log.d("EXACT",dataSnapshot.getValue().toString());
//                }
//
//                @Override
//                public void onCancelled(DatabaseError databaseError) {
//
//                }
//            });
            BookInfo b = new BookInfo();
            b.setAuthor(authorname.getText().toString());
            b.setGenre(genrename.getText().toString());
            b.setPublisher(publishername.getText().toString());
            b.setQty(quantity.getText().toString());
            b.setSubGenre(subgenrename.getText().toString());
            b.setTitle(bookname.getText().toString().toUpperCase());
            Log.d("EXACT", "Before set value");
            dref.child("").setValue(b);
            Log.d("EXACT", "Successful");
            x = 1;
        }

        if (x == 1) {
            Log.d("EXACT", "Double Successful");
            bookname.setText("");
            authorname.setText("");
            genrename.setText("");
            subgenrename.setText("");
            quantity.setText("");
            publishername.setText("");

            x = 0;
        }

    }
}
