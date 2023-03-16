package com.backendMarch.librarymanagementsystem.Service;

import com.backendMarch.librarymanagementsystem.DTO.BookRequestDto;
import com.backendMarch.librarymanagementsystem.DTO.BookResponseDto;
import com.backendMarch.librarymanagementsystem.Entity.Author;
import com.backendMarch.librarymanagementsystem.Entity.Book;
import com.backendMarch.librarymanagementsystem.Repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    @Autowired
    AuthorRepository authorRepository;

    public BookResponseDto addBook(BookRequestDto bookRequestDto)  {
        Book book = new Book();
        Author author = authorRepository.findById(bookRequestDto.getAuthorId()).get();
        book.setTitle(bookRequestDto.getTitle());
        book.setGenre(bookRequestDto.getGenre());
        book.setPrice(bookRequestDto.getPrice());
        book.setIssued(false);
        book.setAuthor(author);

        author.getBooks().add(book);
        authorRepository.save(author);

        BookResponseDto bookResponseDto = new BookResponseDto();
        bookResponseDto.setPrice(book.getPrice());
        bookResponseDto.setTitle(book.getTitle());

        return bookResponseDto;
    }
}
