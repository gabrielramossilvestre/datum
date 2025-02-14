package br.com.datum.prova.service;

import br.com.datum.prova.entities.User;

import java.util.Optional;

public interface UserService {
    Optional<User> getByName(String name);
}
