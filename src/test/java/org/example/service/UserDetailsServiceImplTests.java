package org.example.service;

import org.example.entity.User;
import org.example.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

public class UserDetailsServiceImplTests {

    //@InjectMocks automatically creates the bean of the class on which it is used.
    @InjectMocks
    UserDetailsServiceImpl userDetailsService;

    @Mock
    private UserRepository userRepository;

    //Initialize all the beans with @Mock and inject them in the bean with @InjectMock
    @BeforeEach
    void setUp(){
        MockitoAnnotations.initMocks(this);
    }




    @Test
    @Disabled
    void loadUserByUsernameTest(){
        /*
            when(userRepository.findByUserName("shyam")).thenReturn(User.builder().userName("ram").password("abcabc").build());
            // matches the argument shyam and returns the user only when shyam argument i spassed
         */
        when(userRepository.findByUserName(ArgumentMatchers.anyString())).thenReturn(User.builder().userName("ram").password("abcabc").roles(new ArrayList<>()).build()); // returns ram for any input string
        UserDetails userDetails = userDetailsService.loadUserByUsername("ram");
        assertNotNull(userDetails);
    }
}
