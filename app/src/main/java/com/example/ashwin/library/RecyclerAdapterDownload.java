package com.example.ashwin.library;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;


public class RecyclerAdapterDownload extends RecyclerView.Adapter<RecyclerAdapterDownload.ViewHolder> {
    private ArrayList<FileDownload> values;


    RecyclerAdapterDownload(ArrayList<FileDownload> values) {
        this.values = values;
    }

    public RecyclerAdapterDownload getThis() {
        return this;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.downloads_card_view, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.fileName.setText(values.get(position).getFileName());
        holder.fileSize.setText(values.get(position).getFileSize());
        holder.fileType.setText(values.get(position).getFileType());
        holder.uploadTime.setText(values.get(position).getUploadTime());
        holder.databaseRef = values.get(position).getDatabaseRef();
    }

    @Override
    public int getItemCount() {
        Log.d("Log message", "values.size() = " + values.size());
        return values.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView fileName, fileType, fileSize, uploadTime;
        private String databaseRef;

        ViewHolder(final View itemView) {
            super(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                private static final int MY_PERMISSION = 1;
                @Override
                public void onClick(final View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                    builder.setTitle("Download Confirmation");
                    builder.setMessage("Are you sure you want to download this file?");
                    builder.setIcon(R.drawable.ic_file_download_black_24dp);
                    builder.setCancelable(true);
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            StorageReference storageReference = FirebaseStorage.getInstance().getReference();
                            storageReference.child(databaseRef + "/" + fileName.getText() + "." + fileType.getText().toString().toLowerCase()).getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                @Override
                                public void onComplete(@NonNull Task<Uri> task) {
                                    Log.d("Log message task result", "Task Result:\n" + task.getResult());
                                    Log.d("Log message file", "Filename:\n" + fileName.getText());
                                    Log.d("Log message file", "Filetype:\n" + fileType.getText());
                                    Intent intent = new Intent(v.getContext(), Pop.class);
                                    intent.putExtra("URL", task.getResult().toString());
                                    intent.putExtra("fileName", fileName.getText().toString());
                                    intent.putExtra("fileType", fileType.getText().toString());
                                    v.getContext().startActivity(intent);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                }
                            });
                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
            });

            fileName = (TextView) itemView.findViewById(R.id.download_filename);
            fileType = (TextView) itemView.findViewById(R.id.download_file_type);
            fileSize = (TextView) itemView.findViewById(R.id.download_file_size);
            uploadTime = (TextView) itemView.findViewById(R.id.download_date);
        }
    }
}
