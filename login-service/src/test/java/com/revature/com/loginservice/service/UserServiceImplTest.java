package com.revature.com.loginservice.service;

import com.revature.com.loginservice.entity.User;
import com.revature.com.loginservice.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.SecureRandom;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UserServiceImplTest {

    private static UserRepository repository;

    private static UserServiceImpl service;

    private static PasswordEncoder encoder;

    @BeforeAll
    static void beforeAll() {
        repository = Mockito.mock(UserRepository.class);
        encoder = new BCryptPasswordEncoder(10, new SecureRandom());
        service = new UserServiceImpl(repository, encoder);
    }

    @AfterEach
    void after(){
        // Running into an issue with verify checking for number of method invocations including previous tests.
        //Resetting resets the observed number of method invocations to 0.
        Mockito.reset(repository);
    }

    @Test
    void getAllUsers() {
        List<User> users = List.of(
                new User(1L, "test", "test", "test", "test")
        );

        when(repository.findAll()).thenReturn(users);
        assertEquals(service.getAllUsers(), users);

    }

    @Test
    void getUserById() {
        ArgumentCaptor<Long> longArgumentCaptor = ArgumentCaptor.forClass(Long.class);
        service.getUserById(1L);
        verify(repository).findById(longArgumentCaptor.capture());
        Long passed1 = longArgumentCaptor.getValue();
        assertEquals(passed1, 1L);

        Optional<User> e = Optional.empty();

        Mockito.when(repository.findById(1L)).thenReturn(e);
        assertNull(service.getUserById(1L));

        User user = new User();
        user.setUserId(2L);
        Mockito.when(repository.findById(2L)).thenReturn(Optional.of(user));
        assertEquals(service.getUserById(2L), user);
    }

    @Test
    void getUserByCredentials() {
        ArgumentCaptor<String> stringArgumentCaptor = ArgumentCaptor.forClass(String.class);

        service.getUserByCredentials("test@email.com", "test@email.com");
        verify(repository).findByEmail(stringArgumentCaptor.capture());
        assertEquals(stringArgumentCaptor.getValue(), "test@email.com");
        Mockito.when(repository.findByEmail("nullTest")).thenReturn(null);
        User passfail = new User();
        passfail.setPassword(encoder.encode("FAIL"));
        Mockito.when(repository.findByEmail("passwordFail")).thenReturn(passfail);
        User user = new User();
        user.setEmail("success");
        user.setPassword(encoder.encode("success"));
        Mockito.when(repository.findByEmail("success")).thenReturn(user);
        assertNull(service.getUserByCredentials("nullTest", "pass"));
        assertNull(service.getUserByCredentials("passwordFail", "pass"));
        assertEquals(service.getUserByCredentials("success", "success"), user);
    }

    @Test
    void getUserByEmail() {
        ArgumentCaptor<String> stringArgumentCaptor = ArgumentCaptor.forClass(String.class);
        service.getUserByCredentials("test@email.com", "test@email.com");
        verify(repository).findByEmail(stringArgumentCaptor.capture());
        assertEquals(stringArgumentCaptor.getValue(), "test@email.com");
    }

    @Test
    void addUser() {
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        User user = new User(1L, "test", "test", "test", "test");

        service.addUser(user);
        verify(repository).save(userArgumentCaptor.capture());
        assertTrue(encoder.matches("test", userArgumentCaptor.getValue().getPassword()));
        assertEquals(userArgumentCaptor.getValue(), user);
    }

    @Test
    void updateUser() {
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        User user = new User(1L, "test", "test", "test", "test");


        Mockito.when(repository.findById(0L)).thenReturn(Optional.empty());

        //Should not throw exception, update affects no user and returns no user, though.
        //Unsure how to show that.
        User check = new User(3L, "test3", "test3", "test3", "test3");
        service.updateUser(0L, check);
        assertEquals("test", user.getEmail());
        assertEquals("test", user.getFirstName());
        assertEquals("test", user.getLastName());
        assertEquals("test", user.getPassword());
        assertEquals(1L, user.getUserId());

        Mockito.when(repository.findById(Mockito.anyLong())).thenReturn(Optional.of(user));
        service.updateUser(1L, user);
        verify(repository).save(userArgumentCaptor.capture());

        //Checking encoder
        assertTrue(encoder.matches("test", userArgumentCaptor.getValue().getPassword()));
        assertEquals(userArgumentCaptor.getValue(), user);

        //Checking null check on password
        User user2 = new User(2L, "test2", null, "test2", "test2");

        String password = user.getPassword();
        service.updateUser(1L, user2);

        User usr_db = userArgumentCaptor.getValue();
        assertEquals(password, usr_db.getPassword());
        assertEquals(user2.getEmail(), usr_db.getEmail());
        assertEquals(user2.getFirstName(), usr_db.getFirstName());
        assertEquals(user2.getLastName(), usr_db.getLastName());
        assertEquals(1L, usr_db.getUserId());
    }

    @Test
    void deleteUser() {
        ArgumentCaptor<Long> longArgumentCaptor = ArgumentCaptor.forClass(Long.class);
        service.deleteUser(1L);
        verify(repository, Mockito.atLeastOnce()).deleteById(longArgumentCaptor.capture());
        assertEquals(longArgumentCaptor.getValue(), 1L);
    }
}