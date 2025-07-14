package com.example.bookify.book.performance;

import com.example.bookify.domain.book.domain.model.Book;
import com.example.bookify.domain.book.domain.repository.BookRepository;
import com.example.bookify.domain.book.fixture.BookFixture;
import com.example.bookify.domain.book.service.BookBatchInsertService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class BookBulkInsertPerformanceTest {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookBatchInsertService bookBatchInsertService;

    private final int TEST_COUNT = 1000;

    @Test
    void bulkInsertWithSave() {
        List<Book> books = BookFixture.generateBooks(TEST_COUNT);

        long start = System.currentTimeMillis();
        for (Book book : books) {
            bookRepository.save(book);
        }
        bookRepository.flush();
        long end = System.currentTimeMillis();

        System.out.println("< JPA save() 사용 >");
        System.out.println(TEST_COUNT + "건 입력 실행시간 : " + (end - start) + "ms(" + ((end - start)/1000.0) + "s)");
    }

    @Test
    void bulkInsertWithSaveAll() {
        List<Book> books = BookFixture.generateBooks(TEST_COUNT);

        long start = System.currentTimeMillis();
        bookRepository.saveAll(books);
        long end = System.currentTimeMillis();

        System.out.println("< JPA saveAll() 사용 >");
        System.out.println(TEST_COUNT + "건 입력 실행시간 : " + (end - start) + "ms(" + ((end - start)/1000.0) + "s)");
    }

    @Test
    void bulkInsertWithJdbcTemplate() {
        List<Book> books = BookFixture.generateBooks(TEST_COUNT);

        long start = System.currentTimeMillis();
        bookBatchInsertService.batchInsertBooks(books);
        long end = System.currentTimeMillis();

        System.out.println("< JDBC batch insert 사용 >");
        System.out.println(TEST_COUNT + "건 입력 실행시간 : " + (end - start) + "ms(" + ((end - start)/1000.0) + "s)");
    }
}
