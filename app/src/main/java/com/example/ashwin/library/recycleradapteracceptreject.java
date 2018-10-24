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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;


class recycleradapteracceptreject extends RecyclerView.Adapter<recycleradapteracceptreject.ViewHolder> {
    String uid;
    String uuid;
    int z = 0;
    String u;
    DatabaseReference ref;
    int g = 0;
    int q = 0;
    int a = 0;
    int o = 0;
    int m = 0;
    int flag = 0;
    addreqlistdata d = new addreqlistdata();
    private ArrayList<addreqlistdata> values;
    private HashMap<addreqlistdata, String> out;
    private ArrayList<Boolean> Amey;

    recycleradapteracceptreject(ArrayList<addreqlistdata> values, HashMap<addreqlistdata, String> out) {
        this.values = values;
        this.out = out;
        Amey = new ArrayList<>();
        for (int i = 0; i < values.size(); i++) {
            Amey.add(true);
        }
    }

    public recycleradapteracceptreject getThis() {
        return this;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.acceptrejectcard, parent, false));
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

    public void callme(final int position, final books b, final String userid, final String bookname) {
        g = 0;

        ref = FirebaseDatabase.getInstance().getReference("student/" + userid + "/blist");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ref = FirebaseDatabase.getInstance().getReference("student/" + userid + "/blist");
                if (g == 0) {
                    Log.d("acceptbtn", Integer.toString(position));
                    Log.d("acceptbtn", "this main thing");

                    ref.child("").push().setValue(b);
                    final DatabaseReference qty = FirebaseDatabase.getInstance().getReference("student/" + userid + "/bookqty");
                    q = 0;
                    qty.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (q == 0) {


                                int qqttyy = dataSnapshot.getValue(Integer.class);
                                ++qqttyy;
                                qty.setValue(qqttyy);
                                q = 1;
                                String originalbookname = bookname.replaceAll("\\s+", "").toUpperCase();
                                a = 0;
                                final DatabaseReference updatebookqty = FirebaseDatabase.getInstance().getReference("Books/" + originalbookname + "/qty");
                                updatebookqty.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        if (a == 0) {
                                            String bkqty = dataSnapshot.getValue(String.class);
                                            int foo = Integer.parseInt(bkqty);
                                            --foo;
                                            bkqty = Integer.toString(foo);
                                            updatebookqty.setValue(bkqty);
                                            a = 1;
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });


                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                    g = 1;

                    values.remove(position);
                    getThis().notifyItemRemoved(position);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        Log.d("acceptbtn", "go");

        //Log.d("acceptbtn", values.get(position).getBname());
        holder.bname.setText(values.get(position).getBname());

        Log.d("acceptbtn", "there");

        holder.stid.setText(values.get(position).getStid());
        holder.accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Log.d("acceptbtn",""+Integer.toString(position));
                int pos = getOriginalPos(position);
                Log.d(String.valueOf(pos), "asd");
                uuid = values.get(pos).getUuid();
                Log.d("size", String.valueOf(values.size()));
                books d = new books();
                Log.d("acceptbtn", "are you here");

                //String name = x;
                //final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                // uid = user.getUid();
                String test = "RequestList/" + uuid + "/" + out.get(values.get(pos));
                Log.d("acceptbtn", "" + test);
                Log.d("asd", test);
                DatabaseReference sref = FirebaseDatabase.getInstance().getReference(test);

                Log.d("acceptbtn", "are you here also");


                Log.d("acceptbtn", "are you here also and here also");

                //
                // Log.d("acceptbtn", dataSnapshot.getValue().toString());
                //int y;

                String doi, dor;
                d.setUserid(uuid);

                Date c = Calendar.getInstance().getTime();
                //System.out.println("Current time => " + c);

                SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
                doi = df.format(c);

                Calendar cal = Calendar.getInstance();
                cal.setTime(new Date());
                cal.add(Calendar.DAY_OF_YEAR, 7);
                Date sevenlater = cal.getTime();


                dor = df.format(sevenlater);


                o = 1;
                d.setBKname(values.get(pos).getBname());
                d.setDoi(doi);
                d.setDor(dor);

                Log.d("acceptbtn", "HERE2");
                //sref.removeValue();

                callme(pos, d, uuid, values.get(position).getBname());

                sref = FirebaseDatabase.getInstance().getReference(test);
                removeFromRequestList(test);


            }
        });
        holder.reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = getOriginalPos(position);
                uuid = values.get(pos).getUuid();


                String test = "RequestList/" + uuid + "/" + out.get(values.get(pos));
                DatabaseReference sref = FirebaseDatabase.getInstance().getReference(test);
                sref = FirebaseDatabase.getInstance().getReference(test);

                removeFromRequestList(test);
                values.remove(pos);
                getThis().notifyItemRemoved(pos);
            }
        });
    }

    private void removeFromRequestList(String e) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference(e);
        Log.d("thisdata", e);
        z = 0;
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (z == 0) {
                    Log.d("thisdata", "" + dataSnapshot.getChildrenCount() + " ///1");

                    dataSnapshot.getRef().removeValue();
                    z = 1;
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        ref.removeValue();
    }

    @Override
    public int getItemCount() {
        return values.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        Button accept, reject;
        private TextView bname, stid;

        ViewHolder(View itemView) {
            super(itemView);
            bname = itemView.findViewById(R.id.bkname);
            stid = itemView.findViewById(R.id.stid);
            accept = itemView.findViewById(R.id.acceptbtn);
            reject = itemView.findViewById(R.id.rejectbtn);

        }
    }
}
