package com.backendMarch.librarymanagementsystem.DTO;

import com.backendMarch.librarymanagementsystem.Enum.TransactionStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class IssueBookResponseDto {

    private String transactionId;
    private String bookName;
    private String priceName;
    private TransactionStatus transactionStatus;
}
