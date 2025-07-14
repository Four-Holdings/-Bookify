package com.example.bookify.domain.book.fixture;

import com.example.bookify.domain.book.domain.model.Book;

import java.util.ArrayList;
import java.util.List;

public class BookFixture {
    public static List<Book> generateBooks(int count) {
        List<Book> books = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            books.add(Book.builder()
                    .title("도서" + i)
                    .author("저자" + i)
                    .publisher("출판사" + (i % 10))
                    .genre("장르" + (i % 10))
                    .stock(i % 10)
                    .build());
        }
        return books;
    };
}
