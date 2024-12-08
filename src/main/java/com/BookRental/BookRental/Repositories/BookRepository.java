package com.BookRental.BookRental.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.BookRental.BookRental.Entites.BookRental;
import com.BookRental.BookRental.Entites.Books;
import com.BookRental.BookRental.Entites.Enum.Status;


@Repository
public interface BookRepository extends JpaRepository<Books,Long>{
    List<Books> findByAvailabilityStatus(Status available);
} 
