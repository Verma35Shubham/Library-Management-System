package com.backendMarch.librarymanagementsystem.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthorRequestDTO {

    private String name;
    private int age;
    private String mobNo;
    private String email;
}
