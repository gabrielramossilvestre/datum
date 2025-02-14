package br.com.datum.prova.service.impl;

import br.com.datum.prova.entities.User;
import br.com.datum.prova.repository.UserRepository;
import br.com.datum.prova.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> getByName(String name) {
        var user = userRepository.findByUsername(name);
        if (user.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return user;
    }
}
