package com.omeroztoprak.booktracker;

public class Book {

    private String title;
    private String author;
    private String category;
    private String comment;

    public Book(String title, String author, String category, String comment) {
        this.title = title;
        this.author = author;
        this.category = category;
        this.comment = comment;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getCategory() {
        return category;
    }

    public String getComment() {
        return comment;
    }
}
