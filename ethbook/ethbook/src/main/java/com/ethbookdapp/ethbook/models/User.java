package com.ethbookdapp.ethbook.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.sql.Date;

@Entity
@Table(name = "user")
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "user_id")
    private String userId;
    @Column(name = "name")
    private String name;
    @Column(name ="email")
    private String email;

    @Column(name = "password")
    @JsonIgnore
    private String password;
    @Column(name = "created_at")
//    @CreatedDate
    @CreationTimestamp
    private Date createdAt;
    @Column(name = "updated_at")
    @UpdateTimestamp
//    @LastModifiedDate
    private Date updatedAt;

    @OneToOne(cascade = CascadeType.ALL,mappedBy = "user")
    @JsonManagedReference
    private Address address;
}
