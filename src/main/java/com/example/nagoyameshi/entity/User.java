package com.example.nagoyameshi.entity;

 import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
 
 @Entity
 @Table(name = "users")
 @Data
public class User {
     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     @Column(name = "id")
     private Integer id;
 
     @Column(name = "full_name")
     private String fullName;
 
     @Column(name = "kana")
     private String kana;
 
     @Column(name = "email")
     private String email;
 
     @Column(name = "enabled")
     private boolean enabled;
 
     @Column(name = "password")
     private String password;
 
     @Column(name = "paid_license")
	 public boolean paidLicense;
 
     @Column(name = "admin")
     private boolean admin;
 
     @Column(name = "created_at", insertable = false, updatable = false)
     private Timestamp createdAt;
 
     @Column(name = "updated_at", insertable = false, updatable = false)
     private Timestamp updatedAt;
		
	
}

