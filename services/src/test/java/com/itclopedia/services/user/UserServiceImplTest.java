package com.itclopedia.services.user;

import com.itclopedia.cources.dao.payment.reminder.PaymReminderRepository;
import com.itclopedia.cources.dao.role.RoleRepository;
import com.itclopedia.cources.dao.user.UserRepository;
import com.itclopedia.cources.exceptions.EntityNotFoundException;
import com.itclopedia.cources.model.*;
import com.itclopedia.cources.services.user.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PaymReminderRepository paymReminderRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    private User testUser;

    private Role testRole;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setId(1);
        testUser.setName("testUser");
        testUser.setEmail("test@example.com");
        testUser.setPassword("password");
        testRole = new Role();
        testRole.setName("ROLE_USER");
    }

    @Test
    void getAllUsers() {
        List<User> users = Collections.singletonList(testUser);
        Mockito.when(userRepository.findAll()).thenReturn(users);
        List<User> result = userService.getAllUsers();
        assertEquals(1, result.size());
        assertEquals(testUser, result.get(0));
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void getUserById_UserExists() {
        Mockito.when(userRepository.findById(1)).thenReturn(Optional.of(testUser));
        User result = userService.getUserById(1);
        assertNotNull(result);
        assertEquals(testUser, result);
        verify(userRepository, times(1)).findById(1);
    }

    @Test
    void getUserById_UserDoesNotExist() {
        Mockito.when(userRepository.findById(1)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> userService.getUserById(1));
        verify(userRepository, times(1)).findById(1);
    }

    @Test
    void getUserByName_UserExists() {
        Mockito.when(userRepository.findByName("Test User")).thenReturn(Optional.of(testUser));
        User result = userService.getUserByName("Test User");
        assertNotNull(result);
        assertEquals(testUser, result);
        verify(userRepository, times(1)).findByName("Test User");
    }

    @Test
    void getUserByName_UserDoesNotExist() {
        Mockito.when(userRepository.findByName("Test User")).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> userService.getUserByName("Test User"));
        verify(userRepository, times(1)).findByName("Test User");
    }

    @Test
    void updateUserEmail_UserExists() {
        Mockito.when(userRepository.existsById(1)).thenReturn(true);
        doNothing().when(userRepository).updateUserEmail(1, "newemail@example.com");
        userService.updateUserEmail(1, "newemail@example.com");
        verify(userRepository, times(1))
                .updateUserEmail(1, "newemail@example.com");
    }

    @Test
    void updateUserEmail_UserDoesNotExist() {
        when(userRepository.existsById(1)).thenReturn(false);
        assertThrows(EntityNotFoundException.class,
                () -> userService.updateUserEmail(1, "newemail@example.com"));
        verify(userRepository, never()).updateUserEmail(anyInt(), anyString());
    }

    @Test
    void insertNewUser() {
        when(roleRepository.findByName("ROLE_USER")).thenReturn(testRole);
        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");
        userService.insertNewUser("Test User", "test@example.com", "password");
        verify(userRepository, times(1)).save(any(User.class));
    }


    @Test
    void deleteUser_UserDoesNotExist() {
        when(userRepository.findById(1)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> userService.deleteUser(1));
        verify(userRepository, never()).delete(any(User.class));
    }

    @Test
    void addPaymentReminder() {
        PaymentReminder paymentReminder = new PaymentReminder();
        when(userRepository.findById(1)).thenReturn(Optional.of(testUser));
        when(paymReminderRepository.findById(1)).thenReturn(Optional.of(paymentReminder));
        userService.addPaymentReminder(1, 1);
        verify(paymReminderRepository, times(1)).save(paymentReminder);
        assertTrue(testUser.getPaymentReminders().contains(paymentReminder));
        assertTrue(paymentReminder.getUserSet().contains(testUser));
    }

    @Test
    void addRole() {
        when(userRepository.findById(1)).thenReturn(Optional.of(testUser));
        when(roleRepository.findByName("ROLE_USER")).thenReturn(testRole);
        userService.addRole(1, "ROLE_USER");
        verify(userRepository, times(1)).save(testUser);
    }

}