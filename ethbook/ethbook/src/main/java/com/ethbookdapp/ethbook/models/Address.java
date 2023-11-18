package com.ethbookdapp.ethbook.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
@Table(name = "address")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "address")
    private String address;
    @OneToOne
    @JoinColumn(name ="user_id")
    @JsonBackReference
    private User user;
}
