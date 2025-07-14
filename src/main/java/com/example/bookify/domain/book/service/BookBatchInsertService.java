package com.example.bookify.domain.book.service;

import com.example.bookify.domain.book.domain.model.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookBatchInsertService {
    private final JdbcTemplate jdbcTemplate; // DB 연결 도구
    private static final int BATCH_SIZE = 1000; //한 번에 처리할 최대 데이터 수

    @Transactional
    public void batchInsertBooks(List<Book> books) {
        String sql = "INSERT INTO books (title, author, publisher, genre, stock, is_deleted, created_at, updated_at, deleted_at) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        for (int i = 0; i < books.size(); i += BATCH_SIZE) {
            List<Book> batchList = books.subList(i, Math.min(i + BATCH_SIZE, books.size()));
            Timestamp now = Timestamp.valueOf(LocalDateTime.now());

            jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    Book book = batchList.get(i);
                    ps.setString(1, book.getTitle());
                    ps.setString(2, book.getAuthor());
                    ps.setString(3, book.getPublisher());
                    ps.setString(4, book.getGenre());
                    ps.setInt(5, book.getStock());
                    ps.setBoolean(6, false);
                    ps.setTimestamp(7, now);
                    ps.setTimestamp(8, now);
                    ps.setTimestamp(9, now);
                }

                // 반복 횟수 반환
                @Override
                public int getBatchSize() {
                    return batchList.size();
                }
            });
        }
    }
}
