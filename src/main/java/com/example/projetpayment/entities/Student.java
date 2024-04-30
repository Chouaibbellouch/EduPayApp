package com.example.projetpayment.entities;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@NoArgsConstructor @AllArgsConstructor @Getter @Setter @ToString @Builder
public class Student {
    @Id
    private String id;
    private String name;
    private int age;
    @Column(unique=true)
    private String code;
    private String programId;
}
