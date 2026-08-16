package com.example.library.persistence;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "member")
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 50)
    private String name;

    @Column(length = 255)
    private String email;

    @ManyToOne
    @JoinColumn(name = "referred_by")
    private Member referredBy;

    @OneToMany(mappedBy = "referredBy")
    private List<Member> referredMembers = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Loan> loans = new ArrayList<>();

    public Member() {
    }
}