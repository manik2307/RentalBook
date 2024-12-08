package com.BookRental.BookRental.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.BookRental.BookRental.Dtos.AuthResponse;
import com.BookRental.BookRental.Dtos.BookUpdate;
import com.BookRental.BookRental.Entites.BookRental;
import com.BookRental.BookRental.Entites.Books;
import com.BookRental.BookRental.Entites.User;
import com.BookRental.BookRental.Entites.Enum.Status;
import com.BookRental.BookRental.Repositories.BookRentalRepository;
import com.BookRental.BookRental.Services.BookService;

@RestController
@RequestMapping("/bookrental/books")
public class BookContoller {
    //posting a new book(admin)
    //updating the book (admin)
    //deleting the book(admin)
    //get book by id(admin,user)
       //get book on rent(admin,user)
    //return book on rent(admin,user)
    //get all the books available for the rent(admin,user)
     @Autowired
    private BookService bookService;

    // Posting a new book (admin only)
    @PostMapping
    public ResponseEntity<AuthResponse> addBook(@RequestBody Books book) {
        return ResponseEntity.ok(bookService.addBook(book));
    }

    // Updating a book (admin only)
    @PutMapping("/{id}")
    public ResponseEntity<Books> updateBook(@PathVariable Long id, @RequestBody BookUpdate book) {
        return ResponseEntity.ok(bookService.updateBook(id, book));
    }

    // Deleting a book (admin only)
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.ok("Book deleted successfully");
    }

    // Get all books(admin, user)
    @GetMapping
    public ResponseEntity<List<Books>> getAllBooks() {
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    // Get book by id (admin, user)
    @GetMapping("/{id}")
    public ResponseEntity<Books> getBookById(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.getBookById(id));
    }


    //get the all the books which are availabe for rent
    @GetMapping("/available")
    public ResponseEntity<List<Books>> getAvailableBooksCount() {
        //long count = bookRepository.countByAvailabilityStatus(Status.AVAILABLE);
        List<Books> list=bookService.getBooksAvailable();
        return ResponseEntity.ok(list);
    }
   
//this annotation is used to rent the book for the user 
    @PostMapping("/{bookId}/rent")
     public ResponseEntity<String> rentBook(@PathVariable Long bookId, @AuthenticationPrincipal User user) {
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User is not authenticated.");
        }
        return bookService.rentBook(bookId,user);
}

//this annotation is used to return the book 
@PostMapping("/{bookId}/return")
public ResponseEntity<String> returnBook(@PathVariable Long bookId, @AuthenticationPrincipal User user) {
    if (user == null) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User is not authenticated.");
    }
    return bookService.returnBook(bookId,user);
}


}
