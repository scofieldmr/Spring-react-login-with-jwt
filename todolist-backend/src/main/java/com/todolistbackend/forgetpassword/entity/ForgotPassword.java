package com.todolistbackend.forgetpassword.entity;

import com.todolistbackend.entity.MyUsers;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "forgot_password")
public class ForgotPassword {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fpid;

    @Column(nullable = false)
    private Long otp;

    @Column(nullable = false)
    private Date expirationTime;

    @OneToOne
    private MyUsers user;
}
