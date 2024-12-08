package com.BookRental.BookRental.Entites;

import com.BookRental.BookRental.Entites.Enum.Role;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.Collection;
import java.util.List;

import jakarta.persistence.Id;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
@Data
@Entity
@Table(name = "users")
public class User implements UserDetails {
   
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long Id;

   @Column(nullable = false)
   private String FirstName;

   @Column( nullable = false)
   private String LastName;

   @Column(unique = true, nullable = false)
   private String email;

   @Column(nullable = false)
   private String Password;

   @Column(nullable = false)
   private Role role;

   @Override
   public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority(role.name()));
   }

   @Override
   public String getUsername() {
       return email;
   }

   @Override
   public String getPassword() {
       return Password;
   }

   @Override
   public boolean isAccountNonExpired() {
       return true;
   }

   @Override
   public boolean isAccountNonLocked() {
       return true;
   }

   @Override
   public boolean isCredentialsNonExpired() {
       return true;
   }

   @Override
   public boolean isEnabled() {
       return true;
   }
}
