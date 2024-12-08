package com.BookRental.BookRental.Dtos;

import com.BookRental.BookRental.Entites.Enum.Role;
import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.persistence.Column;
import lombok.Data;
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RegisterData {
   private String FirstName;
   private String LastName;
   private String Email;
   private String Password;
   private Role role;
}
