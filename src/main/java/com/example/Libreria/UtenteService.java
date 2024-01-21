package com.example.Libreria;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UtenteService {
    @Autowired
    private UtenteRepository utenteRepository;

    public Utente saveUser(Utente user) {
        return utenteRepository.save(user);
    }

    public Optional<Utente> findUserByUsername(String username) {
        return utenteRepository.findByUsername(username);
    }

    public Optional<Utente> findUserById(Long id) {
        return utenteRepository.findById(id);
    }
}
