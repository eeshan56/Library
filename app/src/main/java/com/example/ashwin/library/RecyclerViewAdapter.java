package com.example.ashwin.library;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;



class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private ArrayList<BookInfo> values;

    RecyclerViewAdapter(ArrayList<BookInfo> values) {
        this.values = values;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.name.setText(values.get(position).getTitle());
        holder.athr.setText(values.get(position).getAuthor());
        holder.genre.setText(values.get(position).getGenre());
        holder.publisher.setText(values.get(position).getPublisher());
        holder.qty.setText(values.get(position).getQty());

    }

    @Override
    public int getItemCount() {
        return values.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name, athr, genre, publisher, qty;

        ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.bname);
            athr = itemView.findViewById(R.id.athr);
            genre = itemView.findViewById(R.id.genre_id);
            publisher = itemView.findViewById(R.id.publisher_id);
            qty = itemView.findViewById(R.id.qty_id);
        }
    }
}
