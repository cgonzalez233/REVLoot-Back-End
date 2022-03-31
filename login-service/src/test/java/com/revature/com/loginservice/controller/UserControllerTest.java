package com.revature.com.loginservice.controller;

import com.revature.com.loginservice.entity.User;
import com.revature.com.loginservice.exception.UserException;
import com.revature.com.loginservice.service.UserServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserControllerTest {

    private static UserServiceImpl service;

    private static UserController controller;


    @BeforeAll
    static void beforeAll() {
        service = Mockito.mock(UserServiceImpl.class);
        controller = UserController
                .builder()
                .userService(service)
                .build();
    }

    @AfterEach
    void after(){
        // Running into an issue with verify checking for number of method invocations including previous tests.
        //Resetting resets the observed number of method invocations to 0.
        Mockito.reset(service);
    }

    @Test
    void getAllUsers() {
        List<User> users = List.of(
                new User(1L, "test", "test", "test", "test")
        );

        //Tests both that controller is using serv.findAll() and that controller is piping return.
        when(service.getAllUsers()).thenReturn(users);
        assertEquals(controller.getAllUsers(), users);
    }

    @Test
    void getUserById() {
        ArgumentCaptor<Long> longArgumentCaptor = ArgumentCaptor.forClass(Long.class);
        controller.getUserById(1L);

        //Testing that controller is piping argument to service.
        verify(service).getUserById(longArgumentCaptor.capture());
        assertEquals(longArgumentCaptor.getValue(), 1L);

        //Testing that controller is piping return from service
        User user = new User();
        when(service.getUserById(1L)).thenReturn(user);
        assertEquals(controller.getUserById(1L), user);
    }


    @Test
    void getUserByCredentials() {

        ArgumentCaptor<String> stringArgumentCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> stringArgumentCaptor1 = ArgumentCaptor.forClass(String.class);

        when(service.getUserByCredentials(Mockito.anyString(), Mockito.anyString()))
                .thenReturn(new User());

        controller.getUserByCredentials("email", "password");

        //Testing that controller is piping argument to service.
        verify(service).getUserByCredentials(stringArgumentCaptor.capture(), stringArgumentCaptor1.capture());
        assertEquals(stringArgumentCaptor.getValue(), "email");
        assertEquals(stringArgumentCaptor1.getValue(), "password");

        //Checking UserException thrown and UserException message
        when(service.getUserByCredentials("", ""))
                .thenReturn(null);

        UserException ue = assertThrows(
                UserException.class,
                () -> controller.getUserByCredentials("", ""));

        assertEquals(ue.getMessage(), "User not found with those credentials.");

        //Testing that controller is piping return from service.
        User user = new User();
        when(service.getUserByCredentials("test", "test")).thenReturn(user);
        assertEquals(controller.getUserByCredentials("test", "test"), user);

    }

    @Test
    void addUser() {
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        User user = new User();
        controller.addUser(user);

        //Testing that controller is piping argument to service.
        verify(service).addUser(userArgumentCaptor.capture());
        assertEquals(userArgumentCaptor.getValue(), user);

        //Testing that controller is piping return from service
        User user1 = new User();
        when(service.addUser(user1)).thenReturn(user1);
        assertEquals(controller.addUser(user1), user1);

        //Checking UserException thrown and UserException message
        when(service.addUser(Mockito.any())).thenThrow(new DataIntegrityViolationException("This is a test"));
        UserException ue = assertThrows(
                UserException.class,
                () -> controller.addUser(user)
        );

        assertEquals(ue.getMessage(), "Registration failed. User already exists with that email.");
    }

    @Test
    void updateUser() {
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        ArgumentCaptor<Long> longArgumentCaptor = ArgumentCaptor.forClass(Long.class);

        //Testing that controller is piping argument to service.
        User user = new User();
        controller.updateUser(1L, user);
        verify(service).updateUser(longArgumentCaptor.capture(), userArgumentCaptor.capture());
        assertEquals(userArgumentCaptor.getValue(), user);
        assertEquals(longArgumentCaptor.getValue(), 1L);


        //Checking UserException thrown and UserException message
        doThrow(new DataIntegrityViolationException("test"))
                .when(service)
                .updateUser(Mockito.anyLong(), Mockito.any());

        UserException ue = assertThrows(
                UserException.class,
                () -> controller.updateUser(1L, user)
        );

        assertEquals(ue.getMessage(), "Update failed. Another User already exists with that email.");
    }

    @Test
    void deleteUser() {
        ArgumentCaptor<Long> longArgumentCaptor = ArgumentCaptor.forClass(Long.class);
        controller.deleteUser(1L);

        //Testing that controller is piping argument to service.
        verify(service).deleteUser(longArgumentCaptor.capture());
        assertEquals(longArgumentCaptor.getValue(), 1L);
    }
}