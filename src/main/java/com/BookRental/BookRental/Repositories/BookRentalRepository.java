package com.BookRental.BookRental.Repositories;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.stereotype.Repository;

import com.BookRental.BookRental.Entites.BookRental;
import com.BookRental.BookRental.Entites.Books;
import com.BookRental.BookRental.Entites.User;
import com.BookRental.BookRental.Entites.Enum.Status;
@Repository
public interface BookRentalRepository extends JpaRepository<BookRental,Long>{
    long countByUserAndStatus(User user, Status status);
    Optional<BookRental> findByBookAndUserAndStatus(Books book, User user, Status status);
}

