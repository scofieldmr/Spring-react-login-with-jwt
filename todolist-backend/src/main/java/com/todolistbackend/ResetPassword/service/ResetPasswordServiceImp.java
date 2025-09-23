package com.todolistbackend.ResetPassword.service;

import com.todolistbackend.ResetPassword.dto.ResetPasswordDto;
import com.todolistbackend.entity.MyUsers;
import com.todolistbackend.exception.IncorrectPasswordException;
import com.todolistbackend.exception.PasswordMismatchException;
import com.todolistbackend.forgetpassword.dto.MailBody;
import com.todolistbackend.forgetpassword.exception.UserEmailNotFoundException;
import com.todolistbackend.forgetpassword.service.EmailService;
import com.todolistbackend.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ResetPasswordServiceImp implements ResetPasswordService {

    private final Logger log = LoggerFactory.getLogger(ResetPasswordServiceImp.class);

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final EmailService emailService;

    public ResetPasswordServiceImp(UserRepository userRepository, PasswordEncoder passwordEncoder, EmailService emailService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    @Transactional
    @Override
    public String resetUserPassword(String email, ResetPasswordDto resetPasswordDto) {

        if(!resetPasswordDto.getNewPassword().equals(resetPasswordDto.getConfirmNewPassword())){
            throw new PasswordMismatchException("New Password and Confirm Passwords not matching");
        }

        MyUsers user = userRepository.findByEmail(email);

        if (user == null) {
            throw new UserEmailNotFoundException("User not found with the email: " + email);
        }

        if(!passwordEncoder.matches(resetPasswordDto.getCurrentPassword(), user.getPassword())){
            throw new IncorrectPasswordException("Incorrect Current Password");
        }

        String newEncodedPassword = passwordEncoder.encode(resetPasswordDto.getNewPassword());
        user.setPassword(newEncodedPassword);
        userRepository.save(user);
        userRepository.flush();

        MailBody mailBody = MailBody.builder()
                .mailTo(user.getEmail())
                .subject("Password Reset")
                .body("Password Reseted Successfully. Kindly login with the new password..")
                .build();

        emailService.sendEmail(mailBody);

        return "Password Reset Successfully";
    }
}
