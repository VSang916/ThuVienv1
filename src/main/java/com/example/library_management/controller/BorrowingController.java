package com.example.library_management.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.library_management.dto.BorrowingRequest;
import com.example.library_management.entity.Borrowing;
import com.example.library_management.exception.ResourceNotFoundException;
import com.example.library_management.service.BorrowingService;

@RestController
@RequestMapping("/api/borrowings")
public class BorrowingController {
    
    private final BorrowingService borrowingService;

    public BorrowingController(BorrowingService borrowingService){
        this.borrowingService = borrowingService;
    }

    // Lấy tất cả các lần mượn
    @GetMapping
    public List<Borrowing> getAllBorrowings(){
        return borrowingService.getAllBorrowings();
    }

    // Lấy lần mượn theo ID
    @GetMapping("/{id}")
    public ResponseEntity<Borrowing> getBorrowingById(@PathVariable Long id){
        try {
            Borrowing borrowing = borrowingService.getBorrowingById(id);
            return ResponseEntity.ok(borrowing);
        } catch (ResourceNotFoundException ex){
            return ResponseEntity.notFound().build();
        }
    }

    // Tạo lần mượn mới
    @PostMapping
    public ResponseEntity<Borrowing> createBorrowing(@RequestBody BorrowingRequest borrowingRequest){
        Borrowing createdBorrowing = borrowingService.createBorrowing(
                borrowingRequest.getReaderId(),
                borrowingRequest.getBookId(),
                borrowingRequest.getBorrowDate(),
                borrowingRequest.getReturnDate()
        );
        return ResponseEntity.status(201).body(createdBorrowing);
    }

    // Trả sách (cập nhật lần mượn)
    @PutMapping("/return/{id}")
    public ResponseEntity<Borrowing> returnBook(@PathVariable Long id, @RequestParam(required = false) String actualReturnDate){
        try {
            Borrowing updatedBorrowing = borrowingService.returnBook(id, 
                    actualReturnDate != null ? LocalDate.parse(actualReturnDate) : LocalDate.now());
            return ResponseEntity.ok(updatedBorrowing);
        } catch (ResourceNotFoundException | IllegalStateException ex){
            return ResponseEntity.badRequest().body(null);
        }
    }

    // Xóa lần mượn
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBorrowing(@PathVariable Long id){
        try {
            borrowingService.deleteBorrowing(id);
            return ResponseEntity.noContent().build();
        } catch (Exception ex){
            return ResponseEntity.notFound().build();
        }
    }
}
