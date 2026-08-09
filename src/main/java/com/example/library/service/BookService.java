package com.example.library.service;

import com.example.library.dto.Book;
import org.springframework.stereotype.Service;

import javax.print.attribute.HashAttributeSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

@Service
public class BookService {
    private HashMap<Long , Book> books = new HashMap<>();

    public HashMap<Long , Book> getAllBooks(){
        return this.books;
    }

    public Book getBook(long id){
        return books.get(id);
    }

    public void deleteBook(long id){
        books.remove(id);
    }

    public void addBook(Book book){
        books.put(book.getId() , book);
    }

    public void updateBook(Book book) {
        if(!books.containsKey(book.getId())){
            return;
        }

        long id = book.getId();
        Book bookInMemory = books.get(id);

        if(book.getAuthor() != null){
            bookInMemory.setAuthor(book.getAuthor());
        }

        if(book.getName() != null){
            bookInMemory.setName(book.getName());
        }
    }


    public void putBook(Book book) {
        long id = book.getId();
        if (!books.containsKey(id)) {
            return;
        }
        books.put(id, book);
    }
}
