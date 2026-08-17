package com.example.library.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Book {
    private Integer id;
    private String name;
    private String author;

    public Book(Integer id , String name , String author){
        setId(id);
        setName(name);
        setAuthor(author);
    }
}
