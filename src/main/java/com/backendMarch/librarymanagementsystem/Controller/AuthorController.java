package com.backendMarch.librarymanagementsystem.Controller;


import com.backendMarch.librarymanagementsystem.DTO.AuthorResponseDto;
import com.backendMarch.librarymanagementsystem.Entity.Author;
import com.backendMarch.librarymanagementsystem.Service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/author")
public class AuthorController {
    @Autowired
    AuthorService authorService;
    @PostMapping("/add")
    public String addAuthor(@RequestBody  Author author){
        authorService.addAuthor(author);
        return "Author added successfully.";
    }
    @GetMapping("/get_author")
    public AuthorResponseDto getAuthors(@RequestParam("authorId") Integer authorId){

        return authorService.getAuthor(authorId);
    }
}

