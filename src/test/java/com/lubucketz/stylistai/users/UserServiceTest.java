package com.lubucketz.stylistai.users;

import com.lubucketz.stylistai.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository repository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService service;

    private CreateUserRequest createRequest;

    @BeforeEach
    void setUp() {
        createRequest = new CreateUserRequest(
                "lubucketz",
                "lu@example.com",
                "password123",
                "Lu",
                "Bucketz"
        );
    }

    @Test
    void shouldCreateUser() {

        when(passwordEncoder.encode("password123"))
                .thenReturn("hashed-password");

        when(repository.save(any(UserEntity.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        User result = service.create(createRequest);

        assertNotNull(result);
        assertEquals("lubucketz", result.username());
        assertEquals("lu@example.com", result.email());
        verify(repository).save(argThat(user ->
                user.getPassword().equals("hashed-password")
        ));

        verify(repository).save(any(UserEntity.class));
    }

    @Test
    void shouldLoginSuccessfully() {

        UserEntity user = new UserEntity();

        user.setId(UUID.randomUUID());
        user.setEmail("lu@example.com");
        user.setPassword("hashed-password");

        LoginRequest request =
                new LoginRequest("lu@example.com", "password123");

        when(repository.findByEmail("lu@example.com"))
                .thenReturn(Optional.of(user));

        when(passwordEncoder.matches(
                "password123",
                "hashed-password"
        )).thenReturn(true);

        User result = service.login(request);

        assertNotNull(result);
        assertEquals(user.getEmail(), result.email());
    }

    @Test
    void shouldThrowWhenUserNotFound() {

        LoginRequest request =
                new LoginRequest("missing@example.com", "password123");

        when(repository.findByEmail("missing@example.com"))
                .thenReturn(Optional.empty());

        assertThrows(
                RuntimeException.class,
                () -> service.login(request)
        );
    }

    @Test
    void shouldThrowWhenPasswordIsInvalid() {

        UserEntity user = new UserEntity();

        user.setEmail("lu@example.com");
        user.setPassword("hashed-password");

        LoginRequest request =
                new LoginRequest("lu@example.com", "wrong-password");

        when(repository.findByEmail("lu@example.com"))
                .thenReturn(Optional.of(user));

        when(passwordEncoder.matches(
                "wrong-password",
                "hashed-password"
        )).thenReturn(false);

        assertThrows(
                RuntimeException.class,
                () -> service.login(request)
        );
    }
}
