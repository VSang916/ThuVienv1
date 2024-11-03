package com.example.library_management.dto;

public class CategoryRequest {
    private String categoryName;

    // Getters và Setters

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    // Phương thức chuyển đổi DTO thành Entity
    public Category toCategory() {
        Category category = new Category();
        category.setCategoryName(this.categoryName);
        return category;
    }
}
