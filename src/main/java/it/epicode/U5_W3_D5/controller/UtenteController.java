package it.epicode.U5_W3_D5.controller;

import it.epicode.U5_W3_D5.dto.UtenteLoginDTO;
import it.epicode.U5_W3_D5.dto.UtenteRegisterDTO;
import it.epicode.U5_W3_D5.model.Utente;
import it.epicode.U5_W3_D5.security.JwtUtil;
import it.epicode.U5_W3_D5.service.UtenteService;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class UtenteController {

    @Autowired
    private UtenteService utenteService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public Utente register(@RequestBody @Validated UtenteRegisterDTO utenteRegisterDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationException(bindingResult.getAllErrors().stream()
                    .map(objectError -> objectError.getDefaultMessage())
                    .reduce("", (e, s) -> e + s));
        }
        Utente utente = Utente.builder()
                .username(utenteRegisterDTO.getUsername())
                .email(utenteRegisterDTO.getEmail())
                .password(utenteRegisterDTO.getPassword())
                .ruolo(it.epicode.U5_W3_D5.model.Ruolo.valueOf(utenteRegisterDTO.getRuolo()))
                .build();
        return utenteService.saveUtente(utente);
    }

    @PostMapping("/login")
    public String login(@RequestBody @Validated UtenteLoginDTO utenteLoginDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationException(bindingResult.getAllErrors().stream()
                    .map(objectError -> objectError.getDefaultMessage())
                    .reduce("", (e, s) -> e + s));
        }
        Utente utente = utenteService.getUtenteByUsername(utenteLoginDTO.getUsername());
        if (utente == null || !passwordEncoder.matches(utenteLoginDTO.getPassword(), utente.getPassword())) {
            throw new ValidationException("Username o password errati");
        }
        return jwtUtil.createToken(utente.getUsername(), utente.getRuolo().name());
    }
}