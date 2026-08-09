package com.example.library.service;

import com.example.library.dto.Book;
import org.springframework.stereotype.Service;

import javax.print.attribute.HashAttributeSet;
import java.util.*;

@Service
public class BookService {
    private HashMap<Long , Book> books = new HashMap<>();

    public List<Book> getAllBooks(){
        return books.values().stream().sorted(Comparator.comparingLong(Book::getId)).toList();
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

    public Book updateBook(Book book) {
        if(!books.containsKey(book.getId())){
            return null;
        }

        long id = book.getId();
        Book bookInMemory = books.get(id);

        if(book.getAuthor() != null){
            bookInMemory.setAuthor(book.getAuthor());
        }

        if(book.getName() != null){
            bookInMemory.setName(book.getName());
        }
        return bookInMemory;
    }


    public void putBook(Book book) {
        long id = book.getId();
        if (!books.containsKey(id)) {
            return;
        }
        books.put(id, book);
    }
}
