package com.backendMarch.librarymanagementsystem.Service;

import com.backendMarch.librarymanagementsystem.DTO.AuthorResponseDto;
import com.backendMarch.librarymanagementsystem.DTO.BookResponseDto;
import com.backendMarch.librarymanagementsystem.Entity.Author;
import com.backendMarch.librarymanagementsystem.Entity.Book;
import com.backendMarch.librarymanagementsystem.Repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthorService {
    @Autowired
    AuthorRepository authorRepository;

    public void addAuthor(Author author){
        authorRepository.save(author);
    }
    public AuthorResponseDto getAuthor(Integer id){
        Author author = authorRepository.findById(id).get();
        List<Book> bookList = author.getBooks();

        List<BookResponseDto> bookResponseDtoList = new ArrayList<>();

        for (Book b : bookList){
            BookResponseDto bookResponseDto = new BookResponseDto();
            bookResponseDto.setTitle(b.getTitle());
            bookResponseDto.setPrice(b.getPrice());

            bookResponseDtoList.add(bookResponseDto);
        }

        AuthorResponseDto authorResponseDto = new AuthorResponseDto();
        authorResponseDto.setName(author.getName());
        authorResponseDto.setMobNo(author.getMobNo());
        authorResponseDto.setBooksWritten(bookResponseDtoList);

        return authorResponseDto;
    }
}
