package com.todolistbackend.forgetpassword.dto;

import lombok.Builder;

@Builder
public record MailBody(String mailTo, String subject, String body){

}
