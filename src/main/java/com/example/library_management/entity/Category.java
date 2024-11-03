package com.example.library_management.entity;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
@Entity
@Table(name = "categories")
public class Category {
    @Id 
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @Column(name ="category_name", nullable= false, unique=true)
    private String categoryName;
    @ManyToMany(mappedBy= "categories")
    private Set<Book> books; 
    public Category(){}
    public Category(String categoryName){
        this.categoryName = categoryName;
    }
    public Long getId(){
        return id;
    }
    public String getCategoryName(){
        return categoryName;
    }
    public void setCategoryName(String categoryName){
        this.categoryName = categoryName;
    }
    public Set<Book> getBooks(){
        return books;
    }
    public void setBooks(Set<Book> books){
        this.books = books;
    }

}
