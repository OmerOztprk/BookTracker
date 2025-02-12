package com.omeroztoprak.booktracker;

import static com.omeroztoprak.booktracker.DatabaseHelper.COLUMN_CATEGORY_NAME;
import static com.omeroztoprak.booktracker.DatabaseHelper.TABLE_CATEGORIES;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class AddBookActivity extends AppCompatActivity {

    private EditText editTextTitle, editTextAuthor, editTextComment;
    private Spinner spinnerCategory;
    private Button btnSaveBook;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

        editTextTitle = findViewById(R.id.editTextBookTitle);
        editTextAuthor = findViewById(R.id.editTextBookAuthor);
        editTextComment = findViewById(R.id.editTextBookComment);
        spinnerCategory = findViewById(R.id.spinnerCategory);
        btnSaveBook = findViewById(R.id.btnSaveBook);

        databaseHelper = new DatabaseHelper(this);

        List<String> categoryList = getCategories();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categoryList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(adapter);

        btnSaveBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = editTextTitle.getText().toString();
                String author = editTextAuthor.getText().toString();
                String category = spinnerCategory.getSelectedItem().toString();
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

    private List<String> getCategories() {
        List<String> categories = new ArrayList<>();
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + COLUMN_CATEGORY_NAME + " FROM " + TABLE_CATEGORIES, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                categories.add(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CATEGORY_NAME)));
            }
            cursor.close();
        }

        return categories;
    }
}