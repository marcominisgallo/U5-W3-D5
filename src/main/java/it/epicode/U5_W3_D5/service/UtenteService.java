package it.epicode.U5_W3_D5.service;

import it.epicode.U5_W3_D5.model.Utente;
import it.epicode.U5_W3_D5.repository.UtenteRepository;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UtenteService {

    @Autowired
    private UtenteRepository utenteRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Utente saveUtente(Utente utente) {
        if (utenteRepository.findByUsername(utente.getUsername()).isPresent()) {
            throw new ValidationException("Username già esistente");
        }
        if (utenteRepository.findByEmail(utente.getEmail()).isPresent()) {
            throw new ValidationException("Email già esistente");
        }
        utente.setPassword(passwordEncoder.encode(utente.getPassword()));
        return utenteRepository.save(utente);
    }

    public List<Utente> getAllUtenti() {
        return utenteRepository.findAll();
    }

    public Utente getUtenteByUsername(String username) {
        return utenteRepository.findByUsername(username).orElse(null);
    }

    public Optional<Utente> getUtenteById(Long id) {
        return utenteRepository.findById(id);
    }
}