package com.todolistbackend.forgetpassword.repo;

import com.todolistbackend.entity.MyUsers;
import com.todolistbackend.forgetpassword.entity.ForgotPassword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ForgotPasswordRepository extends JpaRepository<ForgotPassword, Long> {

    @Query("select fp from ForgotPassword fp where fp.otp =?1 and fp.user=?2")
    Optional<ForgotPassword> findByOtpAndUser(Long otp, MyUsers user);


    @Query("select fp from ForgotPassword fp where fp.user=?1")
    ForgotPassword findByUser(MyUsers user);
}
