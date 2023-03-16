package com.backendMarch.librarymanagementsystem.Service;

import com.backendMarch.librarymanagementsystem.DTO.StudentRequestDto;
import com.backendMarch.librarymanagementsystem.DTO.StudentResponseDto;
import com.backendMarch.librarymanagementsystem.DTO.StudentUpdateEmailRequestDto;
import com.backendMarch.librarymanagementsystem.Entity.LibraryCard;
import com.backendMarch.librarymanagementsystem.Entity.Student;
import com.backendMarch.librarymanagementsystem.Enum.CardStatus;
import com.backendMarch.librarymanagementsystem.Repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentService {

    @Autowired
    StudentRepository studentRepository;

    public void addStudent(StudentRequestDto studentRequestDto){

        // set the value of card
        Student student = new Student();
        student.setAge(studentRequestDto.getAge());
        student.setName(studentRequestDto.getName());
        student.setDepartment(studentRequestDto.getDepartment());
        student.setEmail(studentRequestDto.getEmail());

        LibraryCard card = new LibraryCard();
        card.setStatus(CardStatus.ACTIVATED);
        card.setStudent(student);
        student.setCard(card);

        studentRepository.save(student);
    }

    public String findByEmail(String email){
        Student student = studentRepository.findByEmail(email);
        return  student.getName();
    }

    public List<String> findByAge(int age){
        List<Student> studentList = studentRepository.findByAge(age);
        List<String> studentListByAge = new ArrayList<>();
        for(Student student : studentList){
            studentListByAge.add(student.getName());
        }
        return studentListByAge;
    }
    public StudentResponseDto updateEmail(StudentUpdateEmailRequestDto studentUpdateEmailRequestDto){
        Student student = studentRepository.findById(studentUpdateEmailRequestDto.getId()).get();
        student.setEmail(studentUpdateEmailRequestDto.getEmail());

        Student updatedStudent = studentRepository.save(student);

        StudentResponseDto studentResponseDto = new StudentResponseDto();
        studentResponseDto.setId(updatedStudent.getId());
        studentResponseDto.setName(updatedStudent.getName());
        studentResponseDto.setEmail(updatedStudent.getEmail());

        return studentResponseDto;
    }
}