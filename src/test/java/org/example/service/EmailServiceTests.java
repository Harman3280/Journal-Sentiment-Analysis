package org.example.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

//Created for testing email functionality in EmailService.
@SpringBootTest
public class EmailServiceTests {

    @Autowired
    EmailService emailService;

    @Test
    public void testEmailSend(){
        emailService.sendEmail("harmansingh.mnnit2019@gmail.com", "Java Mail Sender Test", "Hola AMIGO !!");
    }

}
