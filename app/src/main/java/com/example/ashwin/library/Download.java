package com.example.ashwin.library;

import android.content.Intent;
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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Download extends Fragment {

    View view;
    RecyclerView recyclerView;
    DatabaseReference ref;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.downloadfiles, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.download_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        ref = FirebaseDatabase.getInstance().getReference("Uploads");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<FileDownload> values = new ArrayList<>();
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    for (DataSnapshot DS : ds.getChildren()) {

                        String fileName = DS.child("fileName").getValue(String.class);
                        String fileType = DS.child("fileType").getValue(String.class);
                        String uploadTime = DS.child("uploadTime").getValue(String.class);

                        String fSize = DS.child("fileSize").getValue(String.class);
                        String fileSize = "";
                        long FS = Long.valueOf(fSize);

                        int i = 0;
                        while (FS >= 1024) {
                            FS /= 1024;
                            i++;
                        }

                        String F = Long.toString(FS);
                        fileSize += FS;
                        if (i == 0)
                            fileSize += " B";
                        else if (i == 1)
                            fileSize += " kB";
                        else if (i == 2)
                            fileSize += " MB";
                        else if (i == 3)
                            fileSize += " GB";
                        else if (i == 4)
                            fileSize += " TB";

                        Log.d("Log message", "current upload:\n" + fileName + ", " + fileType + ", " + fileSize + ", " + ", " + uploadTime);
                        values.add(new FileDownload(fileName, fileType, fileSize, uploadTime, ds.getKey()));
                    }
                }
                Log.d("Log message in Download", "values.size() = " + values.size());
                recyclerView.setAdapter(new RecyclerAdapterDownload(values));
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

        getActivity().setTitle("Choose File for Download");
    }
}
