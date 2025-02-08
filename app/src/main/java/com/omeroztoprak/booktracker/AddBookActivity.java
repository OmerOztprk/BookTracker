package com.omeroztoprak.booktracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class AddBookActivity extends AppCompatActivity {

    private EditText editTextTitle, editTextAuthor, editTextCategory, editTextComment;
    private Button btnSaveBook;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

        editTextTitle = findViewById(R.id.editTextBookTitle);
        editTextAuthor = findViewById(R.id.editTextBookAuthor);
        editTextCategory = findViewById(R.id.editTextBookCategory);
        editTextComment = findViewById(R.id.editTextBookComment);
        btnSaveBook = findViewById(R.id.btnSaveBook);

        databaseHelper = new DatabaseHelper(this);

        btnSaveBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = editTextTitle.getText().toString();
                String author = editTextAuthor.getText().toString();
                String category = editTextCategory.getText().toString();
                String comment = editTextComment.getText().toString();

                if (title.isEmpty() || author.isEmpty() || category.isEmpty()) {
                    Toast.makeText(AddBookActivity.this, "Lütfen tüm alanları doldurun", Toast.LENGTH_SHORT).show();
                } else {
                    boolean isInserted = databaseHelper.addBook(title, author, category, comment);
                    if (isInserted) {
                        Toast.makeText(AddBookActivity.this, "Kitap kaydedildi", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(AddBookActivity.this, ListBooksActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(AddBookActivity.this, "Kitap kaydedilirken hata oluştu", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
