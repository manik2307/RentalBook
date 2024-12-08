package com.BookRental.BookRental.Dtos;

import jakarta.persistence.Column;
import lombok.Data;
@Data
public class BookUpdate {

    private String title;
    private String author;
    private String genre;
}
