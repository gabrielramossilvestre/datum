package br.com.datum.prova.config;

import br.com.datum.prova.entities.User;
import br.com.datum.prova.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class AdminUserConfigTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @InjectMocks
    private AdminUserConfig adminUserConfig;

    @Test
    void testRunWhenUserDoesNotExist() {
        when(userRepository.findByUsername("admin")).thenReturn(java.util.Optional.empty());
        when(passwordEncoder.encode(any(String.class))).thenReturn("encodedPassword");

        try {
            adminUserConfig.run();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        verify(userRepository, times(1)).save(any(User.class)); // Verifica se o usuário foi salvo
    }

    @Test
    void testRunWhenUserExists() {
        User existingUser = new User();
        existingUser.setUsername("admin");
        existingUser.setPassword("admin");

        when(userRepository.findByUsername("admin")).thenReturn(java.util.Optional.of(existingUser));

        try {
            adminUserConfig.run();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        verify(userRepository, times(0)).save(any(User.class)); // Verifica que o método save não foi chamado
    }
}
