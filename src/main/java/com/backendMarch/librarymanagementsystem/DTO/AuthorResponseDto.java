package com.backendMarch.librarymanagementsystem.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorResponseDto {
    private String name;
    private  String mobNo;
    private List<BookResponseDto> booksWritten;
}
