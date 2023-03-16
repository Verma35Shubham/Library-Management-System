package com.backendMarch.librarymanagementsystem.Service;

import com.backendMarch.librarymanagementsystem.DTO.IssueBookRequestDto;
import com.backendMarch.librarymanagementsystem.DTO.IssueBookResponseDto;
import com.backendMarch.librarymanagementsystem.Entity.Book;
import com.backendMarch.librarymanagementsystem.Entity.LibraryCard;
import com.backendMarch.librarymanagementsystem.Entity.Transaction;
import com.backendMarch.librarymanagementsystem.Enum.CardStatus;
import com.backendMarch.librarymanagementsystem.Enum.TransactionStatus;
import com.backendMarch.librarymanagementsystem.Repository.BookRepository;
import com.backendMarch.librarymanagementsystem.Repository.CardRepository;
import com.backendMarch.librarymanagementsystem.Repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TransactionService {
    @Autowired
    CardRepository cardRepository;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    private JavaMailSender emailSender;

    public IssueBookResponseDto issueBook(IssueBookRequestDto issueBookRequestDto) throws Exception {
        Transaction transaction = new Transaction();
        transaction.setTransactionNumber(String.valueOf(UUID.randomUUID()));
        transaction.setIssuedOperation(true);


        LibraryCard card;
        try{
            card = cardRepository.findById(issueBookRequestDto.getCardId()).get();
        }
        catch(Exception e){
            transaction.setTransactionStatus(TransactionStatus.FAILED);
            transaction.setMessage("Invalid card id");
            transactionRepository.save(transaction);
            throw new Exception("Invalid card id");
        }

        Book book;
        try{
            book = bookRepository.findById(issueBookRequestDto.getBookId()).get();
        }
        catch (Exception e){
            transaction.setTransactionStatus(TransactionStatus.FAILED);
            transaction.setMessage("Invalid Book id");
            transactionRepository.save(transaction);
            throw new Exception("Invalid Book id");
        }
        transaction.setBook(book);
        transaction.setCard(card);

        if(card.getStatus() != CardStatus.ACTIVATED){
            transaction.setTransactionStatus(TransactionStatus.FAILED);
            transaction.setMessage("Your card is not activated");
            transactionRepository.save(transaction);
            throw new Exception("Your card is not activated");
        }
        if(book.isIssued() == true){
            transaction.setTransactionStatus(TransactionStatus.FAILED);
            transaction.setMessage("Sorry! Book is already issued.");
            transactionRepository.save(transaction);
            throw new Exception("Sorry! Book is already issued.");
        }

        transaction.setTransactionStatus(TransactionStatus.SUCCESS);
        transaction.setMessage("Transaction Successful");

        book.setIssued(true);
        book.setCard(card);
        book.getTransaction().add(transaction);
        card.getTransactionList().add(transaction);
        card.getBookIssued().add(book);

        cardRepository.save(card);

        IssueBookResponseDto issueBookResponseDto = new IssueBookResponseDto();

        issueBookResponseDto.setTransactionId(transaction.getTransactionNumber());
        issueBookResponseDto.setTransactionStatus(TransactionStatus.SUCCESS);
        issueBookResponseDto.setBookName(book.getTitle());

        String text = "Congrats !!." + card.getStudent().getName()+ "You have been issued "+book.getTitle()+" book.";

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("tanu20215@gmail.com");
        message.setTo(card.getStudent().getEmail());
        message.setSubject("Issued Book Notification");
        message.setText(text);
        emailSender.send(message);

        return issueBookResponseDto;
    }
    public String getAllTxns(int cardId){
        List<Transaction> transactionList = transactionRepository.getAllSuccessfullTxnsWithCardNo(cardId);
        String ans = "";
        for(Transaction t: transactionList){
            ans += t.getTransactionNumber();
            ans += "\n";
        }

        return ans;
    }
}
