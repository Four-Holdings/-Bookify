package com.example.bookify.domain.user.domain.model;

import com.example.bookify.global.common.jpa.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Builder(access = AccessLevel.PRIVATE)
    private User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public static User of(String name, String email, String password){
        return User.builder()
                .name(name)
                .email(email)
                .password(password)
                .build();
    }

    public void updateInfo(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public void updatePassword(String password) {
        this.password = password;
    }
}
