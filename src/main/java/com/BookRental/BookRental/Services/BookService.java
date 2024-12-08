package com.BookRental.BookRental.Services;

import java.util.List;
import java.util.Optional;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.BookRental.BookRental.Dtos.AuthResponse;
import com.BookRental.BookRental.Dtos.BookUpdate;
import com.BookRental.BookRental.Entites.BookRental;
import com.BookRental.BookRental.Entites.Books;
import com.BookRental.BookRental.Entites.User;
import com.BookRental.BookRental.Entites.Enum.Status;
import com.BookRental.BookRental.Repositories.BookRentalRepository;
import com.BookRental.BookRental.Repositories.BookRepository;

@Service
public class BookService {
     @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookRentalRepository bookRentalRepository;

    // Add a new book
    public AuthResponse addBook(Books book) {
        //check if the book status is available not rented 
        //if rented then also change to available 
        if (book.getAvailabilityStatus() == Status.Rented) {
            book.setAvailabilityStatus(Status.Available);
        }
        bookRepository.save(book);
        return AuthResponse.builder().build();
    }

    // Update book details
    public Books updateBook(Long id, BookUpdate book) {
        Optional<Books> existingBook = bookRepository.findById(id);
        if (existingBook.isPresent()) {
            Books updatedBook = existingBook.get();
            updatedBook.setTitle(book.getTitle());
            updatedBook.setAuthor(book.getAuthor());
            updatedBook.setGenre(book.getGenre());
            return bookRepository.save(updatedBook);
        } else {
            throw new RuntimeException("Book not found");
        }
    }

    // Delete book by id
    public void deleteBook(Long id) {
        //this method check whether the book is present 
        Books b=getBookById(id);
        bookRepository.deleteById(b.getId());
    }

    // Get all books available for rent
    public List<Books> getAllBooks() {
        return bookRepository.findAll();
    }

    // Get book by id
    public Books getBookById(Long id) {
        return bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Book not found"));
    }

    // Get books that are avalable for rent
    public List<Books> getBooksAvailable() {
        //find all the books that are avalable for rent and retuen that
       return bookRepository.findByAvailabilityStatus(Status.Available);  
    }

    //get a book for rent by the user 
    public ResponseEntity<String> rentBook(Long bookId,User user)
    {
        if (bookRentalRepository.countByUserAndStatus(user, Status.Rented) >= 2) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("You can only rent up to 2 books.");
    }

    Books book =getBookById(bookId);

    if (book.getAvailabilityStatus() != Status.Available) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Book is not available for rent.");
    }

    book.setAvailabilityStatus(Status.Rented);
    bookRepository.save(book);

    BookRental rental = new BookRental();
    rental.setBook(book);
    rental.setUser(user);
    rental.setStatus(Status.Rented);
    bookRentalRepository.save(rental);
    return ResponseEntity.ok("Book rented successfully!");
    }


    // Return book form the usr and cahnge the status
    public ResponseEntity<String> returnBook(Long bookId, User user) {
        Books book = getBookById(bookId);
        
        BookRental rental = bookRentalRepository.findByBookAndUserAndStatus(book, user, Status.Rented)
        .orElseThrow(() -> new IllegalArgumentException("No active rental found for this book."));
        
        rental.setStatus(Status.Available);
        bookRentalRepository.save(rental);
        
        book.setAvailabilityStatus(Status.Available);
        bookRepository.save(book);
        
        return ResponseEntity.ok("Book returned successfully!");
     }

}
