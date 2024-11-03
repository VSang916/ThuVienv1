package com.example.library_management.dto;

import java.util.Set;

import com.example.library_management.entity.Book;

public class BookRequest {
    private String title;
    private Integer quantity;
    private Set<Long> categoryIds;
    private Set<Long> authorIds;

    // Getters và Setters

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Set<Long> getCategoryIds() {
        return categoryIds;
    }

    public void setCategoryIds(Set<Long> categoryIds) {
        this.categoryIds = categoryIds;
    }

    public Set<Long> getAuthorIds() {
        return authorIds;
    }

    public void setAuthorIds(Set<Long> authorIds) {
        this.authorIds = authorIds;
    }

    // Phương thức chuyển đổi DTO thành Entity
    public Book toBook() {
        Book book = new Book();
        book.setTitle(this.title);
        book.setQuantity(this.quantity);
        // Các thuộc tính khác nếu cần
        return book;
    }
}
