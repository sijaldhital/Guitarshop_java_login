package com.spring.Usersadd;
import com.sijal.guitarshop.entity.UserEnt;
import com.sijal.guitarshop.repository.MyUserRepository;
import com.sijal.guitarshop.service.impl.UserAuthentiationServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
public class UserManagementUnitTest {
      @Mock
    private MyUserRepository userRepo;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserAuthentiationServiceImpl userAuthService;

    private UserEnt mockUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockUser = new UserEnt();
        mockUser.setId(1L);
        mockUser.setUsername("testuser");
        mockUser.setPassword("password123");
    }

    // --- Test: saveUser() ---

    @Test
    void saveUser_Success() {
        // Mock the password encoding
        when(passwordEncoder.encode("password123")).thenReturn("encodedPassword");
        when(userRepo.save(mockUser)).thenReturn(mockUser);

        // Act
        userAuthService.saveUser(mockUser);

        // Assert
        verify(userRepo, times(1)).save(mockUser);
        assertEquals("encodedPassword", mockUser.getPassword());
    }

    @Test
    void saveUser_Failure_UserIsNull() {
        // Act & Assert
        assertThrows(NullPointerException.class, () -> {
            userAuthService.saveUser(null);
        });

        verify(userRepo, never()).save(any());
    }

    // --- Test: checkAuthDetails() ---

    @Test
    void checkAuthDetails_Success() {
        // Mock the repository and password encoder
        when(userRepo.findByUsername("testuser")).thenReturn(mockUser);
        when(passwordEncoder.matches("password123", "password123")).thenReturn(true);

        // Act
        boolean result = userAuthService.checkAuthDetails(mockUser);

        // Assert
        assertTrue(result);
    }

    @Test
    void checkAuthDetails_Failure_InvalidPassword() {
        // Mock the repository and password encoder
        when(userRepo.findByUsername("testuser")).thenReturn(mockUser);
        when(passwordEncoder.matches("wrongpassword", "password123")).thenReturn(false);

        // Act
        boolean result = userAuthService.checkAuthDetails(mockUser);

        // Assert
        assertFalse(result);
    }

    @Test
    void checkAuthDetails_Failure_UserNotFound() {
        // Mock the repository
        when(userRepo.findByUsername("unknownuser")).thenReturn(null);

        // Act
        boolean result = userAuthService.checkAuthDetails(mockUser);

        // Assert
        assertFalse(result);
    }

    // --- Test: findById() (custom CRUD operation) ---

    @Test
    void findById_Success() {
        // Mock the repository
        when(userRepo.findById(1L)).thenReturn(Optional.of(mockUser));

        // Act
        Optional<UserEnt> result = userRepo.findById(1L);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(mockUser, result.get());
    }

    @Test
    void findById_Failure_UserNotFound() {
        // Mock the repository
        when(userRepo.findById(1L)).thenReturn(Optional.empty());

        // Act
        Optional<UserEnt> result = userRepo.findById(1L);

        // Assert
        assertFalse(result.isPresent());
    }

    // --- Test: deleteUserById() (CRUD operation) ---

    @Test
    void deleteUserById_Success() {
        // Mock the repository
        doNothing().when(userRepo).deleteById(1L);

        // Act
        userRepo.deleteById(1L);

        // Assert
        verify(userRepo, times(1)).deleteById(1L);
    }

    @Test
    void deleteUserById_Failure_UserNotFound() {
        // Mock the repository
        doThrow(new RuntimeException("User not found")).when(userRepo).deleteById(999L);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            userRepo.deleteById(999L);
        });

        verify(userRepo, times(1)).deleteById(999L);
    }
    
}
