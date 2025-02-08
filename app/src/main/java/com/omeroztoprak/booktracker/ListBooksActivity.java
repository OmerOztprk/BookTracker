package com.omeroztoprak.booktracker;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import androidx.appcompat.app.AppCompatActivity;

public class ListBooksActivity extends AppCompatActivity {

    private ListView listViewBooks;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_books);

        listViewBooks = findViewById(R.id.listViewBooks);
        databaseHelper = new DatabaseHelper(this);

        Cursor cursor = databaseHelper.getAllBooks();
        String[] fromColumns = {DatabaseHelper.COLUMN_BOOK_TITLE, DatabaseHelper.COLUMN_BOOK_AUTHOR};
        int[] toViews = {android.R.id.text1, android.R.id.text2};

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_2, cursor, fromColumns, toViews, 0);
        listViewBooks.setAdapter(adapter);
    }
}