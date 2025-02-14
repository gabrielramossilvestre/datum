package br.com.datum.prova.service;

import br.com.datum.prova.entities.User;
import br.com.datum.prova.repository.UserRepository;
import br.com.datum.prova.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;

    private static final String USER_NAME = "gabriel";

    @BeforeEach
    void setUp() {
        user = new User();
        user.setUsername(USER_NAME);
        user.setPassword("gabriel");
    }

    @Test
    void testGetByNameUserExists() {
        when(userRepository.findByUsername(USER_NAME)).thenReturn(Optional.of(user));

        Optional<User> result = userService.getByName(USER_NAME);

        assertTrue(result.isPresent(), "O usuário deveria ser encontrado");
        Assertions.assertEquals(USER_NAME, result.get().getUsername());
    }

}
