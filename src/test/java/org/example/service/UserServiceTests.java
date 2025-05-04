package org.example.service;

import org.example.entity.User;
import org.example.repository.UserRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServiceTests {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Test
    @Disabled
    public void testFindByUserName(){
        assertEquals(4, 2+2);
        assertNotNull(userRepository.findByUserName("ram"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"ram", "Rohan", "Karan"})
    @Disabled
    public void testFindByUserName_2(String name){
        assertNotNull(userRepository.findByUserName(name), "Failed for name : "+name);
    }

    //We can also use @ENUMSource to pass an enum

    @ParameterizedTest
    @ArgumentsSource(UserArgumentsProvider.class)
    @Disabled
    public void testSaveNewUser(User user){
        assertTrue(userService.saveNewUser(user), "Failed for user : "+user);
    }



    @ParameterizedTest
    @CsvSource({
            "1,1,2",
            "2,10,2",
            "3,2,9"
    })
    @Disabled
    public void testAddition(int a, int b, int expected){
        assertEquals(expected, a+b, "failed for "+(a+b));
    }



}
