package com.example.memorydb.book.service;

import com.example.memorydb.book.db.entity.BookEntity;
import com.example.memorydb.book.db.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor // 생성자 메서드로 얘를 받겠다.
public class BookService {

    private final BookRepository bookRepository;

    // create, update
    public BookEntity create(BookEntity book) {
        return bookRepository.save(book);
    }

    // all
    public List<BookEntity> findAll(){
        return bookRepository.findAll();
    }

    // delete
}
