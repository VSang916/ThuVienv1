package com.example.library_management.entity;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
@Entity
@Table(name ="books")
@JsonIdentityInfo(
  generator = ObjectIdGenerators.PropertyGenerator.class, 
  property = "id")
public class Book {
    @Id 
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @Column(name ="title", nullable= false)
    private  String title;
    @Column(name = "quantity", nullable= false )
    private  Integer quantity;
    @Column(name = "link_file", nullable = false)
    private String link_file;
    @ManyToMany
    @JsonManagedReference 
    @JoinTable(
        name = "books_categories", 
        joinColumns= @JoinColumn(name ="book_id"),
        inverseJoinColumns= @JoinColumn(name ="category_id")
    )
    private Set<Category> categories;
    @ManyToMany
    @JsonManagedReference 
    @JoinTable(
        name ="book_authors",
        joinColumns= @JoinColumn(name="book_id"),
        inverseJoinColumns= @JoinColumn(name = "author_id")
    )
    private Set<Author> authors;
    @OneToOne (mappedBy= "book", cascade= CascadeType.ALL, fetch= FetchType.LAZY,optional= true)
    @JsonManagedReference
    private Inventory inventory ; 
    @OneToMany(mappedBy= "book", cascade= CascadeType.ALL, orphanRemoval=true)
    @JsonManagedReference
    private  Set<Borrowing> borrowings;
    public Book(){
    }
    public Book(String title, Integer quantity){
        this.title = title;
        this.quantity = quantity;
        this.link_file = link_file;
    }
    public Long getId(){
        return id;
    }
    public String getTitle(){
        return title;
    }
    public void setTitle(String title){
        this.title = title;
    }
    public Integer getQuantity(){
        return quantity;
    }
    public void setQuantity(Integer quantity){
        this.quantity = quantity;
    }

    public String getFile(){
        return link_file;
    }
    public void setFile(String link_file){
        this.link_file = link_file;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    public Set<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(Set<Author> authors) {
        this.authors = authors;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
        if (inventory != null) {
            inventory.setBook(this);
        }
    }

    public Set<Borrowing> getBorrowings() {
        return borrowings;
    }

    public void setBorrowings(Set<Borrowing> borrowings) {
        this.borrowings = borrowings;
    }
}
