package com.example.demo.domain;

/**
 * Created by cw on 2017/9/30.
 */
public class Book {

    private String book_id;

    private String book_name;

    private String book_code;

    private String location;

    public Book(String book_id, String book_name, String book_code, String location) {
        this.book_id = book_id;
        this.book_name = book_name;
        this.book_code = book_code;
        this.location = location;
    }

    public String getBook_id() {
        return book_id;
    }

    public void setBook_id(String book_id) {
        this.book_id = book_id;
    }

    public String getBook_name() {
        return book_name;
    }

    public void setBook_name(String book_name) {
        this.book_name = book_name;
    }

    public String getBook_code() {
        return book_code;
    }

    public void setBook_code(String book_code) {
        this.book_code = book_code;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
