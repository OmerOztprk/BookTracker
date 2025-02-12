package com.omeroztoprak.booktracker;

import android.content.Context;
import android.content.DialogInterface;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import androidx.annotation.NonNull;

import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {

    private List<Book> bookList;
    private DatabaseHelper databaseHelper;
    private Context context;

    public BookAdapter(List<Book> bookList, DatabaseHelper databaseHelper, Context context) {
        this.bookList = bookList;
        this.databaseHelper = databaseHelper;
        this.context = context;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book, parent, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        Book book = bookList.get(position);
        holder.textViewTitle.setText(book.getTitle());
        holder.textViewAuthor.setText(book.getAuthor());
        holder.textViewCategory.setText(book.getCategory());
        holder.textViewComment.setText(book.getComment());

        holder.btnDelete.setOnClickListener(v -> {
            int pos = holder.getAdapterPosition();
            if (pos != RecyclerView.NO_POSITION) {
                Book bookToDelete = bookList.get(pos);
                boolean isDeleted = databaseHelper.deleteBook(bookToDelete.getId());
                if (isDeleted) {
                    bookList.remove(pos);
                    notifyItemRemoved(pos);
                }
            }
        });

        holder.btnUpdate.setOnClickListener(v -> {
            String currentComment = book.getComment();
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Yorum Güncelle");

            final EditText input = new EditText(context);
            input.setText(currentComment);
            builder.setView(input);

            builder.setPositiveButton("Kaydet", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String updatedComment = input.getText().toString().trim();
                    if (!updatedComment.isEmpty()) {
                        boolean isUpdated = databaseHelper.updateBookComment(book.getId(), updatedComment);
                        if (isUpdated) {
                            book.setComment(updatedComment);
                            notifyItemChanged(holder.getAdapterPosition());
                            Toast.makeText(context, "Yorum başarıyla güncellendi", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "Yorum güncellenemedi", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(context, "Yorum boş olamaz", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            builder.setNegativeButton("İptal", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            builder.show();
        });
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    public static class BookViewHolder extends RecyclerView.ViewHolder {

        TextView textViewTitle, textViewAuthor, textViewCategory, textViewComment;
        Button btnDelete, btnUpdate;

        public BookViewHolder(View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewAuthor = itemView.findViewById(R.id.textViewAuthor);
            textViewCategory = itemView.findViewById(R.id.textViewCategory);
            textViewComment = itemView.findViewById(R.id.textViewComment);
            btnDelete = itemView.findViewById(R.id.btnDelete);
            btnUpdate = itemView.findViewById(R.id.btnUpdate);
        }
    }
}