package com.BookRental.BookRental.Controllers;
import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.BookRental.BookRental.Dtos.AuthResponse;
import com.BookRental.BookRental.Dtos.Logindata;
import com.BookRental.BookRental.Dtos.RegisterData;
import com.BookRental.BookRental.Entites.User;
import com.BookRental.BookRental.Services.AuthService;
import com.BookRental.BookRental.Services.UserService;

@RestController
//@RequestMapping("/bookrental")
public class Authentication {
    @Autowired
    private AuthService authservice;
    //used to register new users and in return if success then a message saying success
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterData data)
    {
        return ResponseEntity.ok(authservice.register(data));
    }
    //used to login existing the new users in the system for the new persons who want to enroll so that they can get the permission to access the data and can rent the book acc to requirement
    @PostMapping("/Login")
    public ResponseEntity<AuthResponse> Login(@RequestBody Logindata login)
    {
        return ResponseEntity.ok(authservice.Login(login));
    }
}
