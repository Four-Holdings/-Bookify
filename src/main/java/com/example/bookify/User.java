package com.example.bookify;

import com.example.bookify.global.common.jpa.BaseEntity;
import com.example.bookify.global.common.jpa.SoftDeleteEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User extends SoftDeleteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
}
