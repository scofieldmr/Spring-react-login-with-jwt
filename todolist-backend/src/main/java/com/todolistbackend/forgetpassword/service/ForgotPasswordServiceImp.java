package com.todolistbackend.forgetpassword.service;

import com.todolistbackend.entity.MyUsers;
import com.todolistbackend.exception.PasswordMismatchException;
import com.todolistbackend.forgetpassword.dto.ChangePasswordDto;
import com.todolistbackend.forgetpassword.dto.MailBody;
import com.todolistbackend.forgetpassword.entity.ForgotPassword;
import com.todolistbackend.forgetpassword.exception.InvalidOtpException;
import com.todolistbackend.forgetpassword.exception.OTPExpiredException;
import com.todolistbackend.forgetpassword.exception.UserEmailNotFoundException;
import com.todolistbackend.forgetpassword.repo.ForgotPasswordRepository;
import com.todolistbackend.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.Random;

@Service
public class ForgotPasswordServiceImp implements ForgotPasswordService {

    private static Logger logger = LoggerFactory.getLogger(ForgotPasswordServiceImp.class);

    private final ForgotPasswordRepository forgotPasswordRepository;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final EmailService emailService;

    public ForgotPasswordServiceImp(ForgotPasswordRepository forgotPasswordRepository, UserRepository userRepository, PasswordEncoder passwordEncoder, EmailService emailService) {
        this.forgotPasswordRepository = forgotPasswordRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    @Override
    public String verifyEmail(String email) {

        MyUsers findUser = userRepository.findByEmail(email);
        if (findUser == null) {
            throw new UserEmailNotFoundException("Kindly enter a valid email");
        }

        long otp = otpGenerator();

        ForgotPassword oldForgotPassword = forgotPasswordRepository.findByUser(findUser);

        if (oldForgotPassword != null) {
            oldForgotPassword.setOtp(otp);
            oldForgotPassword.setExpirationTime(new Date(System.currentTimeMillis() + 5 * 60 * 1000));

            forgotPasswordRepository.save(oldForgotPassword);

            logger.info("Old Forgot Password fpId : {}", oldForgotPassword.getFpid());
        }
        else{
            ForgotPassword forgotPassword = new ForgotPassword();
            forgotPassword.setOtp(otp);
            forgotPassword.setExpirationTime(new Date(System.currentTimeMillis() + 5 * 60 *10000));  // 3min 20sec
            forgotPassword.setUser(findUser);

            forgotPasswordRepository.save(forgotPassword);

            logger.info("New Forgot Password fpId: {}", forgotPassword.getFpid());
        }

        MailBody mailBody = MailBody.builder()
                .mailTo(email)
                .subject("OTP for the Forgot Password Request")
                .body("This is the OTP for the forgot password request : "+ otp + "\n"
                        +"Kindly verify the OTP in 5 minutes")
                .build();

        emailService.sendEmail(mailBody);

        logger.info("OTP sent to the email : {} ",email);

        return "Email verified .. OTP send to the registered email..";
    }

    @Override
    public String verifyOtp(String email, Long otp) {
        MyUsers findUser = userRepository.findByEmail(email);
        if (findUser == null) {
            throw new UserEmailNotFoundException("Kindly enter a valid email");
        }

        ForgotPassword forgotPassword = forgotPasswordRepository.findByOtpAndUser(otp,findUser)
                .orElseThrow(()-> new InvalidOtpException("Invalid OTP.. Kindly enter a valid OTP"));

        if(forgotPassword.getExpirationTime().before(Date.from(Instant.now()))){
            forgotPasswordRepository.deleteById(forgotPassword.getFpid());
            throw new OTPExpiredException("OTP Expired. Kindly try again.");
        }

        logger.info("OTP Verified successfully for the user : {} ",forgotPassword.getUser().getEmail());

        return "OTP Verified Successfully";
    }

    @Override
    @Transactional
    public String passwordChange(String email, ChangePasswordDto changePasswordDto) {

        if(!changePasswordDto.getNewPassword().equals(changePasswordDto.getRepeatNewPassword())){
            throw new PasswordMismatchException("Password Mismatch. Kindly retry again");
        }

        MyUsers findUser = userRepository.findByEmail(email);
        if (findUser == null) {
            throw new UserEmailNotFoundException("Kindly enter a valid email");
        }

        String encodedPassword = passwordEncoder.encode(changePasswordDto.getNewPassword());
        findUser.setPassword(encodedPassword);

        MyUsers updateUser = userRepository.save(findUser);

        logger.info("Password changes successfully for the user : {}",updateUser.getEmail());

        return "Password Changed Successfully";
    }

    private long otpGenerator() {
        Random random = new Random();
        return random.nextInt(100_000,999_999);
    }
}
